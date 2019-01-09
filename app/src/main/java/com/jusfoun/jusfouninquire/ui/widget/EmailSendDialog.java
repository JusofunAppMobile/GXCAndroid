package com.jusfoun.jusfouninquire.ui.widget;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.route.ReportRoute;
import com.jusfoun.jusfouninquire.service.event.RefreshMyReportEvent;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.RegularUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import netlib.util.PreferenceUtils;
import netlib.util.ToastUtils;

import com.jusfoun.jusfouninquire.TimeOut;

/**
 * @Description 发送邮件对话框
 */
public class EmailSendDialog extends Dialog implements View.OnClickListener {

    private BaseInquireActivity activity;
    private String entName;
    private String entId;
    private EditText etEmail;

    private final static String EMAIL_LOG = "email_log";

    public static final int TYPE_REPORT_ONE= 1;//发送单个企业报告
    public static final int TYPE_CONTENT = 2;//发送通讯录
    public static final int TYPE_REPORT_MORE = 3; //发送多个企业报告
    private int type =1;

//    public EmailSendDialog(BaseInquireActivity activity, String entName, String entId) {
//        super(activity);
//        this.activity = activity;
//        this.entName = entName;
//        this.entId = entId;
//        initViews();
//    }
//
//    /**
//     * 发送企业通讯录
//     *
//     * @param activity
//     * @param entId
//     */
//    public EmailSendDialog(BaseInquireActivity activity, String entId) {
//        super(activity);
//        isSendContacts = true;
//        this.activity = activity;
//        this.entName = entName;
//        this.entId = entId;
//        initViews();
//    }

    /**
     * 发送企业通讯录
     *
     * @param activity
     * @param entId
     */
    public EmailSendDialog(BaseInquireActivity activity, String entName, String entId,int type) {
        super(activity);
        this.activity = activity;
        this.entName = entName;
        this.entId = entId;
        this.type = type;
        initViews();
    }

    private void initViews() {
        setContentView(R.layout.dialog_email_send);
        findViewById(R.id.vCancel).setOnClickListener(this);
        findViewById(R.id.vSure).setOnClickListener(this);
        etEmail = (EditText) findViewById(R.id.etEmail);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(activity) * 1);
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);

        String userId = InquireApplication.getUserInfo().getUserid();
        String emailLog = PreferenceUtils.getString(getContext(), EMAIL_LOG);
        if (!TextUtils.isEmpty(emailLog)) {
            try {
                JSONObject obj = new JSONObject(emailLog);
                if (obj.has(userId))
                    etEmail.setText(obj.getString(userId));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:
                String email = etEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    ToastUtils.show("请输入报告接收邮箱");
                    return;
                } else if (!RegularUtils.checkEmail(email)) {
                    ToastUtils.show("邮箱格式不正确");
                    return;
                }

                String userId = InquireApplication.getUserInfo().getUserid();

                // 将输入的邮箱按用户的形式保存在本地
                String emailLog = PreferenceUtils.getString(getContext(), EMAIL_LOG);
                try {
                    JSONObject obj;
                    if (TextUtils.isEmpty(emailLog))
                        obj = new JSONObject();
                    else
                        obj = new JSONObject(emailLog);
                    obj.put(userId, email);

                    PreferenceUtils.setString(getContext(), EMAIL_LOG, obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                activity.showLoading();
                if (type==TYPE_CONTENT) {
                    sendContacts(email, userId);
                } else if(type == TYPE_REPORT_ONE) {
                    sendReprot(email, userId);
                }else if(type==TYPE_REPORT_MORE){
                    sendMoreReprot(email, userId);
                }
                break;
        }
    }

    /**
     *   发送单个企业报告
     * */
    private void sendReprot(String email, String userId) {
        HashMap<String, String> map = new HashMap<>();
        final TimeOut timeOut = new TimeOut(activity);
        map.put("entName", entName);
        map.put("entId", entId);
        map.put("email", email);
        map.put("userId", userId);
        map.put("t", timeOut.getParamTimeMollis() + "");
        map.put("m", timeOut.MD5time() + "");

        ReportRoute.sendEmail(activity, map, activity.getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                activity.hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    ToastUtils.show("导出成功，请前往邮箱查看");
                    EventBus.getDefault().post(new RefreshMyReportEvent());
                    dismiss();
                } else {
                    ToastUtils.show(model.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                activity.hideLoadDialog();
                ToastUtils.show(error);
            }
        });
    }



    /***
     *   发送多个企业报告
     * */
    private void sendMoreReprot(String email, String userId) {
        HashMap<String, String> map = new HashMap<>();
        final TimeOut timeOut = new TimeOut(activity);
        map.put("entId", entId);
        map.put("email", email);
        map.put("userId", userId);
        map.put("t", timeOut.getParamTimeMollis() + "");
        map.put("m", timeOut.MD5time() + "");

        ReportRoute.sendReportMoreEmail(activity, map, activity.getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                activity.hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    ToastUtils.show("导出成功，请前往邮箱查看");
                    EventBus.getDefault().post(new RefreshMyReportEvent());
                    dismiss();
                } else {
                    ToastUtils.show(model.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                activity.hideLoadDialog();
                ToastUtils.show(error);
            }
        });
    }

    /**
     * 发送企业通讯录
     *
     * @param email
     * @param userId
     */
    private void sendContacts(String email, String userId) {
        HashMap<String, String> map = new HashMap<>();
        final TimeOut timeOut = new TimeOut(activity);
        map.put("t", timeOut.getParamTimeMollis() + "");
        map.put("m", timeOut.MD5time() + "");

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("EntId", entId);
        bodyMap.put("email", email);
        bodyMap.put("userId", userId);
        ReportRoute.sendContacts(activity, map, bodyMap, activity.getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                activity.hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    ToastUtils.show("导出成功，请前往邮箱查看");
                    dismiss();
                } else {
                    ToastUtils.show(model.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                activity.hideLoadDialog();
                ToastUtils.show(error);
            }
        });
    }
}
