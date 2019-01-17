package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.Group;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.gxc.base.BaseActivity;
import com.gxc.utils.PictureUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.relationGroup)
    Group relationGroup;

    private boolean isRelation = false; // 是否为查关系

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

    /**
     * 查关系
     *
     * @param context
     * @param url
     * @return
     */
    public static Intent getRelationIntent(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isRelation", true);
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

        isRelation = getIntent().getBooleanExtra("isRelation", false);
        relationGroup.setVisibility(isRelation ? View.VISIBLE : View.GONE);

        if (isRelation) { // 查关系
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
            titleView.setVisibility(View.GONE);
        }

        titleView.setTitle(title);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(layout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(isRelation ? Color.TRANSPARENT : -1)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 在android5.0及以上版本使用webView进行截长图时,默认是截取可是区域内的内容.因此需要在支撑窗体内容之前加上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            WebView.enableSlowWholeDocumentDraw();
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.vFinish, R.id.vSave, R.id.vShare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vFinish:
                finish();
                break;
            case R.id.vSave:
                PictureUtils.saveImage(activity, mAgentWeb.getWebCreator().getWebView());
                break;
            case R.id.vShare:
                break;
        }
    }

}
