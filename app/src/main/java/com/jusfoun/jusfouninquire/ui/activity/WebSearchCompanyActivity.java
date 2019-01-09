package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.widget.CustomWebViewClent;

import netlib.util.AppUtil;

/**
 * @author lee
 * @version create time:2016/1/2918:05
 * @Email email
 * @Description 企业查询web页面
 */

public class WebSearchCompanyActivity extends BaseInquireActivity {

    public static final String COMPANY_NAME = "companyName";
    public static final String COMPANY_AREA = "companyArea";
    private WebView webView;
    private BackAndRightImageTitleView titleView;
    private String name, area,url;
    private LinearLayout loading;

    private NetWorkErrorView mNetWorkError;
    private CustomWebViewClent webViewClient;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString(COMPANY_NAME);
            area = bundle.getString(COMPANY_AREA);
        }
        Log.e("tag","name="+name+",area="+area);
//        mSwipeBackLayout.setEnableGesture(true);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.web);
        mNetWorkError = (NetWorkErrorView) findViewById(R.id.net_work_error);
        titleView = (BackAndRightImageTitleView) findViewById(R.id.web_title);
        loading= (LinearLayout) findViewById(R.id.loading);
        initWebView();
    }

    @Override
    protected void initWidgetActions() {
        titleView.setTitleText("企业查询");

        url = mContext.getString(R.string.req_url) + mContext.getString(R.string.all_net_search_url)+
                "?Province="+area+"&text="+name+"&appType=0&version="+ AppUtil.getVersionName(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mNetWorkError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                webViewClient.showLoading();
                webView.reload();
            }
        });


        titleView.setmLeftClickListener(new BackAndRightImageTitleView.LeftClickListener() {
            @Override
            public void onClick() {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
        Log.d("TAG","url:"+url);
        webView.loadUrl(url);
    }

    private void initWebView(){
        webViewClient = new CustomWebViewClent(webView,mNetWorkError,mContext,loading);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(chromeClient);
    }

    WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode,event);
    }

}
