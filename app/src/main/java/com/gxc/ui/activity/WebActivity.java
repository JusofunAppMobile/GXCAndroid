package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.Group;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.utils.LogUtils;
import com.gxc.utils.PictureUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;

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
    @BindView(R.id.ivBack2)
    View ivBack2;

    private boolean isRelation = false; // 是否为查关系

    private boolean isFullScreen = false;// 网页是否全屏

    private View errorView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_web;
    }

    @Override
    public void initActions() {
        ivBack2.setVisibility(isFullScreen ? View.VISIBLE : View.GONE);
        titleView.setVisibility(isFullScreen ? View.GONE : View.VISIBLE);

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        LogUtils.e("URL=" + url);
        LogUtils.e("TITLE=" + title);

        isRelation = getIntent().getBooleanExtra("isRelation", false);
        relationGroup.setVisibility(isRelation ? View.VISIBLE : View.GONE);

        if (isRelation) { // 查关系
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
            titleView.setVisibility(View.GONE);
        }

        titleView.setTitle(title);

        errorView = View.inflate(this, R.layout.view_empty, null);
        errorView.setVisibility(View.VISIBLE);
        TextView textView = errorView.findViewById(R.id.tvEmpty);
        textView.setText("刷新试试吧~");
        errorView.findViewById(R.id.tvError).setVisibility(View.VISIBLE);
        errorView.findViewById(R.id.tvReload).setVisibility(View.VISIBLE);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(layout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(isRelation ? Color.TRANSPARENT : -1)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(errorView)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(url);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            LogUtils.e("shouldOverrideUrlLoading:URL=" + view.getUrl());
//            http://202.106.10.250:4808/dist/#/vip?data=9iFQlbMLJeuq9ONpaG%2FVDkFlePca9Rf%2F9v6UTySLWiET1dgisCQG6A%3D%3D
            return super.shouldOverrideUrlLoading(view, request);
        }
    };

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // 清空缓存
        AgentWebConfig.clearDiskCache(this);
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
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


    public static Intent getIntent(Context context, String title, String url) {
        return getIntent(context, title, url, false);
    }


    public static Intent getIntent(Context context, String title, String url, boolean isFullScreen) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("isZoomable", false);
        intent.putExtra("isFullScreen", isFullScreen);
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
    protected void onCreate(Bundle savedInstanceState) {
        // 在android5.0及以上版本使用webView进行截长图时,默认是截取可是区域内的内容.因此需要在支撑窗体内容之前加上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            WebView.enableSlowWholeDocumentDraw();
        isFullScreen = getIntent().getBooleanExtra("isFullScreen", false);
        if (!isFullScreen)
            setTheme(R.style.MyTheme_Layout_Root);
        else
            setTheme(R.style.MyTheme_Layout_Root2);
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.vFinish, R.id.vSave, R.id.vShare, R.id.ivBack2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vFinish:
            case R.id.ivBack2:
                finish();
                break;
            case R.id.vSave:
                PictureUtils.saveImage(activity, mAgentWeb.getWebCreator().getWebView());
                break;
            case R.id.vShare:
                break;
        }
    }

    @Override
    public boolean isSetStatusBar() {
        if (isFullScreen)
            return false;
        return super.isSetStatusBar();
    }

    @Override
    public boolean isBarDark() {
        if (isFullScreen)
            return false;
        return super.isBarDark();
    }
}
