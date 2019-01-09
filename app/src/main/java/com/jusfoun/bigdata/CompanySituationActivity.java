package com.jusfoun.bigdata;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.jusfoun.bigdata.http.NearMapSource;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.view.PhoneCallDialog;
import com.jusfoun.jusfouninquire.ui.view.SlidingUpPanelLayout;

import java.util.HashMap;



/**
 * @author lee
 * @version create time:2016/3/39:32
 * @Email email
 * @Description $企业概况页面
 */

public class CompanySituationActivity extends BaseActivity implements LocationUtil.MyLocationListener, WebContentConstant {

    /**
     * 组件
     */
    BackAndRightTitleViewNew titleView;

    ScrollView mScrollView;

    MapView mMapView;

    TextView nametextview;

    TextView area;

    TextView server;

    TextView assets_value;

    TextView income_value;

    TextView company_address;

    TextView call_phoneView;

    TextView company_infoView;

    TextView registerNumView;

    TextView companyTypeView;

    TextView corporateType;

    TextView corporateView;

    TextView registerCapitaView;

    TextView registerDateView;

    TextView businessStateView;

    TextView legalNumView;

    TextView investmentNumView;

    TextView keyPersonalNumView;

    TextView ICchangeNumICchangeNumView;

    TextView lawsuitNumView;

    TextView punishNumView;

    TextView collectCompanyText;

    View seeDetailLayout;

    RelativeLayout collectCompanyLayout;

    RelativeLayout backLayout;

    SlidingUpPanelLayout slidingUpPanelLayout;


//    PhoneCallDialog dialog;

    TextView failedText;

    View netErrorView;

    View noDataView;

    LinearLayout companyInfoLayout;

    ImageView backImageView;

    ImageView collectCompanyImage;

    TextView rightText;

    LinearLayout addressLayout;


    /**
     * 对象
     */
    private LocationUtil locationUtil;
    public BaiduMap mBaiduMap;
    /**
     * 变量
     */
    private float height = 0, titleViewHeight = 0;
    private String entId = "", entName = "";
    private String map_lat = "", map_lng = "";
    private String callNumber = "";
    private int screenHeight = 0;
    private float bili, bili2, alpha;
    private boolean isCollect = false;
    private String favoriteId = "";


    private NearMapSource nearMapSource;

    @Override
    public void initData() {
//        dialog = new PhoneCallDialog(this, R.style.my_dialog);
        locationUtil = new LocationUtil(this);
        Bundle bundle = getIntent().getBundleExtra(SELECT_MODEL);
        entId = bundle.getString(ENT_ID);
        entName = bundle.getString(ENT_NAME);
        map_lat = bundle.getString(MAP_LAT);
        map_lng = bundle.getString(MAP_LNG);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenHeight = metric.heightPixels;

        nearMapSource = new NearMapSource();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_company_situation);

