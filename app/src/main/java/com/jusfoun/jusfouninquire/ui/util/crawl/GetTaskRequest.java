package com.jusfoun.jusfouninquire.ui.util.crawl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

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
 * @Description ${获取任务request}
 */
public class GetTaskRequest implements BaseReptileRequest {

    private Context mContext;
    private Handler handler;
    private ReptileDispatcher reptileDispatcher;
    private boolean isAtLast = false;

    public GetTaskRequest(Context mContext, final Handler handler) {
        this.mContext = mContext;
        this.handler = handler;
    }

    @Override
    public void start() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.e("tag", "start GetTaskRequest");
                getCompanyMapDetail();
            }
        });
    }

    @Override
    public void setDispatcher(ReptileDispatcher reptileDispatcher) {
        this.reptileDispatcher = reptileDispatcher;
    }


    public void getCompanyMapDetail() {

        SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMdd");
        String di = myFmt.format(new Date(System.currentTimeMillis())) + "_" + PhoneUtil.getIMEI(mContext);

        Map<String, String> map = new HashMap<>();
        map.put("di", EncryptUtil.encryptAES(di));

//        LogUtil.e("tag","di1="+di);
//        LogUtil.e("tag","di2="+EncryptUtil.encryptAES(di));
//        LogUtil.e("tag","di3="+EncryptUtil.decryptAES(EncryptUtil.encryptAES(di)));

        VolleyGetRequest<TaskData> getRequest = new VolleyGetRequest<>(
                GetParamsUtil.getParmas(mContext.getString(R.string.req_url) + "/api/CompanyInfo/GetCompany", map),
                TaskData.class, new Response.Listener<TaskData>() {
            @Override
            public void onResponse(TaskData response) {
                if (response.getResult() == 0&&response.getData()!=null) {
                    // 获取任务成功，存储 任务
                    startNetTask(response.getData());
                } else {
                    reStartGetTask();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                reStartGetTask();
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
     * 获取任务成功
     * 1.存储任务
     * 2.添加 抓取任务
     */
    private void startNetTask(TaskModel taskModel) {
        TaskEvent taskEvent = new TaskEvent();
        taskEvent.setState("获取到任务");
        taskEvent.setUrl(taskModel.getU());
        taskEvent.setTime(taskModel.getT() + "");
        EventBus.getDefault().post(taskEvent);


        TaskSharedpreference.saveTask(mContext, taskModel);
        reptileDispatcher.taskFinish();
        HtmlRequest htmlRequest = new HtmlRequest(mContext, handler, taskModel);
        ReptileUtil.getInatance().add(htmlRequest);
    }

    /**
     * 获取任务失败，五分钟之后 再次获取
     */
    private void reStartGetTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCompanyMapDetail();
            }
        }, TaskConstant.TIME_DELAY);
    }


}
