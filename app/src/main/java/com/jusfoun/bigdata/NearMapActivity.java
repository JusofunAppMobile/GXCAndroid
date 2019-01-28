package com.jusfoun.bigdata;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.jusfoun.bigdata.http.NearMapSource;
import com.jusfoun.bigdata.xrecycleview.XRecyclerView;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.permission.PermissionBelowM;
import com.jusfoun.jusfouninquire.ui.permission.PermissionGen;
import com.jusfoun.jusfouninquire.ui.permission.PermissionGranted;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import netlib.util.AppUtil;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.jusfoun.jusfouninquire.ui.util.AppUtil.isPermissionDenied;
import static com.jusfoun.jusfouninquire.ui.util.AppUtil.showPermissionStorageDialog;


/**
 * 附近页面
 *
 * @author cxy
 * @version create time:2015年7月30日
 */
@RuntimePermissions
public class NearMapActivity extends BaseBaiduMapActivity implements TypeConstant, WebContentConstant {

    /**
     * 常量
     */
    private static final int MAP_LOAD_DATA = 5;
    private static final int CHOICE_INDUSTRY = 1;//李建 2015-08-10
    protected TextView titleText;
    protected TextView noDataText;
    protected ImageView ivNetError;
    protected RelativeLayout contentLayout;

    /**
     * 组件
     */
    // 用来选择类别的 view。
    XRecyclerView mRecyclerView;
    // 关键字 营收规模 更多 排序
    View typeBut1;
    View typeBut2;
    View typeBut3;
    View typeBut4;
    View triangleLayout;
    TextView failedText;
    Button mapCenterBtn;
    TextView locationIcon;
    ImageView img4;
    ImageView titleReset;
    ImageView titleSwitch;
    View block1;
    View block2;
    View block3;
    View block4;
    TextView countText;
    LinearLayout totalLayout;

    // 底部 列表 view。
    MapBottomListView bottomListLayout;
    RelativeLayout mapViewLayout;
    /**
     * 变量
     */

    private String AlreadyShowCount;
    private String allCompanyCount;
    /* 当前选中的是哪个type。 0: 关键字分类 1: 营收规模分类 2: 更多筛选 3: 排序分类 */
    private int choiceType = -1;
    // 排序方式 list
    private List<BaseTypeModel> sortList;
    // 营收规模类别 list
    private List<BaseTypeModel> shopTypeList;
    private List<BaseTypeModel> submitShopTypeList;
    // 关键字 ， 营收规模id。 排序id
    private String searchKey = "", shopTypeId = "", sortId = "0";
    private String[] income_tag, sort_tag, income_value, sort_value;
    private int pageNum = 1;

    private boolean isFristInto = true;
    public int currentCoord;

    // 更多 Model。
    private TypeMoreModel typeMoreModel = new TypeMoreModel();

    private boolean isNodata = false;

    private boolean isNetFiale = false;

    private CompanyListAdapter companyAdapter;

    private List<XListDataModel> companyList = new ArrayList<>();

    View titleView;
    View typeTitlelayout;
    ImageView tvTitleBarLeft;

    protected static final int PERMISSION_REQUEST = 100;
    public static  int ACTION_APPLICATION_DETAILS_SETTINGS_CODE = 0x100;

    private NearMapSource source;



    private  NearMapDataModel currentClickModel = null;
    @Override
    public void initData() {
        initCache();
        source = new NearMapSource();
        dialog = new CallPhoneDialog(context, R.style.my_dialog);
        //initBaiduMapLocation(); //这是初始化定位之后，会执行附近中的一系列操作
        sortList = new ArrayList<>();
        shopTypeList = new ArrayList<>();
        submitShopTypeList = new ArrayList<>();

        income_tag = getResources().getStringArray(R.array.income_tag);
        income_value = getResources().getStringArray(R.array.income_value);
        sort_tag = getResources().getStringArray(R.array.sort_tag);
        sort_value = getResources().getStringArray(R.array.sort_value);
        for (int i = 0; i < income_tag.length; i++) {
            BaseTypeModel model = new BaseTypeModel();
            model.setId(i + "");
            model.setParams(income_value[i]);
            model.setSelected(false);
            model.setTitle(income_tag[i]);
            shopTypeList.add(model);

            BaseTypeModel submodel = new BaseTypeModel();
            submodel.setId(i + "");
            submodel.setParams(income_value[i]);
            submodel.setSelected(false);
            submodel.setTitle(income_tag[i]);
            submitShopTypeList.add(submodel);


        }

        for (int i = 0; i < sort_tag.length; i++) {
            BaseTypeModel model = new BaseTypeModel();
            model.setId(i + "");
            model.setParams(sort_value[i]);
            model.setSelected(false);
            model.setTitle(sort_tag[i]);
            sortList.add(model);
        }

        mNearHashMap.put("maptype", mapType + "");//李建 2018-08-11
    }

