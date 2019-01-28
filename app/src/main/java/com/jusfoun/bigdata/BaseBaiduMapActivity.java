package com.jusfoun.bigdata;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.jusfoun.BaiduPopView;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import netlib.util.ToastUtils;

public abstract class BaseBaiduMapActivity extends BaseActivity implements LocationUtil.MyLocationListener {

    public final static int LIST_VIEW_TYPE = 0;
    public final static int MAP_VIEW_TYPE = 1;

    private static final int DODELAYMSG = 10;
    private static final int HIDE_DIALOG = 11;
    protected static final int SETTINGS_CODE = 100;

    private static final int DELAY_TIME = 750;
    private static final int BY_TIME = 30 * 1000;

    /* 点击地图上某坐标后地图放大等级*/
    private static final float CLICK_POI_MANGNIFY_MAP_LEVEL = 19.0f;


    /**
     * 控件
     */
    public MapView mMapView;
    public BaiduMap mBaiduMap;
    protected CallPhoneDialog dialog;
    public RelativeLayout failedLayout, no_dataLayout;
    /**
     * 变量
     */
    private float level;
    public double latitude = 0d, longitude = 0d;
    public String mCurrentProvince, mCurrentCity, mCurrentArea;
    protected int mapType;
    protected HashMap<String, String> mNearHashMap;
    // 选择样式类别
    protected int choiceViewType = LIST_VIEW_TYPE;

    public LatLng beforeClickLatlng;
    public float beforeClickLevel = 0.0f;

    public Marker lastClickMarker = null;
    public boolean bottomListViewEvent = false;

    private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    public Point pt;
    public LocationUtil locationUtil;
    private LocationManager mLocationManager;
    /**
     * 地图状态变化后两秒再去地理反编码
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DODELAYMSG:
                    if (!bottomListViewIsShow()) {
                        if (bottomListViewEvent) {
                            bottomListViewEvent = false;
                            return;
                        }
                        bottomListViewEvent = false;
                        updateMapState();
                    } else {
                        return;
                    }
                    break;
                case HIDE_DIALOG:
                    hideLoadDialog();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        context.getContentResolver().
                registerContentObserver(Settings.Secure.getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED), false,
                        mGpsMonitor);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        hideLoadDialog();
    }

    /**
     * 初始化缓存数据
     */
    protected void initCache() {
        mNearHashMap = new HashMap<>();
        mNearHashMap = NearMapUtil.initNearHashMap(mNearHashMap);
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 初始化百度地图
     */
    protected void initMapView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        UiSettings uiSettings = mBaiduMap.getUiSettings();
        uiSettings.setOverlookingGesturesEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
    }

    /**
     * 初始化定位
     */
    protected void initBaiduMapLocation() {
        if (InquireApplication.getLocationData() == null) {
            locationUtil = new LocationUtil(context);
            locationUtil.setMyLocationListener(this);
            if (!locationUtil.isLocationProvider()) {
                dialog.setText("提示", "暂未打开GPS定位");
                dialog.setButtonText("去打开", "忽略");
                dialog.setImageVisiable(false);
                dialog.setCancelable(false);
                dialog.setListener(new CallPhoneDialog.callBack() {

                    @Override
                    public void onRightClick() {
                        showLoadDialog();
                        requestLocationPermission();
//                        locationUtil.startLocation();
                    }

                    @Override
                    public void onLeftClick() {
                        gotoSettings();
                    }
                });
                dialog.show();
            } else {
                showLoadDialog();

                requestLocationPermission();
//                locationUtil.startLocation();
            }
        } else {
            getMapLocation();
            BDLocation location = InquireApplication.getLocationData();
            mNearHashMap.put("mycoordinate", location.getLatitude() + "," + location.getLongitude());
            mNearHashMap.put("currentlatlng", location.getLatitude() + "," + location.getLongitude());
        }
    }