        titleView = (BackAndRightTitleViewNew) findViewById(R.id.titleView);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mMapView = (MapView) findViewById(R.id.bmapView);
        nametextview = (TextView) findViewById(R.id.nametextview);
        area = (TextView) findViewById(R.id.area);
        server = (TextView) findViewById(R.id.server);
        assets_value = (TextView) findViewById(R.id.assets_value);
        income_value = (TextView) findViewById(R.id.income_value);
        company_address = (TextView) findViewById(R.id.company_address);
        call_phoneView = (TextView) findViewById(R.id.call_phoneView);
        company_infoView = (TextView) findViewById(R.id.company_infoView);
        registerNumView = (TextView) findViewById(R.id.registerNumView);
        companyTypeView = (TextView) findViewById(R.id.companyTypeView);
        corporateType = (TextView) findViewById(R.id.corporateType);
        corporateView = (TextView) findViewById(R.id.corporateView);
        registerCapitaView = (TextView) findViewById(R.id.registerCapitaView);
        registerDateView = (TextView) findViewById(R.id.registerDateView);
        businessStateView = (TextView) findViewById(R.id.businessStateView);
        legalNumView = (TextView) findViewById(R.id.legalNumView);
        investmentNumView = (TextView) findViewById(R.id.investmentNumView);
        keyPersonalNumView = (TextView) findViewById(R.id.keyPersonalNumView);
        ICchangeNumICchangeNumView = (TextView) findViewById(R.id.ICchangeNumICchangeNumView);
        lawsuitNumView = (TextView) findViewById(R.id.lawsuitNumView);
        punishNumView = (TextView) findViewById(R.id.punishNumView);
        collectCompanyText = (TextView) findViewById(R.id.collectCompanyText);
        seeDetailLayout = findViewById(R.id.seeDetailLayout);
        collectCompanyLayout = (RelativeLayout) findViewById(R.id.collectCompanyLayout);
        backLayout = (RelativeLayout) findViewById(R.id.backLayout);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        failedText = (TextView) findViewById(R.id.failedText);
        netErrorView = findViewById(R.id.netErrorLayout);
        noDataView = findViewById(R.id.nodataLayout);
        companyInfoLayout = (LinearLayout) findViewById(R.id.companyInfoLayout);
        backImageView = (ImageView) findViewById(R.id.backView);
        collectCompanyImage = (ImageView) findViewById(R.id.collectCompanyImage);
        rightText = (TextView) findViewById(R.id.text_right);
        addressLayout =(LinearLayout)findViewById(R.id.layout_address);


    }

    @Override
    public void doBusiness() {

//        titleView.setLeftImage("skin_icon_back", R.drawable.skin_icon_back_black);
        titleView.setTitle("企业概况");
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

//        titleView.getRightText().setText(Html.fromHtml("数据<br>栏目"));
//        titleView.getRightText().setTextColor(Color.parseColor("#DF0202"));
//        titleView.getRightText().setBackgroundColor(Color.TRANSPARENT);
//        titleView.getRightText().setVisibility(View.VISIBLE);
//        titleView.getRightText().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().post(new DataColumnEvent());
//                finish();
//            }
//        });


//        rightText.setText(Html.fromHtml("数据<br>栏目"));
//        rightText.setTextColor(Color.parseColor("#DF0202"));
//        rightText.setBackgroundColor(Color.TRANSPARENT);
//        rightText.setVisibility(View.VISIBLE);
//        rightText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().post(new DataColumnEvent());
//                finish();
//            }
//        });


        UiSettings uiSettings = mBaiduMap.getUiSettings();
        uiSettings.setOverlookingGesturesEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        height = PhoneUtil.dip2px(this, 200);
        titleViewHeight = PhoneUtil.dip2px(this, 48);
        bili = titleViewHeight / (screenHeight - height);
        bili2 = 1.0f - bili;
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.d("TAG", "slideOffset:::::::::::" + slideOffset);
                //设置title
                if (slideOffset >= bili2) {
                    alpha = (slideOffset - bili2) / bili;
                    titleView.setAlpha(alpha);
                    backImageView.setAlpha(1.0f - alpha);
                    backLayout.setAlpha(1.0f - alpha);
                } else {
                    titleView.setAlpha(0.0f);
                    backImageView.setAlpha(1.0f);
                    backLayout.setAlpha(1.0f);
                }
            }

            @Override
            public void onPanelCollapsed(View panel) {

            }

            @Override
            public void onPanelExpanded(View panel) {

            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });

        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpPanelLayout.startStateViewInit();
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        seeDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CompanyDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(CompanyDetailActivity.COMPANY_ID, entId);
                bundle.putString(CompanyDetailActivity.COMPANY_NAME, entName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//        collectCompanyLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showLoadDialog();
//                final int mode;
//                HashMap<String, String> params = new HashMap<>();
//                if (isCollect) {
//                    params.put("operatetype", "delete");
//                    params.put("favoriteid", favoriteId);
//                    mode = 1;
//                } else {
//                    params.put("operatetype", "add");
//                    mode = 2;
//                }
//                params.put("entid", entId);
//
//                HttpManager.getInstance()
//                        .get(UrlConstant.MycollectionOperate, params, new CommonCallback<FavoriterUpdateResultModel>() {
//                            @Override
//                            public void onSuccess(FavoriterUpdateResultModel response, Object... obj) {
//                                updateView(response, mode);
//                            }
//
//                            @Override
//                            public void onFail(String message, int code, Object... obj) {
//                                Toaster.showToast(message);
//                            }
//
//                            @Override
//                            public void onAfter() {
//                                super.onAfter();
//                                hideLoadDialog();
//                            }
//                        });
//            }
//        });

        call_phoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(callNumber) || "-".equals(callNumber) ||
                        "—".equals(callNumber) || "_".equals(callNumber)) {
                    return;
                }
