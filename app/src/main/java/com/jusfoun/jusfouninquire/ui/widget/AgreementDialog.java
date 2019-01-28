package com.jusfoun.jusfouninquire.ui.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;


/**
 * @Description 支付协议对话框
 */
public class AgreementDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private CheckBox vCheckBox;

    public AgreementDialog(BaseInquireActivity activity, OnSelectListener listener) {
        super(activity);
        this.activity = activity;
        this.listener = listener;
        initViews();
    }

    private void initViews() {
        setContentView(R.layout.dialog_agreement);
        findViewById(R.id.vCancel).setOnClickListener(this);
        findViewById(R.id.vSure).setOnClickListener(this);
        findViewById(R.id.vAgree).setOnClickListener(this);

        vCheckBox = (CheckBox) findViewById(R.id.vCheckBox);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(activity) * 1);
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);

//        String userId = InquireApplication.getUserInfo().getUserid();
//        String emailLog = PreferenceUtils.getString(getContext(), EMAIL_LOG);
//        if (!TextUtils.isEmpty(emailLog)) {
//            try {
//                JSONObject obj = new JSONObject(emailLog);
//                if (obj.has(userId))
//                    etEmail.setText(obj.getString(userId));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:
                if (vCheckBox.isChecked() && listener != null) {
                    listener.select();
                    dismiss();
                }
                break;
            case R.id.vAgree:
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("url", activity.getResources().getString(R.string.req_url) + "/Html/agreement.html ");
                intent.putExtra("title", "用户协议");
                activity.startActivity(intent);
                break;
        }
    }

    private OnSelectListener listener;

    public interface OnSelectListener {
        void select();
    }
}
