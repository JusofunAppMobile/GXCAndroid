package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

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

            }
        });

        type = getIntent().getIntExtra("type", 0);
        value = getIntent().getStringExtra("value");

        titleView.setTitle(getTitleValue());
        etInput.setHint(getHint());
        if (!TextUtils.isEmpty(value))
            etInput.setText(value);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtils.showSoftInput(activity);
            }
        }, 200);
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