    @Override
    public void initView() {
        setContentView(R.layout.fragment_nearmap);
        tvTitleBarLeft = (ImageView) findViewById(R.id.tv_title_bar_left);
        titleText = (TextView) findViewById(R.id.titleText);
        titleSwitch = (ImageView) findViewById(R.id.searchText);
        titleReset = (ImageView) findViewById(R.id.clearText);
        titleView = (RelativeLayout) findViewById(R.id.titleView);
        typeBut1 = (LinearLayout) findViewById(R.id.typeBut1);
        block1 = (TextView) findViewById(R.id.block1);
        typeBut2 = (LinearLayout) findViewById(R.id.typeBut2);
        block2 = (TextView) findViewById(R.id.block2);
        typeBut3 = (LinearLayout) findViewById(R.id.typeBut3);
        block3 = (TextView) findViewById(R.id.block3);
        block4 = (TextView) findViewById(R.id.block4);
        typeBut4 = (RelativeLayout) findViewById(R.id.typeBut4);
        img4 = (ImageView) findViewById(R.id.img4);
        triangleLayout = (LinearLayout) findViewById(R.id.triangleLayout);
        typeTitlelayout = (LinearLayout) findViewById(R.id.typeTitlelayout);
        mapCenterBtn = (Button) findViewById(R.id.mapCenterBtn);
        locationIcon = (TextView) findViewById(R.id.locationIcon);
        mapViewLayout = (RelativeLayout) findViewById(R.id.mapview_relativeLayout);
        mRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView_nearby);
        noDataText = (TextView) findViewById(R.id.no_dataText);
        ivNetError = (ImageView) findViewById(R.id.iv_net_error);
        failedText = (TextView) findViewById(R.id.failedText);
        contentLayout = (RelativeLayout) findViewById(R.id.contentLayout);
        bottomListLayout = (MapBottomListView) findViewById(R.id.bottomListLayout);
        countText = (TextView)findViewById(R.id.text_count);
        totalLayout =(LinearLayout)findViewById(R.id.layout_total);


    }


    private void refreshData() {
//            if (xListView.getVisibility()==View.VISIBLE)
//                return;
        //初始化一边地图状态
        if (choiceViewType == LIST_VIEW_TYPE) {
            if (no_dataLayout.getVisibility() == View.VISIBLE) {
                failedLayout.setVisibility(View.GONE);
            } else if (failedLayout.getVisibility() == View.VISIBLE) {
                no_dataLayout.setVisibility(View.GONE);
            } else {
                setListViewVisble();
                failedLayout.setVisibility(View.GONE);
                no_dataLayout.setVisibility(View.GONE);
            }
        } else {
            setMapViewVisible();
            failedLayout.setVisibility(View.GONE);
            no_dataLayout.setVisibility(View.GONE);
        }
        if (isFristInto) {
            initBaiduMapLocation();
        }
        setTypeViewGone(choiceType);
    }

    @Override
    public void doBusiness() {

        failedLayout = (RelativeLayout) findViewById(R.id.failed_layout);
        no_dataLayout = (RelativeLayout) findViewById(R.id.no_dataLayout);
//        titleText.setText(getString(R.string.title_map));

        tvTitleBarLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initMapView();
        initBaiduMap();

        pt = new Point();

        refreshData();

        bottomListLayout.setOnBottemListViewItemClickLisner(new MapBottomListView.OnBottomListViewItemClickListener() {

            @Override
            public void onClick(Object object) { //底部列表的item点击执行事件
                XListDataModel model = (XListDataModel) object;
                Intent intent = new Intent(NearMapActivity.this, CompanySituationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ENT_ID, model.getEnt_id());
                bundle.putString(MAP_LAT, model.getMap_lat());
                bundle.putString(MAP_LNG, model.getMap_lng());
                bundle.putString(ENT_NAME, model.getEntname());
                intent.putExtra(SELECT_MODEL, bundle);
                startActivity(intent);
            }

            @Override
            public void onPackUpListClick() { //收起底部列表的点击事件
                if (bottomListLayout.getVisibility() == View.VISIBLE) {
                    showBottomList(false);
                }
            }

            @Override
            public void bottomListViewRefresh() {//刷新bottomListView 的数据
//                mRecyclerView.setRefreshing(true);
                  if(currentClickModel!=null){
                      getXlistDataByLaglon(currentClickModel.getLat(),currentClickModel.getLng());
                  }

            }

            @Override
            public void bottomListViewLoadMore() {//加载更多bottomListView 的数据
//                getXlistData(mNearHashMap, pageNum);
                if(currentClickModel!=null){
                    getXlistDataByLaglon(currentClickModel.getLat(),currentClickModel.getLng());
                }
            }
        });

        //bottomListView点击错误页面重新请求
        bottomListLayout.setOnErrorViewClickLisner(new MapBottomListView.OnErrorViewClickLisner() {
            @Override
            public void onClick() {
                mRecyclerView.setRefreshing(true);
            }
        });

        failedLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFristInto) {
                    locationAgain();
                } else {
                    mRecyclerView.setRefreshing(true);
                }
            }
        });

        /**王晨晨 清空查找条件	 */
        titleReset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mNearHashMap = NearMapUtil.initNearHashMap(mNearHashMap);
                setReset();
            }
        });
        /**王晨晨 切换地图与list       */

        titleSwitch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                setTypeViewGone(choiceType);
                if (choiceViewType == LIST_VIEW_TYPE) {
                    totalLayout.setVisibility(View.GONE);
                    setMapViewVisible();
                    //移除精确经纬度信息
                    if (mNearHashMap.containsKey("currentlnglat")) {
                        mNearHashMap.remove("currentlnglat");
                        updateMapState();
                        return;
                    }
                    getData();
                } else {
                    totalLayout.setVisibility(View.VISIBLE);
                    if (mNearHashMap.containsKey("currentlnglat")) {
                        mNearHashMap.remove("currentlnglat");
                    }
                    setListViewVisble();
                    mRecyclerView.setRefreshing(true);
                }
            }
        });

        locationIcon.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (!bottomListViewIsShow()) {
                    locationAgain();
                }
            }
        });


        initRecyclerView();
    }

    private void initRecyclerView() {
        companyAdapter = new CompanyListAdapter(this, new ArrayList<XListDataModel>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(companyAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                getXlistData(mNearHashMap, pageNum);
            }

            @Override
            public void onLoadMore() {
                getXlistData(mNearHashMap, pageNum);
            }
        });

        companyAdapter.setOnItemViewClickListener(new BaseRecyclerAdapter.OnItemViewClickListener<XListDataModel>() {
            @Override
            public void onItemClick(XListDataModel xListDataModel, int position) {
                Intent intent = new Intent(NearMapActivity.this, CompanySituationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ENT_ID, xListDataModel.getEnt_id());
                bundle.putString(MAP_LAT, xListDataModel.getMap_lat());
                bundle.putString(MAP_LNG, xListDataModel.getMap_lng());
                bundle.putString(ENT_NAME, xListDataModel.getEntname());
                intent.putExtra(SELECT_MODEL, bundle);
                startActivity(intent);
            }
        });
    }


    /**
     * 设置typeView重新隐藏起来
     *
     * @param index 对应的类型
     */
    private void setTypeViewGone(int index) {
        choiceType = -1;
    }

    /**
     * 设置typeView重新显示出来
     *
     * @param index 对应的类型
     */
    private void setTypeViewChoice(int index) {
        choiceType = index;
    }


    //李建 2018-08-11 附近列表+地图+过滤
    public void getData() {
//        titleText.setText(getString(R.string.title_map));
        showLoadDialog();
//        HttpManager.getInstance()
//                .get(getString(R.string.req_url)+UrlConstant.GetMapListV4_0_3, mNearHashMap, new CommonCallback<NearMapModel>() {
//                    @Override
//                    public void onSuccess(NearMapModel response, Object... obj) {
//                        updateMapView(response);
//                    }
//
//                    @Override
//                    public void onFail(String message, int code, Object... obj) {
//                        setMapDataNetError(MAP_LOAD_DATA);
//                        Toaster.showToastError();
//                    }
//
//                    @Override
//                    public void onAfter() {
//                        super.onAfter();
//                        hideLoadDialog();
//                    }
//                });
//

        source.getMapList(this, mNearHashMap, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                if(data!=null&&data instanceof NearMapModel){
                    updateMapView((NearMapModel)data);
                }

            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                setMapDataNetError(MAP_LOAD_DATA);
                Toaster.showToastError();
            }
        });
    }

    private void updateMapView(NearMapModel model) {
        bottomListLayout.onButtomLoadFinish();
        bottomListLayout.isShowNoDataLayout(false);
        onLoadFinish();
        failedLayout.setVisibility(View.GONE);
        no_dataLayout.setVisibility(View.GONE);

        isNetFiale = false;

        //此处防止在地图变化的一瞬间点击了坐标点，从而使得底部列表显示出来
        if (bottomListViewIsShow()) {
            return;
        }
        //此处说明数据并非是在底部view显示时，获取的数据"
        MapStatus ms = mBaiduMap.getMapStatus();
        if(ms!=null) {
            beforeClickLevel = ms.zoom;
            beforeClickLatlng = ms.target;
        }

        setMapViewVisible();
        if (model.getResult() == 0) {
            allCompanyCount = model.getTotal()+"";
            if (mapType == 4) {
                AlreadyShowCount = model.getAlreadyShowNum();
            }
            setMapTitleCount(mCurrentProvince, mCurrentCity, allCompanyCount, AlreadyShowCount);
            if (model.getData() != null && model.getData().size() > 0) {
                currentCoord = model.getData().size();

                addPoiOverlay(model.getData());
            } else {
                mBaiduMap.clear();
            }
        } else {
            Toaster.showToast(R.string.register_login_neterror);
        }
    }

    public void getXlistData(HashMap<String, String> params, int pageNum) {
        params.put("pageindex", pageNum + "");
        params.put("pageSize",   "20");
        source.getCompanyList(this, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                onLoadFinish();
                if(data!=null&&data instanceof XListModel){
                    updateView((XListModel)data);
                }

            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                onLoadFinish();
                Toaster.showToastError();
                setXListViewDataError();
            }
        });

    }

    /**
     *  获取某经纬度处 的企业列表
     * */
    public void getXlistDataByLaglon(String lat,String lon) {
        pageNum = 1;
        HashMap<String,String> params = new HashMap<>();
        params.put("currentlatlng", lat + ","+lon);
        source.getXlistDataByLaglon(this, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                onLoadFinish();
                if(data!=null&&data instanceof XListModel) {
                    updateView((XListModel)data);
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                onLoadFinish();
                Toaster.showToastError();
                setXListViewDataError();
            }
        });

    }

    /**
     * 请求成功后的处理
     *
     * @param model
     */
    protected void updateView(XListModel model) {
        bottomListLayout.onButtomLoadFinish();
        bottomListLayout.isShowNoDataLayout(false);
        failedLayout.setVisibility(View.GONE);
        no_dataLayout.setVisibility(View.GONE);

        isNetFiale = false;

        List<XListDataModel> datas = model.getData();

        if (choiceViewType == LIST_VIEW_TYPE) {
            setListViewVisble();
            totalLayout.setVisibility(View.VISIBLE);
            if (pageNum == 1) {
                if (datas == null || datas.size() == 0) {
                    isNodata = true;
                    mRecyclerView.setVisibility(View.GONE);
                    no_dataLayout.setVisibility(View.VISIBLE);
                } else {
                    isNodata = false;
                    companyList.clear();
                    if (!isFristInto && getActivity() != null) {
                        int yOffset = titleView.getHeight() + typeBut1.getHeight();
                        CustomToast.getToast().showCompanyNum(context, String.format(getString(R.string.find_for_you), model.getTotal() + ""), yOffset);
                    }
                }
            }
            companyList.addAll(model.getData());
            companyAdapter.setData(companyList);
            countText.setText(model.getTotal()+"");
            boolean hasNextPage = companyAdapter.getItemCount() < model.getTotal();
            if (hasNextPage) {
                mRecyclerView.setLoadingMoreEnabled(true);
            } else {
                mRecyclerView.setLoadingMoreEnabled(false);
            }
        }

        if (choiceViewType == MAP_VIEW_TYPE) {//显示地图时
            bottomListLayout.onButtomLoadFinish();

            bottomListLayout.setCurrentAll_Company(model.getTotal() + "家企业");
            if (!TextUtils.isEmpty(model.getDistince()) && !TextUtils.isEmpty(model.getDistinceunit())) {
                bottomListLayout.setDistanceCompany(model.getDistince() + model.getDistinceunit() + "");
            } else {
                bottomListLayout.setDistanceCompany("");
            }
            if (mapType == 4) {
                bottomListLayout.setData(model, pageNum);
            }

        }
        pageNum++;
    }

    /**
     * xlistView 的页面网络异常状态
     */
    protected void setXListViewDataError() {
        failedLayout.setVisibility(View.GONE);
        no_dataLayout.setVisibility(View.GONE);
        if (choiceViewType == LIST_VIEW_TYPE) {
            onLoadFinish();
            if (pageNum == 1) {
                failedLayout.setVisibility(View.VISIBLE);
                isNetFiale = true;
                failedText.setText(R.string.register_login_neterror);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                Toaster.showToast(R.string.register_login_neterror);
            }
        } else if (choiceViewType == MAP_VIEW_TYPE) {
            if (pageNum == 1) {
                bottomListLayout.isShowNoDataLayout(true);
            }
        }
    }

    public void setMapDataNetError(int mode) {
        failedLayout.setVisibility(View.GONE);
        no_dataLayout.setVisibility(View.GONE);
        if (choiceViewType == MAP_VIEW_TYPE) {
            if (mode == MAP_LOAD_DATA) {
                addPoiOverlay(null);
                Toaster.showToast(R.string.register_login_neterror);
            }
        }
    }

    private void onLoadFinish() {
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof InitBaiduEvent) {
            Log.e("tag", "InitBaiduEvent");
//            if (xListView.getVisibility()==View.VISIBLE)
//                return;
            //初始化一边地图状态
            if (choiceViewType == LIST_VIEW_TYPE) {
                if (no_dataLayout.getVisibility() == View.VISIBLE) {
                    failedLayout.setVisibility(View.GONE);
                } else if (failedLayout.getVisibility() == View.VISIBLE) {
                    no_dataLayout.setVisibility(View.GONE);
                } else {
                    setListViewVisble();
                    failedLayout.setVisibility(View.GONE);
                    no_dataLayout.setVisibility(View.GONE);
                }
            } else {
                setMapViewVisible();
                failedLayout.setVisibility(View.GONE);
                no_dataLayout.setVisibility(View.GONE);
            }
            if (isFristInto) {
                initBaiduMapLocation();
            }

            setTypeViewGone(choiceType);
        }
    }

    @Override
    protected void changeMapType(int maptype) {
        if (isFristInto) {
            mRecyclerView.setRefreshing(true);
            isFristInto = false;
        } else {
            getData();//李建 2018-08-11 附近列表+地图+过滤
        }

    }

    @Override
    protected boolean bottomListViewIsShow() {
        if (bottomListLayout.getVisibility() == View.GONE) {
            return false;
        } else if (bottomListLayout.getVisibility() == View.INVISIBLE) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    protected void clickMark(Marker marker) {
        if (mapType != 4) {
            markClickEvent(marker);
        } else {

            if (lastClickMarker == marker) {
                return;
            }

            BitmapDescriptor checkedbd = BitmapDescriptorFactory.fromResource(R.drawable.checked_company);
            marker.setIcon(checkedbd);
            checkedbd.recycle();
            if (lastClickMarker != null) {
                Bundle bundle = lastClickMarker.getExtraInfo();
                NearMapDataModel model = (NearMapDataModel) bundle.getSerializable("item_map");
                if (model.getTotal() > 1) {
                    BitmapDescriptor more_bd = BitmapDescriptorFactory.fromResource(R.drawable.more_company);
                    lastClickMarker.setIcon(more_bd);
                    more_bd.recycle();
                } else {
                    BitmapDescriptor one_bd = BitmapDescriptorFactory.fromResource(R.drawable.one_company);
                    lastClickMarker.setIcon(one_bd);
                    one_bd.recycle();
                }
            }
            lastClickMarker = marker;

            showBottomList(true);
            Bundle bundle = marker.getExtraInfo();
            NearMapDataModel model = (NearMapDataModel) bundle.getSerializable("item_map");
            setLatlngToMapCenter(marker.getPosition());

            mNearHashMap.put("currentlnglat", model.getLat() + "," + model.getLng());
//            mRecyclerView.setRefreshing(true);

            currentClickModel = model;
            // 获取 某经纬度的企业
            getXlistDataByLaglon( model.getLat(), model.getLng());
        }

    }

    @Override
    protected void mapTouch() {
        super.mapTouch();
        // TODO  处理 地图变化，可以去请求网络了。
        showBottomList(false);
    }

    /**
     * 显示地图
     */
    private void setMapViewVisible() {

//        titleText.setText(getString(R.string.title_map));
        choiceViewType = MAP_VIEW_TYPE;
        mRecyclerView.setVisibility(View.GONE);
        typeBut4.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        mapViewLayout.setVisibility(View.VISIBLE);
//        SkinUtils.setSkinBackgroundDrawable(context
//                , titleSwitch
//                , "selector_near_list_right_image"
//                , R.drawable.list);
        titleSwitch.setImageResource(R.drawable.list);
        //titleSwitch.setBackgroundResource(R.drawable.selector_near_list_right_image);
        locationIcon.setVisibility(View.VISIBLE);
    }

    /**
     * 显示列表
     */
    private void setListViewVisble() {

        titleText.setText(getString(R.string.title_list));
        choiceViewType = LIST_VIEW_TYPE;
        mapCenterBtn.setVisibility(View.GONE);
        locationIcon.setVisibility(View.GONE);
        mapViewLayout.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        typeBut4.setVisibility(View.VISIBLE);
        img4.setVisibility(View.INVISIBLE);
//        SkinUtils.setSkinBackgroundDrawable(context
//                , titleSwitch
//                , "selector_near_map_right_image"
//                , R.drawable.type_map);
        titleSwitch.setImageResource(R.drawable.type_map);
        //titleSwitch.setBackgroundResource(R.drawable.selector_near_map_right_image);
    }


    /**
     * 判断当前显示无数据界面类型
     */
    public void setNodataShow() {
        if (mapViewLayout.getVisibility() == View.VISIBLE)
            return;
        if (isNodata) {
            mRecyclerView.setVisibility(View.GONE);
            no_dataLayout.setVisibility(View.VISIBLE);
        }
        if (isNetFiale) {
            mRecyclerView.setVisibility(View.GONE);
            failedLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 错误和无数据界面隐藏
     */
    public void setNoDataHide() {
        failedLayout.setVisibility(View.GONE);
        no_dataLayout.setVisibility(View.GONE);
    }

    /**
     * 显示查询结果
     *
     * @param currentProvince  当前省份
     * @param currentCity      当前城市
     * @param allCompanyCount  总企业数
     * @param AlreadyShowCount 显示企业数
     */
    public void setMapTitleCount(String currentProvince, String currentCity, String allCompanyCount, String AlreadyShowCount) {

        if (mapType == 4) {
            mapCenterBtn.setText("附近共有" + allCompanyCount + "家企业，目前已显示" + AlreadyShowCount + "家");
        } else if (mapType == 1) {
            mapCenterBtn.setText("全国已统计总企业" + allCompanyCount + "家");
        } else if (mapType == 2) {
            mapCenterBtn.setText(currentProvince + "已统计总企业" + allCompanyCount + "家");
        } else if (mapType == 3) {
            mapCenterBtn.setText(currentCity + "已统计总企业" + allCompanyCount + "家");
        }
        mapCenterBtn.setVisibility(View.VISIBLE);
    }

    /**
     * title部位 重置功能
     */
    private void setReset() {
        setTypeViewGone(choiceType);
        sortId = "0";
        typeMoreModel = new TypeMoreModel();
        submitShopTypeList.clear();
        for (int i = 0; i < income_tag.length; i++) {
            BaseTypeModel submodel = new BaseTypeModel();
            submodel.setId(i + "");
            submodel.setParams(income_value[i]);
            submodel.setSelected(false);
            submodel.setTitle(income_tag[i]);
            submitShopTypeList.add(submodel);
        }
    }

    /**
     * 显示与隐藏底部listView
     *
     * @param show
     */
    private void showBottomList(boolean show) {
        if (show) {
            bottomListLayout.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.GONE);
            typeTitlelayout.setVisibility(View.GONE);
            locationIcon.setVisibility(View.GONE);
        } else {
            locationIcon.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.VISIBLE);
//            typeTitlelayout.setVisibility(View.VISIBLE);
            bottomListLayout.setVisibility(View.GONE);
            /******************下面的该筛选项由李亮添加*******************/
            if (mNearHashMap.containsKey("currentlnglat")) {
                mNearHashMap.remove("currentlnglat");
            }
            bottomListViewEvent = true;
            if (lastClickMarker != null) {
                Bundle bundle = lastClickMarker.getExtraInfo();
                NearMapDataModel model = (NearMapDataModel) bundle.getSerializable("item_map");
                if (model!=null&&model.getTotal() > 1) {
                    BitmapDescriptor more_bd = BitmapDescriptorFactory.fromResource(R.drawable.more_company);
                    lastClickMarker.setIcon(more_bd);
                    more_bd.recycle();
                } else {
                    BitmapDescriptor one_bd = BitmapDescriptorFactory.fromResource(R.drawable.one_company);
                    lastClickMarker.setIcon(one_bd);
                    one_bd.recycle();
                }
            }

            restoreMapBeforeCLickLatlng();

            lastClickMarker = null;
        }
    }

    // ####################### 6.0 运行时权限 #########################

    private boolean isGoSettingPrivilege;

    @Override
    public void onResume() {
        super.onResume();
        if (isGoSettingPrivilege) {
            isGoSettingPrivilege = false;
            requestLocationPermission();
        }
    }



    private void showPermissionDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("在设置-应用-" + com.jusfoun.jusfouninquire.ui.util.AppUtil.getAppName(this) + "-权限中开启"  + "定位权限，以正常使用" + com.jusfoun.jusfouninquire.ui.util.AppUtil.getAppName(this) + "功能")
               .setPositiveButton("取消",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }) .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        finish();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", AppUtil.getPackageName(context), null);
                        intent.setData(uri);
                        ((Activity)context).startActivityForResult(intent,ACTION_APPLICATION_DETAILS_SETTINGS_CODE);
                    }
                })
                .setCancelable(false).create();
        dialog.show();
    }


    @Override
    protected void requestLocationPermission() {
//        PermissionGen.with(this)
//                .addRequestCode(PERMISSION_REQUEST)
//                .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
////                .permissions(Manifest.permission.READ_PHONE_STATE)
//                .request();

        NearMapActivityPermissionsDispatcher.getPermissionWithPermissionCheck(this);
    }

//    @PermissionBelowM(PERMISSION_REQUEST)
//    public boolean permissionBelowM() {
//        return true;
//    }
//
//    @PermissionGranted(PERMISSION_REQUEST)
//    public void permissionSuccess() {
//        if (AppUtil.checkAppops(this, AppOpsManager.OPSTR_FINE_LOCATION)) {
//            locationUtil.startLocation();
//        } else {
//            String text = "请到安全中心开启" + AppUtil.getAppName(this) + "的定位权限";
//            showPermissionDialog(text);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ACTION_APPLICATION_DETAILS_SETTINGS_CODE){
            requestLocationPermission();
        }else if(requestCode==SETTINGS_CODE){
            requestLocationPermission();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        NearMapActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void getPermission() {
        if (isPermissionDenied(this, AppOpsManager.OPSTR_FINE_LOCATION)) {
            showPermissionDialog();
            return;
        }
        locationUtil.startLocation();
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    void showRationale(final PermissionRequest request) {
        showPermissionDialog();
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDenied() {
        showPermissionDialog();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAsk() {
        showPermissionDialog();
    }


}
