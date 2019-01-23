package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.gxc.model.WebModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.gxc.utils.PictureUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Set;

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
    AgentWeb.PreAgentWeb preAgentWeb;
    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.relationGroup)
    Group relationGroup;
    @BindView(R.id.ivBack2)
    View ivBack2;
    @BindView(R.id.vStatus)
    View vStatus;

    private boolean isRelation = false; // 是否为查关系

    private boolean isFullScreen = false;// 网页是否全屏

    private boolean isHttpGetUrl = false;// 是否需要通过请求接口获取链接

    private View errorView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_web;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 在android5.0及以上版本使用webView进行截长图时,默认是截取可是区域内的内容.因此需要在支撑窗体内容之前加上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            WebView.enableSlowWholeDocumentDraw();
        isHttpGetUrl = getIntent().getBooleanExtra("isHttpGetUrl", false);
        isFullScreen = getIntent().getBooleanExtra("isFullScreen", false);

        if (!isHttpGetUrl){
            if (!isFullScreen)
                setTheme(R.style.MyTheme_Layout_Root);
            else
                setTheme(R.style.MyTheme_Layout_Root2);
        }
        else
            setTheme(R.style.MyTheme_Layout_Root2);
        super.onCreate(savedInstanceState);
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

        preAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(layout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(isRelation ? Color.TRANSPARENT : -1)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(errorView)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready();

        if (isHttpGetUrl)
            getUrl();
        else
            mAgentWeb = preAgentWeb.go(url);
    }


    private void getUrl() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", getIntent().getIntExtra("menuType", 0));
        String param1 = getIntent().getStringExtra("name_one");
        String param2 = getIntent().getStringExtra("name_two");
        String param3 = getIntent().getStringExtra("route_num");
        if (!TextUtils.isEmpty(param1))
            map.put("name_one", param1);
        if (!TextUtils.isEmpty(param2))
            map.put("name_two", param2);
        if (!TextUtils.isEmpty(param3))
            map.put("route_num", param3);

        RxManager.http(RetrofitUtils.getApi().getH5Address(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    WebModel webModel = model.dataToObject(WebModel.class);
                    if (webModel != null) {
                        isFullScreen = (webModel.webType == 1);
                        if (!isFullScreen) {
                            setStatusBarFontDark(true);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                vStatus.setVisibility(View.VISIBLE);
                                vStatus.getLayoutParams().height = AppUtils.getStatusHeight();
                            }
                        }
                        ivBack2.setVisibility(isFullScreen ? View.VISIBLE : View.GONE);
                        titleView.setVisibility(isFullScreen ? View.GONE : View.VISIBLE);
                        mAgentWeb = preAgentWeb.go(webModel.H5Address);
                    }
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

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.e("shouldOverrideUrlLoading1111:URL=" + url);
            if (url.startsWith(Constants.URL_PREFIX)) {
                try {
                    String deurl = URLDecoder.decode(url, "UTF-8");
                    LogUtils.e("》》》" + deurl);
                    Uri uri = Uri.parse(deurl);

                    return true;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            LogUtils.e("shouldOverrideUrlLoading:URL=" + view.getUrl());
//            http://202.106.10.250:4808/dist/#/vip?data=9iFQlbMLJeuq9ONpaG%2FVDkFlePca9Rf%2F9v6UTySLWiET1dgisCQG6A%3D%3D


            if (view.getUrl().startsWith("gxc://edit")) {

                Uri uri;
                try {
                    uri = Uri.parse(view.getUrl());
                    String type;
                    Set<String> parameter = uri.getQueryParameterNames();

                    if (parameter != null && parameter.size() > 0 && parameter.contains("type")) {
                        if (!TextUtils.isEmpty(uri.getQueryParameter("type"))) {
                            type = uri.getQueryParameter("type");

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }


            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LogUtils.e("onPageStarted=" + url);
        }
    };

    @Override
    public void onPause() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // 清空缓存
        AgentWebConfig.clearDiskCache(this);
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        if (mAgentWeb != null)
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

    /**
     * @param context
     * @param menuType
     * @param extras   7:查关系  8: 风险分析 16：裁判文书15：中标信息 17：行政处罚 18：商标信息  参数一必填
     *                 参数二  查关系必填
     *                 参数三 路径数  查关系必填
     * @return
     */
    public static Intent getIntent(Context context, int menuType, String... extras) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("menuType", menuType);
        if (extras != null && extras.length > 0) {
            intent.putExtra("name_one", extras[0]);
            if (extras.length > 1)
                intent.putExtra("name_two", extras[1]);
            if (extras.length > 2)
                intent.putExtra("route_num", extras[2]);
        }
        intent.putExtra("isHttpGetUrl", true);
        intent.putExtra("isFullScreen", true);
        return intent;
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
