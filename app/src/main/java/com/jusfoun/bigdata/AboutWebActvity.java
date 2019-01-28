package com.jusfoun.bigdata;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.util.WebUtil;
import com.siccredit.guoxin.R;


public class AboutWebActvity extends BaseActivity {

    //常量
    public static final String TYPE = "type";
    public static final String WEB_URL = "webType";
    public static final String TITLE_NAME = "titleName";
    public static final int EXACT_SEARCH = 6; // 精确查找
    public static final int DATA_MAIN = 7; // 数据链接主入口


    //组件
    BackAndRightTitleViewNew titleView;

    WebView webView;

    RelativeLayout progress_layout;

    //变量
    private int webType;
    private String titleNameText;
    private String url;
    private boolean isError = false;

    //对象
    @Override
    public void initData() {
        webType = getIntent().getIntExtra(TYPE, DATA_MAIN);
//        if (webType == EXACT_SEARCH) { // 精确查找
//            titleNameText = "精确查询";
//            url = getString(R.string.req_url) + getResources().getString(R.string.url_exact_search);
//        } else if (webType == DATA_MAIN) { // 数据链接主入口
//            titleNameText = "数据";
//            url = getString(R.string.req_url) + getResources().getString(R.string.url_data_main);
//        }
    }

    public static void startActivity(Context context, int webType) {
        Intent intent = new Intent(context, AboutWebActvity.class);
        intent.putExtra(AboutWebActvity.TYPE, webType);
        context.startActivity(intent);
    }

    @Override
    public void onEvent(IEvent event) {
        if (webType != DATA_MAIN)
            super.onEvent(event);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_about_our_webview);
    }

    @Override
    public void doBusiness() {
        titleView.setTitle(titleNameText);
        titleView.setLeftImage("skin_icon_back", R.drawable.skin_icon_back_black);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setSupportZoom(true);
        WebUtil.getUserAgentString(webView.getSettings(), this);

        if (!url.contains("?")) {
            url += "?";
        }
//        if (SkinManager.getInstance().isExternalSkin()) {
//            Logger.e("TAG", "url:" + url + "&skinType=1");
//            webView.loadUrl(url + "&skinType=1");
//        } else {
//            Logger.e("TAG", "url:" + url + "&skinType=0");
//            webView.loadUrl(url + "&skinType=0");
//        }
//
//        progress_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                isError = false;
//                if (SkinManager.getInstance().isExternalSkin()) {
//                    Logger.e("TAG", "url:" + url + "&skinType=1");
//                    webView.loadUrl(url + "&skinType=1");
//                } else {
//                    Logger.e("TAG", "url:" + url + "&skinType=0");
//                    webView.loadUrl(url + "&skinType=0");
//                }
//            }
//        });
    }