    private void gotoSettings() {
        //进入GPS设置页面
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivityForResult(intent, SETTINGS_CODE);
        } catch (ActivityNotFoundException ex) {
            // The Android SDK doc says that the location settings activity
            // may not be found. In that case show the general settings.
            // General settings activity
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                startActivityForResult(intent, SETTINGS_CODE);
                //                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重新定位
     */
    protected void locationAgain() {
        showLoadDialog();
        locationUtil = new LocationUtil(context);
        if (locationUtil.getMyLocationListener() == null) {
            locationUtil.setMyLocationListener(this);
        }
//        locationUtil.startLocation();
        requestLocationPermission();
    }

    /**
     * 初始化百度地图事件
     */
    protected void initBaiduMap() {
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                clickMark(marker);
                return true;
            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //touchMapHideList();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                //touchMapHideList();
                return false;
            }
        });

        mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                mBaiduMap.hideInfoWindow();
                mHandler.removeMessages(DODELAYMSG);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {

                latitude = arg0.target.latitude;
                longitude = arg0.target.longitude;

                mNearHashMap.put("currentlatlng",latitude+","+longitude);

                level = arg0.zoom;
                Message delayMsg = new Message();
                delayMsg.what = DODELAYMSG;
                mHandler.sendMessageDelayed(delayMsg, DELAY_TIME);
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {

            }
        });
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(listener);
    }

    /**
     * 点击marker 出现公司名称 可点击进入详情
     *
     * @param marker
     */
    protected void markClickEvent(Marker marker) {
        TextView button = new TextView(this);
        final LatLng ll = marker.getPosition();
        final String tags[] = marker.getTitle().split(",");

        // wangchenchen
        Bundle bundle = marker.getExtraInfo();
        NearMapDataModel model = (NearMapDataModel) bundle.getSerializable("item_map");
         int companyNum =0;
         if(model!=null){
             companyNum = model.getTotal();
         }
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setPadding(PhoneUtil.dip2px(context, 20), PhoneUtil.dip2px(context, 12), PhoneUtil.dip2px(context, 20),
                PhoneUtil.dip2px(context, 20));
        if (mapType != 4) {/** Liang 对非4级情况时如果返回该点数据为1条，infoWindow的信息强制显示为多于1家*/
            String companyCount = "";
            if (model.getTotal() > 10000) {
                DecimalFormat format = new DecimalFormat(".00");
                companyCount = format.format(model.getTotal() / 10000f) + "万";
            } else {
                companyCount = model.getTotal() + "";
            }
            /*tags[1] 是在addPoiOverlay（）方法中判断了气泡是地区还是公司坐标点*/
            button.setText(tags[1] + companyCount + "家企业  >");

            BaiduPopView baiduPopView = new BaiduPopView(this);
            baiduPopView.setData(tags[1] + companyCount + "家企业  >");
            showBaiuPop(baiduPopView, ll, 0, companyNum, model, tags[1]);
        }
    }

    /**
     * 创建InfoWindow的按钮
     *
     * @return
     */
    private Button createButton() {
        Button button = new Button(this);
//        button.setBackgroundResource(R.drawable.infowindow);
        button.setTextColor(0xffffffff);
        return button;
    }

    /**
     * 添加PioOverlay
     *
     * @param datas
     */
    protected void addPoiOverlay(List<NearMapDataModel> datas) {
        removeAllMarker();
        LatLng p;
        int index = 0;
        BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_big_map_poi);
        BitmapDescriptor bdB = BitmapDescriptorFactory.fromResource(R.drawable.one_company);
        BitmapDescriptor bdc = BitmapDescriptorFactory.fromResource(R.drawable.one_company);

        MarkerOptions markerOptions;// wangchenchen
        Marker marker;
        if (datas == null) {
            bdA.recycle();
            bdB.recycle();
            bdc.recycle();
            return;
        }

        for (NearMapDataModel item : datas) {
            if (TextUtils.isEmpty(item.getLat()) || TextUtils.isEmpty(item.getLng()))
                continue;
            p = new LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()));
            index++;

            if (item.getLat() != null && item.getLng() != null) {
                String company;
                if (mapType == 1) {
                    // 金融终端逻辑 获取province ，企信包获取area
                    company = item.getArea();
                } else if (mapType == 4) {
                    if (item.getTotal() == 1) {// 李建 2018-08-07 取不到值地图bug
                        company = item.getEntname();
                    } else {
                        company = "";
                    }
                } else {
                    company = item.getArea();
                }

                // 李建 2015-08-07 修改地图Bug
                if (mapType == 1 || mapType == 2 || mapType == 3) {
                    markerOptions = new MarkerOptions().position(p).icon(bdA)
                            .title(item.getTotal() + "," + company)
                            .zIndex(index);
                    marker = (Marker) (mBaiduMap.addOverlay(markerOptions));
                } else {
                    if (item.getTotal() > 1) {
                        markerOptions = new MarkerOptions().position(p).icon(bdB)
                                .title(item.getTotal() + "," + company)
                                .zIndex(index);
                        marker = (Marker) (mBaiduMap.addOverlay(markerOptions));
                    } else {
                        markerOptions = new MarkerOptions().position(p).icon(bdc)
                                .title(item.getTotal() + "," + company)
                                .zIndex(index);
                        marker = (Marker) (mBaiduMap.addOverlay(markerOptions));
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("item_map", item);
                marker.setExtraInfo(bundle);
            }
        }
        bdA.recycle();
        bdB.recycle();
        bdc.recycle();
        hideLoadDialog();
    }

    // 清除地图上的marker
    public void removeAllMarker() {
        mBaiduMap.clear();
    }

    private void showBaiuPop(BaiduPopView button, final LatLng llInfo, int arg1, final int companyNum,
                             final NearMapDataModel model, final String tag) {
        InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), llInfo, -PhoneUtil.dip2px(context, 25),
                new OnInfoWindowClickListener() {
                    public void onInfoWindowClick() {
                        mBaiduMap.hideInfoWindow();
                        if (mapType == 4) {
                            if (companyNum == 1) {
//                                Intent intent = new Intent(context, CompanyDetailActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString(CompanyDetailActivity.COMPANY_ID, entId);
//                                bundle.putString(CompanyDetailActivity.COMPANY_NAME, entName);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
                            } else {
                                mNearHashMap.put("currentlnglat", model.getLat() + "," + model.getLng());
                                showLoadDialog();
                                getXlistData(mNearHashMap, 1);
                            }
                        } else {

                            if (!TextUtils.isEmpty(model.getLat()) && !TextUtils.isEmpty(model.getLng())) {
                                LatLng latLng = new LatLng(Double.parseDouble(model.getLat()),
                                        Double.parseDouble(model.getLng()));

                                levelChanged(tag, model.getTotal() + "", latLng);
                            }
                        }
                    }
                });

        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    /**
     * 点击infowindow层层放大
     *
     * @param name
     * @param companyNum
     * @param latlng
     */
    private void levelChanged(String name, String companyNum, LatLng latlng) {

        if (mapType == 4) {
            return;
        } else if (mapType == 1) {
            level = 7.9f;
        } else if (mapType == 2) {
            level = 10.6f;
        } else if (mapType == 3) {
            level = 15f;
        }
        // 设定中心点坐标
        // 定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder().
                target(latlng).
                zoom(level).build();
        // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    /**
     * 点击某坐标将该坐标移动到地图中心
     *
     * @param latlng
     */
    public void setLatlngToMapCenter(LatLng latlng) {
        // 设定中心点坐标
        // 定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder().
                target(latlng).
                zoom(CLICK_POI_MANGNIFY_MAP_LEVEL).build();
        // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    /**
     * 还原地图到点击某个点之前的状态
     */
    public void restoreMapBeforeCLickLatlng() {
        // 设定中心点坐标
        // 定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder().
                target(beforeClickLatlng).
                zoom(beforeClickLevel).build();
        // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }


    /**
     * 将历史定位坐标设为地图中心点
     */
    protected void getMapLocation() {
//        Log.e("tag","initBaiduMapLocation5");
        BDLocation location = InquireApplication.getLocationData();
        if (location != null) {
//            Log.e("tag","initBaiduMapLocation6");
            level = 18f;
            mapType = 4;
            LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 设定中心点坐标
            // 定义地图状态
            Log.e("tag","lat:" + locationLatLng.latitude + ",lng:" + locationLatLng.longitude);
            MapStatus mMapStatus = new MapStatus.Builder().target(locationLatLng).zoom(level).build();
            // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.animateMapStatus(mMapStatusUpdate);
            mBaiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {

                @Override
                public void onMapLoaded() {
                    if (!bottomListViewIsShow()) {
                        updateMapState();
                    }
                }
            });
        }
    }

    protected abstract void requestLocationPermission();


    @Override
    public void locationSucc(BDLocation location) {
        Log.e("tag","定位成功");
        InquireApplication.setLocationData(location);
        locationOption();

        // 定位成功会将定位点的定位度设置为 地图中心点
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        mNearHashMap.put("currentlatlng",latitude+","+longitude);


        mNearHashMap.put("mycoordinate", location.getLatitude() + "," + location.getLongitude());
    }

    @Override
    public void locationFail() {
        hideLoadDialog();
        Log.e("tag","定位失败");
        BDLocation location = InquireApplication.getLocationData();
        if (location != null) {
            getMapLocation();
        } else {
            if (choiceViewType == LIST_VIEW_TYPE) {
                failedLayout.setVisibility(View.VISIBLE);
            }
            ToastUtils.show("定位失败，请检查是否开启了位置信息或点击左下角再次尝试");
        }
    }

    /**
     * 将初始或重新定位的坐标设为地图中心点
     */
    private void locationOption() {
        // 设置自定义定位图标
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location);

        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(LocationMode.NORMAL, true, mCurrentMarker));
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        if (getActivity() == null) {
            return;
        }
        BDLocation location = InquireApplication.getLocationData();
        if (location != null) {
            level = 18f;
            mapType = 4;
            MyLocationData locData = new MyLocationData.Builder()
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            // 设定中心点坐标
            // 定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(level).build();
            // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.animateMapStatus(mMapStatusUpdate);
            mBaiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {

                @Override
                public void onMapLoaded() {
                    if (!bottomListViewIsShow()) {
                        updateMapState();
                    }
                }
            });
        } else {
            hideLoadDialog();
        }
        mCurrentMarker.recycle();
    }

    /**
     * 更新地图状态显示面板
     */
    protected void updateMapState() {
        MapStatus ms = mBaiduMap.getMapStatus();


        if (level >= 3 && level <= 7.8 && mapType != 1) {
            mapType = 1;
            mBaiduMap.setMyLocationEnabled(false);
        } else if (level > 7.8 && level <= 10.5 && mapType != 2) {
            mapType = 2;
            mBaiduMap.setMyLocationEnabled(false);
        } else if (level > 10.5 && level <= 13 && mapType != 3) {
            mapType = 3;
            mBaiduMap.setMyLocationEnabled(false);
        } else if (level > 13 && level <= 19 && mapType != 4) {
            mapType = 4;
            mBaiduMap.setMyLocationEnabled(true);
        } else if (level > 13 && level <= 20 && mapType == 4) {
            mapType = 4;
            mBaiduMap.setMyLocationEnabled(true);
        } else {
            return;
        }
        if(ms!=null) {
            boolean flag = mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ms.target));
            if (!flag) {
                hideLoadDialog();
            }
        }