//                dialog.setImageVisiable(true);
//                dialog.setButtonText("拨打", "取消");
//                dialog.setText("拨打电话", callNumber + "");
//                dialog.setListener(new CallPhoneDialog.callBack() {
//                    @Override
//                    public void onLeftClick() {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse("tel:" + callNumber));
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onRightClick() {
//
//                    }
//                });
//                dialog.show();

                new PhoneCallDialog(CompanySituationActivity.this, callNumber).show();
            }
        });

        netErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });

        getMyLocation();
        addPoiOverlay();
    }

    /**
     * 设置我的位置图标
     */
    private void locationOption(BDLocation location) {
        // 设置自定义定位图标
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location);

        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker));
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationData locData = new MyLocationData.Builder()
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
    }

    /**
     * 添加PioOverlay
     */
    protected void addPoiOverlay() {
        mBaiduMap.clear();
        LatLng latlng = null;
        float level = 18f;
        BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.more_company);

        MarkerOptions markerOptions = null;// wangchenchen

        if (TextUtils.isEmpty(map_lat) || TextUtils.isEmpty(map_lng)) {
            return;
        }
        latlng = new LatLng(Double.parseDouble(map_lat), Double.parseDouble(map_lng));

        markerOptions = new MarkerOptions().position(latlng).icon(bd);
        mBaiduMap.addOverlay(markerOptions);

        // 设定中心点坐标
        // 定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latlng).zoom(level).build();
        // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
        bd.recycle();
    }

    /**
     * 以历史定位坐标获取数据
     */
    private void getMyLocation() {
        BDLocation location = InquireApplication.getLocationData();
        if (location != null) {
            getCompanySituationData(location);
            locationOption(location);
        } else {
            locationUtil.startLocation();
            showLoadDialog();
        }
    }

    /**
     * 获取数据
     *
     * @param location
     */
    private void getCompanySituationData(BDLocation location) {
        HashMap<String, String> params = new HashMap<>();
        params.put("entid", entId);
        params.put("entname", entName);
        params.put("mycoordinate", location.getLatitude() + "," + location.getLongitude());
        params.put("mapLng", map_lng);
        params.put("mapLat", map_lat);
        if (LoginSharePreference.getUserInfo(this) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(this).getUserid())) {
            params.put("userid", InquireApplication.getUserInfo().getUserid());
        } else {
            params.put("userid", "");
        }
        showLoadDialog();

        nearMapSource.getCompanySituationData(this, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                if (data != null)
                    dealSituation((BaseCompanySituationModel) data);
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                Toaster.showToast(error);
                showNetErrorLayout(true);
            }
        });


    }

    private void dealSituation(BaseCompanySituationModel model) {
        showNetErrorLayout(false);
        if (model.getData() != null) {
            setViewData(model.getData());
            // 初始化成中间显示，根据需求 添加的。
            slidingUpPanelLayout.initStateView();
        } else {
            noDataView.setVisibility(View.VISIBLE);
            titleView.setAlpha(1.0f);
        }
        mScrollView.setVisibility(View.VISIBLE);
    }


    protected void updateView(Object object, int mode) {
        hideLoadDialog();
        if (mode == 1) {
            isCollect = false;
            favoriteId = "";
            collectCompanyText.setText("收藏");
            collectCompanyImage.setImageResource(R.drawable.icon_collection_blue);
        }
        if (mode == 2) {
            FavoriterUpdateResultModel model = (FavoriterUpdateResultModel) object;
            isCollect = true;
            favoriteId = model.getFavoriteid();
            collectCompanyText.setText("已收藏");
            collectCompanyImage.setImageResource(R.drawable.rellread_collect_situation_blue);
        }
    }

    /**
     * 设置网络异常页面是否显示
     *
     * @param isShow
     */
    private void showNetErrorLayout(boolean isShow) {
        if (isShow) {
            netErrorView.setVisibility(View.VISIBLE);
            failedText.setText(getString(R.string.register_login_neterror));
            titleView.setAlpha(1.0f);
            backLayout.setAlpha(0.0f);
            backImageView.setAlpha(0.0f);
        } else {
            netErrorView.setVisibility(View.GONE);
            titleView.setAlpha(0.0f);
            backLayout.setAlpha(1.0f);
            backImageView.setAlpha(1.0f);
        }
    }

    //为view 添加数据
    private void setViewData(CompanySituationModel model) {
        if (!TextUtils.isEmpty(model.getEntname())) {
            nametextview.setText(model.getEntname());
        } else {
            nametextview.setText("公司");
        }

        if (!TextUtils.isEmpty(model.getArea())) {
            if (!TextUtils.isEmpty(model.getDistince())) {
                area.setText(model.getDistince() +" | " + model.getArea());
            } else {
                area.setText(model.getArea());
            }
        } else {
            if (!TextUtils.isEmpty(model.getDistince()) ) {
                area.setText(model.getDistince() );
            } else {
                area.setText("暂无");
            }
        }

//        if (!TextUtils.isEmpty(model.getIndustry())) {
//            server.setText(model.getIndustry());
//        } else {
//            server.setText("暂无");
//        }


        if (!TextUtils.isEmpty(model.getIndustry())) {
            server.setText(model.getCorporateName());
        } else {
            server.setText("暂无");
        }


        if (!TextUtils.isEmpty(model.getTotalAssetsu()) && !TextUtils.isEmpty(model.getTotalAssetsuUit())) {
            assets_value.setText(model.getTotalAssetsu() + model.getTotalAssetsuUit());
        } else {
            assets_value.setText("暂无");
        }
        if (!TextUtils.isEmpty(model.getRevenue()) && !TextUtils.isEmpty(model.getRevenueUnit())) {
            income_value.setText(model.getRevenue() + model.getRevenueUnit());
        } else {
            income_value.setText("暂无");
        }
        if (!TextUtils.isEmpty(model.getAddress())) {
            company_address.setText(model.getAddress());
        } else {
            company_address.setText("-");
        }
        if (!TextUtils.isEmpty(model.getCallNum())) {
            callNumber = model.getCallNum();
            call_phoneView.setText(model.getCallNum());
        } else {
            callNumber = "";
            call_phoneView.setText("-");
        }
        if (!TextUtils.isEmpty(model.getEntBriefIntroduction())) {
            company_infoView.setText(model.getEntBriefIntroduction());
            companyInfoLayout.setVisibility(View.VISIBLE);
        } else {
            company_infoView.setText("");
            companyInfoLayout.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(model.getRegisterNum())) {
            registerNumView.setText(model.getRegisterNum());
        } else {
            registerNumView.setText("-");
        }
        if (!TextUtils.isEmpty(model.getEntType())) {
            companyTypeView.setText(model.getEntType());
        } else {
            companyTypeView.setText("-");
        }
        if (!TextUtils.isEmpty(model.getCorporateType())) {
            corporateType.setText(model.getCorporateType());
        } else {
            corporateType.setText("法人");
        }
        if (!TextUtils.isEmpty(model.getCorporateName())) {
            corporateView.setText(model.getCorporateName());
        } else {
            corporateView.setText("-");
        }
        if (!TextUtils.isEmpty(model.getRegisterCapital()) && !TextUtils.isEmpty(model.getRegisterCapitalUnit())) {
            registerCapitaView.setText(model.getRegisterCapital() + model.getRegisterCapitalUnit());
        } else {
            registerCapitaView.setText("-");
        }
        if (!TextUtils.isEmpty(model.getRegisterDate())) {
            registerDateView.setText(model.getRegisterDate());
        } else {
            registerDateView.setText("-");
        }
        if (!TextUtils.isEmpty(model.getBusinessState())) {
            businessStateView.setText(model.getBusinessState());
        } else {
            businessStateView.setText("-");
        }
        if (!TextUtils.isEmpty(model.getShareholderNum())) {
            legalNumView.setText(model.getShareholderNum());
        } else {
            legalNumView.setText("0");
        }
        if (!TextUtils.isEmpty(model.getInvestmentNum())) {
            investmentNumView.setText(model.getInvestmentNum());
        } else {
            investmentNumView.setText("0");
        }
        if (!TextUtils.isEmpty(model.getMainPersonalNum())) {
            keyPersonalNumView.setText(model.getMainPersonalNum());
        } else {
            keyPersonalNumView.setText("-");
        }
        if (!TextUtils.isEmpty(model.getICchangeNum())) {
            ICchangeNumICchangeNumView.setText(model.getICchangeNum());
        } else {
            ICchangeNumICchangeNumView.setText("-");
        }
        if (!TextUtils.isEmpty(model.getLawsuitNum())) {
            lawsuitNumView.setText(model.getLawsuitNum());
        } else {
            lawsuitNumView.setText("-");
        }

        if (!TextUtils.isEmpty(model.getIsCollect())) {
            if ("1".equals(model.getIsCollect())) {
                collectCompanyText.setText("已收藏");
                collectCompanyImage.setImageResource(R.drawable.rellread_collect_situation_blue);
                favoriteId = model.getFavoriteId();
                isCollect = true;
            } else {
                collectCompanyText.setText("收藏");
                collectCompanyImage.setImageResource(R.drawable.icon_collection_blue);
                favoriteId = "";
                isCollect = false;
            }
        }

        if (!TextUtils.isEmpty(model.getPunishNum())) {
            punishNumView.setText(model.getPunishNum());
        } else {
            punishNumView.setText("-");
        }

    }

    @Override
    public void locationSucc(BDLocation location) {
        hideLoadDialog();
        InquireApplication.setLocationData(location);
        getCompanySituationData(location);
    }

    @Override
    public void locationFail() {
        hideLoadDialog();
        Toaster.showToast("定位失败，请检查是否开启了位置信息或点击左下角再次尝试");
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof DeleteFavoriteEvent) {
            if (((DeleteFavoriteEvent) event).getEventType() == 4) {
                collectCompanyText.setText("收藏");
                collectCompanyImage.setImageResource(R.drawable.skin_collect_situation);
                favoriteId = "";
                isCollect = false;
            }
        } else if (event instanceof AddToFavoriteEvent) {
            favoriteId = ((AddToFavoriteEvent) event).getFavoriteId();
            collectCompanyText.setText("已收藏");
            collectCompanyImage.setImageResource(R.drawable.skin_allready_collect_situation);
            isCollect = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

}
