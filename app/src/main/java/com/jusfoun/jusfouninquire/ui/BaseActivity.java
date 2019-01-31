package com.jusfoun.jusfouninquire.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午3:22
 * @Email zyp@jusfoun.com
 * @Description ${所有activity 基类}
 */
public abstract class BaseActivity extends FragmentActivity {
    protected Context mContext;
    protected String TAG="";
    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initWidgetActions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=this.getLocalClassName();
        mContext = this;
        initData();
        initView();
        initWidgetActions();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(this.getClass().getSimpleName());
//        MobclickAgent.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
//        MobclickAgent.onPause(mContext);
    }
}
