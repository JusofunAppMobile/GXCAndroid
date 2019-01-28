package com.jusfoun.jusfouninquire.ui.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午5:41
 * @Email zyp@jusfoun.com
 * @Description ${常见问题}
 */
public class CommonProblemActivity extends BaseInquireActivity {

    /**
     * 常量
     */
    public static final String TYPE = "type";
    public static final String APP_URL = "app_url";
    public static final String AD_TITLE = "ad_title";
    public static final int APP_TYPE = 1;
    public static final int COMMOMPROBLEM_TYPE = 2;
    public static final int AD_TYPE = 3;


    /**
     * 问卷调查
     */
    public static final int QUESTION_TYPE = 4;
    public static final String QURSION__TITLE = "quesion_titile";
    public static final String QUESION_URL = "quesion_url";


    /**
     * 组件
     */
    private TitleView titleView;
    private WebView webView;
    private View netErrorLayout;

    /**
     * 变量
     */
    private int type;
    private String appUrl;
    private String title;

    /**
     * 对象
     */


    @Override
    protected void initData() {
        type = getIntent().getIntExtra(TYPE, 0);
        if (type == APP_TYPE) {
            appUrl = getIntent().getStringExtra(APP_URL);
            Log.d("TAG", "appurl++++++" + appUrl);
        }

        if (type == AD_TYPE) {
            appUrl = getIntent().getStringExtra(APP_URL);
            title = getIntent().getStringExtra(AD_TITLE);
        }

        if (type == QUESTION_TYPE) {
            appUrl = getIntent().getStringExtra(APP_URL);
            title = getIntent().getStringExtra(QURSION__TITLE);
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_common_problem);

        titleView = (TitleView) findViewById(R.id.titleView);


        if (type == APP_TYPE) {
            titleView.setTitle("推荐应用");
        } else if (type == COMMOMPROBLEM_TYPE) {
            titleView.setTitle("常见问题");
        } else if (type == AD_TYPE) {
            titleView.setTitle(title);
        } else if (type == QUESTION_TYPE) {
            titleView.setTitle(title);
        }
        webView = (WebView) findViewById(R.id.webview);
        netErrorLayout = findViewById(R.id.netErrorLayout);
    }

    @Override
    protected void initWidgetActions() {
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setSupportZoom(true);
        if (type == APP_TYPE || type == AD_TYPE || type == QUESTION_TYPE) {
            webView.loadUrl(appUrl + "");

        } else if (type == COMMOMPROBLEM_TYPE) {
            webView.loadUrl(getResources().getString(R.string.req_url) + "/Html/cjwt.html");
        }

        netErrorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoading();
            netErrorLayout.setVisibility(View.GONE);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.e("tag", "onReceivedError");
            Log.d("TAG", "加载失败，或网络异常");
            netErrorLayout.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideLoadDialog();
            view.getSettings().setJavaScriptEnabled(true);
            Log.e("tag", "onPageFinished");
            webView.setVisibility(View.VISIBLE);
        }
    }

}
