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

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.ui.util.crawl.service.WebService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import netlib.util.EncryptUtil;

/**
 * @author zhaoyapeng
 * @version create time:17/7/3115:44
 * @Email zyp@jusfoun.com
 * @Description ${提交任务结果任务request}
 */
public class SubmitTaskRequest implements BaseReptileRequest {

    private Context mContext;
    private Handler handler;
    private ReptileDispatcher reptileDispatcher;
    private boolean isAtLast = false;
    private int count = 0;
    private ResultModel resultModel;

    public SubmitTaskRequest(Context mContext, final Handler handler, ResultModel resultModel) {
        this.mContext = mContext;
        this.handler = handler;
        this.resultModel = resultModel;
        if(resultModel==null){
            this.resultModel = TaskSharedpreference.getTaskResult(mContext);
        }
    }

    @Override
    public void start() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("tag", "start SubmitTaskRequest");
                getCompanyMapDetail();
            }
        });
    }

    @Override
    public void setDispatcher(ReptileDispatcher reptileDispatcher) {
        this.reptileDispatcher = reptileDispatcher;
    }


    public void getCompanyMapDetail() {

        TaskModel taskModel  = TaskSharedpreference.getTask(mContext);
        if(taskModel==null|| TextUtils.isEmpty(taskModel.getU())){
            startNetTask();
            return;
        }

        SimpleDateFormat myFmt=new SimpleDateFormat("yyyyMMdd");
        String di =  myFmt.format(new Date(System.currentTimeMillis()))+"_"+ PhoneUtil.getIMEI(mContext);

        final HashMap<String,String> params = new HashMap<>();
        params.put("di",EncryptUtil.encryptAES(di));
        params.put("f",resultModel.getResult());
        params.put("u",taskModel.getU());

        VolleyPostRequest<TaskModel> getRequest = new VolleyPostRequest<TaskModel>(mContext.getString(R.string.req_url)+"/api/CompanyInfo/PostCompany", TaskModel.class, new Response.Listener<TaskModel>() {
            @Override
            public void onResponse(TaskModel response) {
                LogUtil.e("tag", "response=" + response);
                if (response.getResult() == 0) {
                    startNetTask();
                } else {
                    reStartGetTask();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                if (error != null && error.networkResponse != null) {
//                    reStartGetTask();
//                } else {
//                    reStartGetTaskNoCount();
//                }
                reStartGetTask();
            }
        }, mContext){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
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

    /**
     * 提交任务失败，五分钟之后再次提交，5次之后开启下一次闹钟
     */
    private void reStartGetTask() {
        count++;
        if (count >= 5) {
            startNetTask();
            return;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCompanyMapDetail();
            }
        }, 5 * 60 * 1000);
    }


//    /**
//     * 再重提一次 不需要计数
//     */
//    private void reStartGetTaskNoCount() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getCompanyMapDetail();
//            }
//        }, 5 * 60 * 1000);
//    }


}
