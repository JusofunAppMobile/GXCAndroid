package com.jusfoun.jusfouninquire.ui.activity;

import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.adapter.MapAppAdapter;
import com.jusfoun.jusfouninquire.ui.util.AppInfo;
import com.jusfoun.jusfouninquire.ui.util.LocationUtil;
import com.jusfoun.jusfouninquire.ui.util.MapUtil;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/11.
 * Description
 */
public class BaiduMapActivity extends BaseInquireActivity implements LocationUtil.MyLocationListener {

    public static final String COMPANY_NAME="company_name";
    public static final String LAT="lat";
    public static final String LON="lon";
    private MapView mapView;
    private BaiduMap baiduMap;
    private String companyName,lat,lon;
    private TextView textView,secondeText;
    private LinearLayout layout;
    private List<AppInfo> list=new ArrayList<>();
    private LocationUtil locationUtil;
    private LatLng fromPoint,toPoint;
    private AlertDialog.Builder dialog;
    private BackAndRightImageTitleView title;

    @Override
    protected void initData() {
        super.initData();
        if (getIntent().getExtras()!=null) {
            companyName = getIntent().getExtras().getString(COMPANY_NAME);
            lat=getIntent().getExtras().getString(LAT);
            lon=getIntent().getExtras().getString(LON);
            if (!TextUtils.isEmpty(lat)&&!TextUtils.isEmpty(lon)){
                toPoint=new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
            }
        }
        if(toPoint==null)
            toPoint=new LatLng(39.963175,116.400244);
        locationUtil=new LocationUtil(getApplication());
        locationUtil.setMyLocationListener(this);
        locationUtil.startLocation();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_baidu_map);
        mapView= (MapView) findViewById(R.id.map);
        title= (BackAndRightImageTitleView) findViewById(R.id.map_title);
        dialog = new AlertDialog.Builder(mContext);
    }

    @Override
    protected void initWidgetActions(){

        baiduMap=mapView.getMap();
        BitmapDescriptor bitmap= BitmapDescriptorFactory.fromResource(R.mipmap.checked_company);
        OverlayOptions options=new MarkerOptions()
                .position(toPoint)
                .icon(bitmap);
        baiduMap.addOverlay(options);
        MapStatus status=new MapStatus.Builder().zoom(baiduMap.getMaxZoomLevel()-baiduMap.getMinZoomLevel()).target(toPoint).build();
        MapStatusUpdate update= MapStatusUpdateFactory.newMapStatus(status);
        baiduMap.setMapStatus(update);

        View view=LayoutInflater.from(mContext).inflate(R.layout.item_baidu_pop, null);
        textView= (TextView) view.findViewById(R.id.company_name);
        secondeText = (TextView) view.findViewById(R.id.company_name_seconde);
        layout= (LinearLayout) view.findViewById(R.id.layout);
        textView.setText(companyName);
        secondeText.setText(companyName);

        title.setTitleText("企业位置");
        title.setmLeftClickListener(new BackAndRightImageTitleView.LeftClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        Log.e("tag", "companyName=" + companyName);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    Toast.makeText(mContext, getString(R.string.no_map_app), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (list.size() == 1) {
                    String packageName = list.get(0).getPackageName();
                    if (MapUtil.BAIDU.equals(packageName)) {
                        MapUtil.getInstance(mContext).startBaiduNavi(fromPoint, toPoint, companyName);
                    } else if (MapUtil.GOOGLE.equals(packageName)) {
                        MapUtil.getInstance(mContext).startGoogleNavi(fromPoint, toPoint);
                    } else if (MapUtil.AUTO.equals(packageName)) {
                        MapUtil.getInstance(mContext).startAutoNavi(fromPoint, toPoint, companyName);
                    } else if (MapUtil.SOGOU.equals(packageName)) {
                        MapUtil.getInstance(mContext).startSogouNavi(toPoint);
                    } else if (MapUtil.TENCENT.equals(packageName)) {
                        MapUtil.getInstance(mContext).startTencentNavi(fromPoint, toPoint, companyName);
                    }
                    return;
                }
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.layout_app_map, null);
                ListView listView = (ListView) view1.findViewById(R.id.list);
                MapAppAdapter appAdapter = new MapAppAdapter(mContext);
                appAdapter.refresh(list);
                listView.setAdapter(appAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (fromPoint == null) {
                            Toast.makeText(mContext, "获取地址信息失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (view.getTag() instanceof MapAppAdapter.ViewHolder) {
                            MapAppAdapter.ViewHolder holder = (MapAppAdapter.ViewHolder) view.getTag();
                            String packageName=holder.mAppInfo.getPackageName();
                            if (MapUtil.BAIDU.equals(packageName)) {
                                MapUtil.getInstance(mContext).startBaiduNavi(fromPoint, toPoint, companyName);
                            } else if (MapUtil.GOOGLE.equals(packageName)) {
                                MapUtil.getInstance(mContext).startGoogleNavi(fromPoint, toPoint);
                            } else if (MapUtil.AUTO.equals(packageName)) {
                                MapUtil.getInstance(mContext).startAutoNavi(fromPoint, toPoint, companyName);
                            } else if (MapUtil.SOGOU.equals(packageName)) {
                                MapUtil.getInstance(mContext).startSogouNavi(toPoint);
                            } else if (MapUtil.TENCENT.equals(packageName)) {
                                MapUtil.getInstance(mContext).startTencentNavi(fromPoint, toPoint, companyName);
                            }
                        }
                    }
                });
                dialog.setView(view1);
                dialog.show();
            }
        });
        InfoWindow infoWindow=new InfoWindow(view,toPoint
                ,-BitmapFactory.decodeResource(getResources(),R.mipmap.checked_company).getHeight());
        baiduMap.showInfoWindow(infoWindow);
        MapUtil.getInstance(mContext).getList(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationUtil.stopLocation();
    }

    @Override
    public void locationSucc(BDLocation location) {
        fromPoint=new LatLng(location.getLatitude(),location.getLongitude());
    }

    @Override
    public void locationFail() {
        Toast.makeText(mContext,"定位失败",Toast.LENGTH_SHORT).show();
    }
}