//        Logger.i("reverseGeoCode == " + flag);

        Message delayMsg = new Message();
        delayMsg.what = HIDE_DIALOG;
        mHandler.sendMessageDelayed(delayMsg, BY_TIME);
    }

    /**
     * 地理反编码监听
     */
    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            mHandler.removeMessages(HIDE_DIALOG);
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                hideLoadDialog();
                ToastUtils.show("抱歉，未能找到结果");
                return;
            }

            String province = result.getAddressDetail().province;
            String city = result.getAddressDetail().city;
            String area = result.getAddressDetail().district;

            mNearHashMap = NearMapUtil.removeCurrentLatLng(mNearHashMap);

            if (mapType == 1) {
                mCurrentProvince = "";
            } else if (mapType == 2) {
                mCurrentProvince = NearMapUtil.getProvince(province, mapType);
                mNearHashMap.put("province", Utils.encodeUTF8(mCurrentProvince));
            } else if (mapType == 3) {
                mCurrentProvince = NearMapUtil.getProvince(province, mapType);
                mCurrentCity = NearMapUtil.getCity(province, city, area, mapType);

                mNearHashMap.put("province", mCurrentProvince);
                mNearHashMap.put("city", mCurrentCity);
            } else if (mapType == 4) {
                mCurrentProvince = NearMapUtil.getProvince(province, mapType);
                mCurrentCity = NearMapUtil.getCity(province, city, area, mapType);
                mCurrentArea = area;
                mNearHashMap.put("province", mCurrentProvince);
                mNearHashMap.put("city", mCurrentCity);
            }
            NearMapUtil.GetGeoCoderResultMap(context, mNearHashMap, result, mapType, mMapView, mBaiduMap);
            changeMapType(mapType);//去往子类

        }

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
            hideLoadDialog();
        }
    };

    /**
     * 获取四级 poi 的多家企业列表 以底部弹出方式显示
     *
     * @param params
     * @param pageNum
     */
    public abstract void getXlistData(HashMap<String, String> params, int pageNum);

    protected final ContentObserver mGpsMonitor = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            boolean enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (enabled) {
                if (!locationUtil.isStart()) {
                    showLoadDialog();
                    locationUtil.startLocation();
                }
            }
        }
    };


    /**
     * 地图状态变化，重新获取数据
     *
     * @param maptype
     */
    protected abstract void changeMapType(int maptype);

    protected abstract boolean bottomListViewIsShow();

    /**
     * 把marker 传回子类 去做处理。
     *
     * @param marker
     */
    protected abstract void clickMark(Marker marker);

    protected void mapTouch() {

    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    //进入页面时

    @Override
    public void onDestroy() {
        if (locationUtil != null) {
            locationUtil.stopLocation();
        }
        mMapView.onDestroy();
        context.getContentResolver().unregisterContentObserver(mGpsMonitor);
        super.onDestroy();
    }
}
