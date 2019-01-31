package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.event.WebRefreshEvent;
import com.gxc.model.UserModel;
import com.gxc.model.WebModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.gxc.utils.PictureUtils;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.SearchTitleView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.siccredit.guoxin.R;

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
    @BindView(R.id.errorView)
    NetWorkErrorView emptyView;
    @BindView(R.id.searchTitleView)
    SearchTitleView searchTitleView;
    @BindView(R.id.ivCredit)
    View ivCredit;
    @BindView(R.id.vStatus)
    View vStatus;

    private boolean isCredit; // 企业报告-样本预览

    private boolean isRelation = false; // 是否为查关系

    private boolean isFullScreen = false;// 网页是否全屏

    private boolean isHttpGetUrl = false;// 是否需要通过请求接口获取链接
    private boolean isUserRedSearchTitle = false;// 是否使用红色搜索标题样式

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
        initIntentExtra();
        if (!isHttpGetUrl) {
            if (!isFullScreen)
                setTheme(R.style.MyTheme_Layout_Root);
            else
                setTheme(R.style.MyTheme_Layout_Root2);
        } else
            setTheme(R.style.MyTheme_Layout_Root2);
        super.onCreate(savedInstanceState);
        if (isSetStatusBar() && isFullScreen)
            setStatusBarRed();
    }

    private void initIntentExtra() {
        isRelation = getIntent().getBooleanExtra("isRelation", false);
        isHttpGetUrl = getIntent().getBooleanExtra("isHttpGetUrl", false);
        isFullScreen = getIntent().getBooleanExtra("isFullScreen", false);
        isUserRedSearchTitle = getIntent().getBooleanExtra("isUserRedSearchTitle", false);
        isCredit = getIntent().getBooleanExtra("isCredit", false);
    }

    @Override
    public void initActions() {

        if (!isRelation) // 查关系 开启硬件加速会造成截屏时出现空白
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        ivBack2.setVisibility(isFullScreen ? View.VISIBLE : View.GONE);
        titleView.setVisibility(isFullScreen ? View.GONE : View.VISIBLE);
        if (isUserRedSearchTitle) {
            searchTitleView.setVisibility(View.VISIBLE);
            searchTitleView.setEditText(getIntent().getStringExtra("name_one"));
            searchTitleView.hideRightView();
            setStatusBarEnable(getResources().getColor(R.color.common_red));
            setStatusBarFontDark(false);
        }
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        LogUtils.e("URL=" + url);
        LogUtils.e("TITLE=" + title);


        relationGroup.setVisibility(isRelation ? View.VISIBLE : View.GONE);

        if (isRelation) { // 查关系
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
            titleView.setVisibility(View.GONE);
        }

        if (isCredit)
            ivCredit.setVisibility(View.VISIBLE);
        titleView.setTitle(title);

        errorView = View.inflate(this, R.layout.view_empty, null);
        errorView.setVisibility(View.VISIBLE);
        TextView textView = errorView.findViewById(R.id.tvEmpty);
        textView.setText("刷新试试吧~");
        errorView.findViewById(R.id.tvError).setVisibility(View.VISIBLE);
        errorView.findViewById(R.id.tvReload).setVisibility(View.VISIBLE);

        preAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(layout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
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
        emptyView.showLoading();
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
                if (model.success()) {
                    emptyView.success();
                    WebModel webModel = model.dataToObject(WebModel.class);
                    if (webModel != null) {
                        isFullScreen = (webModel.webType == 1);
                        if (!isFullScreen) {
                            if (!isUserRedSearchTitle)
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
                    emptyView.error();
                }
            }

            @Override
            public void error() {
                emptyView.error();
            }
        });
    }

    @OnClick(R.id.errorView)
    public void emptyClick() {
        getUrl();
    }

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.e("shouldOverrideUrlLoading:URL=" + url);
            try {
                if (url.startsWith("gxc://edit")) { // 自主填报
                    Uri uri;
                    uri = Uri.parse(url);
                    int type;
                    Set<String> parameter = uri.getQueryParameterNames();
                    if (parameter != null && parameter.size() > 0 && parameter.contains("type")) {
                        if (!TextUtils.isEmpty(uri.getQueryParameter("type"))) {
                            type = Integer.parseInt(uri.getQueryParameter("type"));
                            LogUtils.e("type=" + type);
                            Intent intent = new Intent(WebActivity.this, EditReportInfoActivity.class);
                            intent.putExtra(EditReportInfoActivity.TYPE, type);
                            if (type == EditReportInfoActivity.TYPE_INFO) {
                                intent.putExtra(EditReportInfoActivity.ID, uri.getQueryParameter("companyId"));
                            } else if (type == EditReportInfoActivity.TYPE_PRODUCE) {
                                intent.putExtra(EditReportInfoActivity.ID, uri.getQueryParameter("productId"));
                            } else if (type == EditReportInfoActivity.TYPE_RY) {
                                intent.putExtra(EditReportInfoActivity.ID, uri.getQueryParameter("honorId"));
                            } else if (type == EditReportInfoActivity.TYPE_HB) {
                                intent.putExtra(EditReportInfoActivity.ID, uri.getQueryParameter("partnerId"));
                            } else if (type == EditReportInfoActivity.TYPE_CY) {
                                intent.putExtra(EditReportInfoActivity.ID, uri.getQueryParameter("empId"));
                            }

                            startActivity(intent);
                        }
                    }

                    return true;
                } else if (url.startsWith("gxc://vip")) { // VIP 开通
                    UserModel user = AppUtils.getUser();
                    if (user == null) {
                        startActivity(LoginActivity.class);
                        return true;
                    }
                    startActivity(PayActivity.class);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
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


    /**
     * 企业报告-样本预览
     *
     * @param context
     * @param url
     * @return
     */
    public static Intent getCreditIntent(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", "企业报告");
        intent.putExtra("url", url);
        intent.putExtra("isCredit", true);
        return intent;
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
    public static Intent getIntent(Context context, String title, int menuType, String... extras) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("menuType", menuType);
        intent.putExtra("title", title);
        if (extras != null && extras.length > 0) {
            intent.putExtra("name_one", extras[0]);
            if (extras.length > 1)
                intent.putExtra("name_two", extras[1]);
            if (extras.length > 2)
                intent.putExtra("route_num", extras[2]);
        }
        intent.putExtra("isHttpGetUrl", true);
        intent.putExtra("isFullScreen", true);
        intent.putExtra("isUserRedSearchTitle", isUserRedSearchTitle(menuType));
        return intent;
    }

    public static Intent getIntent(Context context, int menuType, String... extras) {
        return getIntent(context, null, menuType, extras);
    }

    // 15 ：中标信息  16：裁判文书 17：行政处罚  18：商标查询
    private static boolean isUserRedSearchTitle(int menuType) {
        switch (menuType) {
            case 15:
            case 16:
            case 17:
            case 18:
                return true;
        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        if (isUserRedSearchTitle)
            overridePendingTransition(0, 0);
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
        if (isUserRedSearchTitle)
            return true;
//        if (isFullScreen)
//            return false;
        return super.isSetStatusBar();
    }

    @Override
    public boolean isBarDark() {
        if (isUserRedSearchTitle)
            return false;
        if (isFullScreen)
            return false;
        return super.isBarDark();
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof WebRefreshEvent && mAgentWeb != null)
            mAgentWeb.getWebCreator().getWebView().reload();
    }
}
