package com.jusfoun.jusfouninquire.ui.util.crawl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.ui.util.crawl.service.WebService;

import de.greenrobot.event.EventBus;
import netlib.util.EncryptUtil;

/**
 * @author zhaoyapeng
 * @version create time:17/7/3115:44
 * @Email zyp@jusfoun.com
 * @Description ${封装请求}
 */
public class HtmlRequest implements BaseReptileRequest {

    private Context mContext;
    private Handler handler;
    private ReptileDispatcher reptileDispatcher;
    private TaskModel model;
    private boolean isAtLast = false;
    private int count = 0;
    private int failCode = -1001;//错误码，无网络或者 被限制

    public HtmlRequest(Context mContext, final Handler handler, TaskModel model) {
        this.mContext = mContext;
        this.handler = handler;
        this.model = model;
        if (model == null) {
            this.model = TaskSharedpreference.getTask(mContext);
        }
    }

    @Override
    public void start() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.e("tag", "start HtmlRequest");
                getHtmlData();
            }
        }, 1000);
    }

    @Override
    public void setDispatcher(ReptileDispatcher reptileDispatcher) {
        this.reptileDispatcher = reptileDispatcher;
    }

    public void getHtmlData() {

        String url;
        try {
            if(model==null|| TextUtils.isEmpty(model.getU())){
                startNetTask();
                return;
            }
            url = EncryptUtil.decryptAES(model.getU());
            if(TextUtils.isEmpty(url)){
                startNetTask();
                return;
            }
        }catch (Exception e){
            startNetTask();
            return;
        }

        VolleyHtmlGetRequest getRequest = new VolleyHtmlGetRequest(url, new Response.Listener<ResultModel>() {
            @Override
            public void onResponse(ResultModel response) {
                Log.e("tag", "response=" + new Gson().toJson(response));
//                PathUtil.stringToFile(mContext, response, model);
                if (response.getCode() == 200) {
                    startNextTask(response);
                } else {
                    reStartGetTask(null,response.getCode());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e("tag", "error1=" + new Gson().toJson(error));
//                Log.e("tag","error2="+error.getMessage());
//                Log.e("tag","error3="+error.getCause().getMessage());

                    reStartGetTask(error,-1);
            }
        }, mContext);
        getRequest.setShouldCache(false);
        VolleyUtil.getQueue(mContext).add(getRequest);
    }


    @Override
    public void setAtLast(boolean isAtLast) {
        this.isAtLast = isAtLast;
    }

    private void checkTaskAndUpload() {
        if (isAtLast) {
            ZipUtil.zipFolder(PathUtil.getFilePath(mContext), PathUtil.getZipFilePath(mContext, System.currentTimeMillis() + ".zip"));
            // TODO 提交接口
            // 提交完成之后 ，请求下一次数据 重新启动service 执行onStartCommand
            Intent intent = new Intent(mContext, Service.class);
            mContext.startService(intent);
        }
    }

    /**
     * 抓取数据 成功 开始下一个任务，提交
     * 1.存储抓取结果 正常情况存储抓取结果，5次错误之后添加错误结果
     * 2.添加提交任务至队列
     * 3.
     */
    private void startNextTask(ResultModel resultModel) {

        TaskEvent taskEvent = new TaskEvent();
        taskEvent.setState("抓取成功");
        taskEvent.setContent(resultModel.getResult());
        EventBus.getDefault().post(taskEvent);

        TaskSharedpreference.saveTaskResult(mContext, new Gson().toJson(resultModel));
        SubmitTaskRequest request = new SubmitTaskRequest(mContext, handler, resultModel);
        ReptileUtil.getInatance().add(request);
        reptileDispatcher.taskFinish();
    }


    /**
     * 获取数据失败，五分钟之后 再次获取
     * 最多2次，2次之后 记为失败，启动一下任务，提交服务端错误原因
     */
    private void reStartGetTask(VolleyError error,int failCode) {
        count++;
        if (count > 2) {
            ResultModel resultModel = new ResultModel();
            if (error != null ) {
               if(error.networkResponse != null){
                   resultModel.setCode(error.networkResponse.statusCode);
                   resultModel.setResult(error.networkResponse.statusCode + "");
               }else{
                   resultModel.setCode(failCode);
                   resultModel.setResult(new Gson().toJson(error));
               }
            }else{
                resultModel.setCode(failCode);
                resultModel.setResult(failCode+"");
            }

            startNextTask(resultModel);
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getHtmlData();
            }
        }, TaskConstant.TIME_DELAY);
    }

//    /**
//     *
//     *   特殊异常情况，可能是由于无网络造成，超时 再启动一次，此次请求不要计数
//     *
//     * */
//    private void reStartGetTask(){
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getHtmlData();
//            }
//        },5*60*1000);
//    }
    /**
     * 提交数据成功
     * 1.清楚本地村存储的任务
     * 2.开启下一次闹钟
     */
    private void startNetTask() {

        // TODO handler.removeALLMsg 待优化，开启下一次 定时时 移除所有任务
        TaskModel taskModel = TaskSharedpreference.getTask(mContext);

        long triggerAtTime;
        if(taskModel!=null){
            triggerAtTime = SystemClock.elapsedRealtime() + taskModel.getT();
        }else{
            triggerAtTime = SystemClock.elapsedRealtime() + TaskConstant.TIME_DELAY;
        }
//       triggerAtTime = SystemClock.elapsedRealtime() + 5*1000;
        TaskSharedpreference.clearTask(mContext);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, WebService.class);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        }

//        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
        TaskEvent taskEvent = new TaskEvent();
        taskEvent.setState("提交成功");
        EventBus.getDefault().post(taskEvent);

        reptileDispatcher.taskFinish();
    }

}
