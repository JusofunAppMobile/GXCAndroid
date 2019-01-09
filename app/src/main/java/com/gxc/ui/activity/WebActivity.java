package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liuguangdan
 * @version create at 2018/9/25/025 13:54
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class WebActivity extends BaseActivity {

    AgentWeb mAgentWeb;
    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.layout)
    LinearLayout layout;

    public static Intent getIntent(Context context, String title, String url, boolean isZoomable) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("isZoomable", isZoomable);
        return intent;
    }

    public static Intent getIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("isZoomable", false);
        return intent;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.act_web;
    }

    @Override
    public void initActions() {
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        titleView.setTitle(title);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(layout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);


        if (getIntent().getBooleanExtra("isZoomable", false)) {
            mAgentWeb.getWebCreator().getWebView().getSettings().setUseWideViewPort(true);
            mAgentWeb.getWebCreator().getWebView().setInitialScale(25);//为25%，最小缩放等级
            mAgentWeb.getWebCreator().getWebView().getSettings().setJavaScriptEnabled(true);
            mAgentWeb.getWebCreator().getWebView().getSettings().setSupportZoom(true);
            mAgentWeb.getWebCreator().getWebView().getSettings().setBuiltInZoomControls(true);
            mAgentWeb.getWebCreator().getWebView().getSettings().setDisplayZoomControls(false);
        }
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb != null && mAgentWeb.back())
            mAgentWeb.getWebCreator().getWebView().goBack();
        else
            super.onBackPressed();
    }
}
