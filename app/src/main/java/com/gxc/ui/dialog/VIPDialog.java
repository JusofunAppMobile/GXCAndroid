package com.gxc.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.gxc.ui.activity.PayActivity;
import com.jusfoun.jusfouninquire.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 16:42
 * @Email lgd@jusfoun.com
 * @Description ${企业认证对话框}
 */
public class VIPDialog extends BaseDialog {

    public VIPDialog(Activity activity) {
        super(activity);
        setContentView(R.layout.dialog_vip);
        ButterKnife.bind(this);
        setWindowStyle(8, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }

    @OnClick({R.id.bt, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt:
                activity.startActivity(new Intent(activity,PayActivity.class));
                dismiss();
                break;
            case R.id.ivClose:
                dismiss();
                break;
        }
    }
}
