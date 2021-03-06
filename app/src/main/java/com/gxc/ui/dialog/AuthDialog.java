package com.gxc.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.gxc.ui.activity.CertifiedCompanyActivity;
import com.siccredit.guoxin.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 16:42
 * @Email lgd@jusfoun.com
 * @Description ${VIP权限对话框}
 */
public class AuthDialog extends BaseDialog {

    public AuthDialog(Activity activity) {
        super(activity);
        setContentView(R.layout.dialog_auth);
        ButterKnife.bind(this);
        setWindowStyle(8, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }

    @OnClick({R.id.bt, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt:
                Intent intent = new Intent(activity, CertifiedCompanyActivity.class);
                activity.startActivity(intent);
                dismiss();
                break;
            case R.id.ivClose:
                dismiss();
                break;
        }
    }
}
