package com.jusfoun.jusfouninquire.ui.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;

/**
 * @author lee
 * @version create time:2015/11/1215:59
 * @Email email
 * @Description $广告详细页面
 */

public class AdvertisementWebActivity extends BaseInquireActivity{

    /**常量*/
    public static final String WEB_URL = "imageUrl";

    /**组件*/
    private BackAndRightImageTitleView titleView;

    private WebView webView;


    /**对象*/


    /**变量*/
    private String webUrl = "";

    @Override
    protected void initData() {
        super.initData();
        webUrl = getIntent().getStringExtra(WEB_URL);
    }
    @Override
    protected void initView() {
        setContentView(R.layout.activity_adver_web);
        titleView = (BackAndRightImageTitleView) findViewById(R.id.titleView);
        titleView.setTitleText("广告页面");
        webView = (WebView) findViewById(R.id.adver_webview);
    }

    @Override
    protected void initWidgetActions() {
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setSupportZoom(true);
        Log.d("TAG","广告页面：webUrl:"+webUrl);
        webView.loadUrl(webUrl);
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageStarted(WebView view, String url,Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("tag", "onPageStarted");
            showLoading();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.e("tag", "onReceivedError");
            hideLoadDialog();
            Log.d("TAG", "加载失败，或网络异常");
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.getSettings().setJavaScriptEnabled(true);
            Log.e("tag", "onPageFinished");
            hideLoadDialog();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK ){
            goActivity(MainActivity.class);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
