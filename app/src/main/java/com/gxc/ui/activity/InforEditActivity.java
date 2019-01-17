package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.AppUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.util.RegexUtils;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.HashMap;

import butterknife.BindView;
import netlib.util.PreferenceUtils;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 18:44
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class InforEditActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.etInput)
    EditText etInput;

    private int type;
    private String value;

    @Override
    protected int getLayoutId() {
        return R.layout.act_infor_edit;
    }

    public static Intent getIntent(Context context, int type, String value) {
        Intent intent = new Intent(context, InforEditActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("value", value);
        return intent;
    }

    @Override
    public void initActions() {
        titleView.setRightText("保存");
        titleView.setRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        type = getIntent().getIntExtra("type", 0);
        value = getIntent().getStringExtra("value");

        titleView.setTitle(getTitleValue());
        etInput.setHint(getHint());
        if (!TextUtils.isEmpty(value)) {
            etInput.setText(value);
            etInput.setSelection(value.length());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtils.showSoftInput(activity);
            }
        }, 200);
    }

    private String getKey() {
        if (type == Constants.INFOR_TYPE_COMPANY)
            return "company";
        else if (type == Constants.INFOR_TYPE_EMAIL)
            return "email";
        else if (type == Constants.INFOR_TYPE_JOB)
            return "job";
        else if (type == Constants.INFOR_TYPE_DEPARTMENT)
            return "department";
        else if (type == Constants.INFOR_TYPE_TRADE)
            return "trade";
        return "";
    }

    private void resetUser() {
        UserModel user = AppUtils.getUser();
        String value = getValue(etInput);
        if (user != null) {
            if (type == Constants.INFOR_TYPE_COMPANY)
                user.company = value;
            else if (type == Constants.INFOR_TYPE_EMAIL)
                user.email = value;
            else if (type == Constants.INFOR_TYPE_JOB)
                user.job = value;
            else if (type == Constants.INFOR_TYPE_DEPARTMENT)
                user.department = value;
            else if (type == Constants.INFOR_TYPE_TRADE)
                user.trade = value;

            PreferenceUtils.setString(activity, Constants.USER, gson.toJson(user));
            finish();
        }
    }

    public void save() {
        if (TextUtils.isEmpty(getValue(etInput))) {
            showToast("请" + getHint());
            return;
        }

        if (type == Constants.INFOR_TYPE_EMAIL) {
            if (!RegexUtils.checkEmail(getValue(etInput))) {
                showToast("邮箱格式不正确");
                return;
            }
        }

        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put(getKey(), getValue(etInput));
        RxManager.http(RetrofitUtils.getApi().updateInfo(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    showToast("修改成功");
                    resetUser();
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });
    }

    private String getTitleValue() {
        if (type == Constants.INFOR_TYPE_COMPANY)
            return "修改公司";
        else if (type == Constants.INFOR_TYPE_EMAIL)
            return "修改邮箱";
        else if (type == Constants.INFOR_TYPE_JOB)
            return "修改职务";
        else if (type == Constants.INFOR_TYPE_DEPARTMENT)
            return "修改部门";
        else if (type == Constants.INFOR_TYPE_TRADE)
            return "修改行业";
        return "";
    }

    private String getHint() {
        if (type == Constants.INFOR_TYPE_COMPANY)
            return "填写公司";
        else if (type == Constants.INFOR_TYPE_EMAIL)
            return "填写邮箱";
        else if (type == Constants.INFOR_TYPE_JOB)
            return "填写职务";
        else if (type == Constants.INFOR_TYPE_DEPARTMENT)
            return "填写部门";
        else if (type == Constants.INFOR_TYPE_TRADE)
            return "填写行业";
        return "";
    }
}