//    @SuppressLint("SetJavaScriptEnabled")
//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Logger.e("WebView", url);
//            if (url.startsWith(WebViewDealConstant.PROTOCOL)) {
//                try {
//                    String deurl = URLDecoder.decode(url, "UTF-8");
//                    Logger.e(deurl);
//                    Uri uri = Uri.parse(deurl);
//                    if (url.startsWith(WebViewDealConstant.RISKCONTROL)) {
//                        String productname = uri.getQueryParameter(WebViewDealConstant.PRODUCTNAME); // 产品及服务
//                        String entname = uri.getQueryParameter(WebViewDealConstant.ENTNAME); // 企业名称
//                        String legalname = uri.getQueryParameter(WebViewDealConstant.LEGALNAME); // 创始人/股东
//                        String entaddress = uri.getQueryParameter(WebViewDealConstant.ENTADDRESS); // 街道
//                        String officehouse = uri.getQueryParameter(WebViewDealConstant.OFFICEHOUSE); // 写字楼
//                        String incomemoney = uri.getQueryParameter(WebViewDealConstant.INCOMEMONEY); // 营收入规模
//                        String totalmoney = uri.getQueryParameter(WebViewDealConstant.TOTALMONEY); // 资产规模
//                        String totalnum = uri.getQueryParameter(WebViewDealConstant.TOTALNUM); // 人员规模
//                        String regmoney = uri.getQueryParameter(WebViewDealConstant.REGMONEY); // 资金规模
//                        String regtime = uri.getQueryParameter(WebViewDealConstant.REGTIME); // 注册时间
//                        String areacode = uri.getQueryParameter(WebViewDealConstant.AREACODE); // 所属区域
//                        String areaname = uri.getQueryParameter(WebViewDealConstant.AREANAME); // 所属区域
//                        String industrycode = uri.getQueryParameter(WebViewDealConstant.INDUSTRYCODE); // 行业类型
//                        String industryname = uri.getQueryParameter(WebViewDealConstant.INDUSTRYNAME); // 行业类型
//
//                        String[] filterValue = new String[12];
//                        String[] filterName = new String[12];
//
//                        filterValue[0] = productname; // 产品及服务
//                        filterName[0] = productname;
//
//                        filterValue[1] = entname;  // 企业名称
//                        filterName[1] = entname;
//
//                        filterValue[2] = legalname; // 创始人/股东
//                        filterName[2] = legalname;
//
//                        filterValue[3] = industrycode;  // 行业类型
//                        filterName[3] = industryname;
//
//                        filterValue[4] = areacode; // 所属区域
//                        filterName[4] = areaname;
//
//                        filterValue[5] = entaddress; // 街道
//                        filterName[5] = entaddress;
//
//                        filterValue[6] = officehouse;  // 写字楼
//                        filterName[6] = officehouse;
//
//                        filterValue[7] = incomemoney;
//                        filterName[7] = getTagByValue(incomemoney, 1); // 1: 营收规模
//
//                        filterValue[8] = totalmoney;
//                        filterName[8] = getTagByValue(totalmoney, 2); // 2 资产规模
//
//                        filterValue[9] = totalnum;
//                        filterName[9] = getTagByValue(totalnum, 3); // 3 人员规模
//
//                        filterValue[10] = regtime;
//                        filterName[10] = getTagByValue(regtime, 4); // 4 注册时间
//
//                        filterValue[11] = regmoney;
//                        filterName[11] = getTagByValue(regmoney, 5); // 5 注册资金
//
//                        // 所属区域
//                        DataAreaModel areaModel = null;
//                        try {
//                            areaModel = new DataAreaModel();
//                            if (areaname != null && areaname.contains(",")) {
//                                List<AreaModel> list = new ArrayList<>();
//                                for (int i = 0; i < areaname.split(",").length; i++) {
//                                    AreaModel model = new AreaModel();
//                                    model.setId(Integer.parseInt(areacode.split(";")[i]));
//                                    model.setNameShortCn(areaname.split(",")[i]);
//                                    list.add(model);
//                                }
//                                areaModel.setDatalist(list);
//                            } else if (!TextUtils.isEmpty(areaname)) {
//                                List<AreaModel> list = new ArrayList<>();
//                                AreaModel model = new AreaModel();
//                                model.setId(Integer.parseInt(areacode));
//                                model.setNameShortCn(areaname);
//                                list.add(model);
//                                areaModel.setDatalist(list);
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                        //行业类型
//                        DataIndustryModel industryModel = null;
//                        try {
//                            industryModel = new DataIndustryModel();
//                            if (industryname != null && industryname.contains(",")) {
//                                List<IndustryModel> industryModelList = new ArrayList<>();
//                                for (int i = 0; i < industryname.split(",").length; i++) {
//                                    IndustryModel model = new IndustryModel();
//                                    model.setCountryClassificationCode(industrycode.split(";")[i]);
//                                    model.setCountryClassName(industryname.split(",")[i]);
//                                    industryModelList.add(model);
//                                }
//                                industryModel.setDatalist(industryModelList);
//                            }else if (!TextUtils.isEmpty(industryname)) {
//                                List<IndustryModel> industryModelList = new ArrayList<>();
//                                IndustryModel model = new IndustryModel();
//                                model.setCountryClassificationCode(industrycode);
//                                model.setCountryClassName(industryname);
//                                industryModelList.add(model);
//                                industryModel.setDatalist(industryModelList);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        startActivity(BigDataListActivity.getIntent(context, filterValue, filterName, "", areaModel, industryModel));
//                        return true;
//                    } else if (url.startsWith(WebViewDealConstant.GETLOCATIONT)) {
//                        Logger.e("定位...");
//                        // 待处理................
//                        return true;
//                    } else if (url.startsWith(WebViewDealConstant.PRECISETOAPP)) {  // 精准营销
//                        startActivity(new Intent(context, NearMapActivity.class));
//                        return true;
//                    } else if (url.startsWith(WebViewDealConstant.RISKCONTROLRECOMMEND) || url.startsWith(WebViewDealConstant.ACCURATESEARCH)) {  //风险控制-推荐企业 //精确查询列表点击
//                        String entid = uri.getQueryParameter(WebViewDealConstant.ENT_ID);
//                        String entname = uri.getQueryParameter(WebViewDealConstant.ENTNAME);
//                        Intent intent = new Intent(context, CompanyNewDetailActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("ent_id", entid);
//                        bundle.putString("entname", entname);
//                        intent.putExtra("SelectModel", bundle);
//                        context.startActivity(intent);
//                        return true;
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            return super.shouldOverrideUrlLoading(view, url);
//        }
//
//        /**
//         * type 1: 营收规模 2 资产规模 3 人员规模  4 注册时间 5 注册资金
//         *
//         * @param type
//         * @return
//         */
//
//        private String getTagByValue(String value, int type) {
//
//            if (value == null)
//                return null;
//
//            String[] tags = getTags(type);
//            String[] values = getValues(type);
//
//            List<String> splitList = Arrays.asList(value.split(";"));
//            StringBuffer stringBuffer = new StringBuffer();
//            if (tags != null) {
//                for (int i = 0; i < values.length; i++) {
//                    if ((type == 3 || type == 4) && values[i].equals(value)) // 人员规模 、 注册时间 单选
//                        return tags[i];
//                    if (splitList.contains(values[i]))
//                        stringBuffer.append(tags[i] + " ");
//                }
//            }
//            return stringBuffer.toString();
//        }
//
//        private String[] getTags(int type) {
//            if (type == 1) { // 营收规模
//                return getResources().getStringArray(R.array.income_tag);
//            } else if (type == 2) { // 资产规模
//                return getResources().getStringArray(R.array.assets_tag);
//            } else if (type == 3) { //人员规模
//                return getResources().getStringArray(R.array.perple_num_tag);
//            } else if (type == 4) { // 注册时间
//                return getResources().getStringArray(R.array.createtime_tag);
//            } else if (type == 5) { //注册资金
//                return getResources().getStringArray(R.array.register_fund);
//            }
//            return null;
//        }
//
//        private String[] getValues(int type) {
//            if (type == 1) { // 营收规模
//                return getResources().getStringArray(R.array.income_value);
//            } else if (type == 2) { // 资产规模
//                return getResources().getStringArray(R.array.assets_value);
//            } else if (type == 3) { //人员规模
//                return getResources().getStringArray(R.array.perple_num_value);
//            } else if (type == 4) { // 注册时间
//                return getResources().getStringArray(R.array.createtime_value);
//            } else if (type == 5) { //注册资金
//                return getResources().getStringArray(R.array.register_fund_value);
//            }
//            return null;
//        }
//
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            view.getSettings().setJavaScriptEnabled(true);
//            progress_layout.setVisibility(View.GONE);
//            webView.setVisibility(View.GONE);
//            showLoadDialog();
//            super.onPageStarted(view, url, favicon);
//
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            view.getSettings().setJavaScriptEnabled(true);
//            //progressLayout.setVisibility(View.GONE);
//            hideLoadDialog();
//            if (isError) {
//                webView.setVisibility(View.GONE);
//                progress_layout.setVisibility(View.VISIBLE);
//            } else {
//                webView.setVisibility(View.VISIBLE);
//                progress_layout.setVisibility(View.GONE);
//            }
//
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode,
//                                    String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
//            isError = true;
//            Logger.d("TAG", "加载失败，或网络异常");
//        }
//
//    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }
}
