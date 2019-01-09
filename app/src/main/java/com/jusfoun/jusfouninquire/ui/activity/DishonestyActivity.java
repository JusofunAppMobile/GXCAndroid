package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/17.
 * Description 失信榜单
 */
public class DishonestyActivity extends BaseInquireActivity {

    private WebView webView;
    private BackAndRightImageTitleView title;
    private String url;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            url = bundle.getString("url");

        Log.e("tag","url="+url);
    }

    @Override
    protected void initView() {

        setContentView(R.layout.activity_dishonesty);
        webView = (WebView) findViewById(R.id.dishonest_web);
        title = (BackAndRightImageTitleView) findViewById(R.id.title_dishonesty);
    }

    @Override
    protected void initWidgetActions() {
        title.setTitleText("失信榜单");
        if (!TextUtils.isEmpty(url))
            webView.loadUrl(url);

    }

}
