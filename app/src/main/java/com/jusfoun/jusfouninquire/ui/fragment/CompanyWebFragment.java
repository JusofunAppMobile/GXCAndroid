package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.service.event.CompanyWebEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.internal.OnWebFragmentListener;
import com.jusfoun.jusfouninquire.ui.util.WebUtil;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.widget.CustomWebViewClent;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/9.
 * Description
 */
public class CompanyWebFragment extends BaseViewPagerFragment {
    public static String URL = "url";
    private WebView web;
    private String url;
    private NetWorkErrorView webError;
    private CompanyDetailModel model;
    private LinearLayout loading;
    private CustomWebViewClent webViewClent;
    private OnWebFragmentListener listener;
    private int position;

    public final static String TAG_SLIDEWEB = "tag_slideweb";

    public static CompanyWebFragment getInstance(Bundle argument) {
        CompanyWebFragment fragment = new CompanyWebFragment();
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_web, container, false);
        web = (WebView) view.findViewById(R.id.web);
        webError = (NetWorkErrorView) view.findViewById(R.id.web_error);
        loading = (LinearLayout) view.findViewById(R.id.loading);
        return view;
    }

    @Override
    protected void initWeightActions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webViewClent = new CustomWebViewClent(web, webError, mContext, loading);
        webViewClent.setOnWebLisener(new CustomWebViewClent.OnWebLisener() {
            @Override
            public void onError(String url, int errorCode) {

            }

            @Override
            public void onPageStart(String url, WebView webView) {
                if (listener != null)
                    listener.onWebViewStart(url, webView);
            }

        });
        web.setWebViewClient(webViewClent);
        web.setBackgroundColor(0);
        WebSettings wSet = web.getSettings();
        wSet.setJavaScriptEnabled(true);
        wSet.setGeolocationEnabled(true);
        wSet.setAppCacheEnabled(false);
        wSet.setBuiltInZoomControls(true);
        wSet.setUseWideViewPort(true);
        wSet.setLoadsImagesAutomatically(true);
        wSet.setSaveFormData(true);
        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        wSet.setSupportZoom(true);
        wSet.setCacheMode(WebSettings.LOAD_NO_CACHE);
        wSet.setDomStorageEnabled(true);
        wSet.setJavaScriptCanOpenWindowsAutomatically(true);
        web.setWebChromeClient(new WebChromeClient());

        webError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                webViewClent.showLoading();
                web.reload();
            }
        });
        WebUtil.getUserAgentString(wSet, mContext);
    }

    @Override
    protected void setViewHint() {

    }

    @Override
    protected void refreshData() {
        Log.e("tag", "refreshData1=="+url);
        Bundle arguments = getArguments();
        if (arguments != null) {
            boolean tagSlideWeb = getArguments().getBoolean(TAG_SLIDEWEB, false);
            if (tagSlideWeb) {
                String url = getArguments().getString("url");
                Log.e("tag", "refreshData2=="+url);
                Log.e(TAG, url);
                webViewClent.setMainUrl(url);
                web.loadUrl(url);
            } else {
                model = (CompanyDetailModel) arguments.getSerializable(CompanyDetailsActivity.COMPANY);
                position = arguments.getInt(CompanyDetailsActivity.POSITION, -1);
                if (position >= 0) {
                    try {
//                    url=URLDecoder.decode(model.getSubclassMenu().get(position).getApplinkurl(), "UTF-8");
                        url = model.getSubclassMenu().get(position).getApplinkurl();
                        Log.e("tag", "refreshData3=="+url);
                        Log.e(TAG, url);
                        webViewClent.setMainUrl(url);
                        web.loadUrl(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnWebFragmentListener) {
            listener = (OnWebFragmentListener) activity;
            listener.onCurrentFragment(this);
        }
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof CompanyWebEvent) {
            CompanyWebEvent companyWebEvent = (CompanyWebEvent) event;
            Bundle bundle = ((CompanyWebEvent) event).getArgument();
            if (bundle != null) {
                model = (CompanyDetailModel) bundle.getSerializable(CompanyDetailsActivity.COMPANY);
                position = bundle.getInt(CompanyDetailsActivity.POSITION, -1);
                if (position >= 0) {
                    try {
                        //url=URLDecoder.decode(model.getSubclassMenu().get(position).getApplinkurl(), "UTF-8");
                        url = model.getSubclassMenu().get(position).getApplinkurl();
                        webViewClent.setMainUrl(url);
                        web.loadUrl(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public WebView getWeb() {
        return web;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (web != null) {
            web.destroy();
        }
    }
}
