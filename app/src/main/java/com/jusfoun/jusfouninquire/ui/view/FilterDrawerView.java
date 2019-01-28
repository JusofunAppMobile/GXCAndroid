package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.FilterContentItemModel;
import com.jusfoun.jusfouninquire.net.model.FilterItemModel;
import com.jusfoun.jusfouninquire.net.model.FilterModel;
import com.jusfoun.jusfouninquire.ui.adapter.FilterListAdapter;
import com.jusfoun.jusfouninquire.ui.util.LocationUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import java.util.HashMap;

/**
 * FilterDrawerView
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/9
 * @Description :搜索结果页面的筛选 抽屉 试图
 */
public class FilterDrawerView extends LinearLayout implements LocationUtil.MyLocationListener {
    private Context mContext;
    private FilterListAdapter mFilterAdapter;
    private ListView mFilterList;
    private TextView filter_reset, filter_sure;
    private HashMap<String, String> params;
    private LocationUtil locationUtil;
    private String locationCity;

    private boolean isHasHeader;
    private boolean isLocation;

    public FilterDrawerView(Context context) {
        super(context);
        initData(context);
        initView();
        initWidgetAction();
    }

    public FilterDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        initWidgetAction();
    }

    public FilterDrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FilterDrawerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
        initView();
        initWidgetAction();
    }

    private void initData(Context context) {
        this.mContext = context;
        mFilterAdapter = new FilterListAdapter(context);
        locationUtil = new LocationUtil(context);
        locationUtil.setMyLocationListener(this);
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_filter_drawer, this, true);
        mFilterList = (ListView) findViewById(R.id.selection_list);
        filter_sure = (TextView) findViewById(R.id.filter_sure);
        filter_reset = (TextView) findViewById(R.id.filter_reset);
    }

    private void initWidgetAction() {
        mFilterList.setAdapter(mFilterAdapter);
        addHeader();
       /* <TextView
        android:id="@+id/txt"
        android:text="所在区域"
        android:layout_marginLeft="14dp"
        android:textSize="14dp"
        android:textColor="#999999"
        android:layout_marginTop="15dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />*/

        filter_reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterAdapter.reset();
                params.put("city", "");
                params.put("province", "");
                params.put("registeredcapital", "");
                params.put("regtime", "");
                params.put("industryid", "");
                params.put("isHavWebSite", "");
            }
        });

        filter_sure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (params == null)
                    return;
                mFilterAdapter.sure(params);
                if (listener != null)
                    listener.onSure();
            }
        });
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    private FilterModel model;

    public void addHeader() {
        if (mFilterList == null)
            return;
        TextView textView = new TextView(mContext);
        textView.setTextColor(Color.parseColor("#333333"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        textView.setPadding(PhoneUtil.dip2px(mContext, 14)
                , PhoneUtil.dip2px(mContext, 14)
                , 0, 0);
        textView.setText("所在区域");

        mFilterList.addHeaderView(textView);
    }

    public void startLocation() {
        locationUtil.startLocation();
    }

    public void setData(FilterModel model, boolean canUnfold) {
        this.model = model;
        if (model != null && model.getFilterList() != null) {
            if (!TextUtils.isEmpty(locationCity)) {
                for (FilterItemModel filterItemModel : model.getFilterList()) {
                    if (filterItemModel.getType() == FilterListAdapter.TYPE_CITY) {
                        if (filterItemModel.getFilterItemList() != null) {
                            FilterContentItemModel itemModel = new FilterContentItemModel();
                            itemModel.setSelect(false);
                            itemModel.setItemname(locationCity);
                            itemModel.setItemvalue(locationCity);
                            itemModel.setLocation(true);
                            filterItemModel.getFilterItemList().add(0, itemModel);
                            break;
                        }
                        break;
                    }
                }
            }

            mFilterAdapter.refresh(model.getFilterList(), canUnfold);
        }
    }

    private void addLocation(FilterModel model) {
        if (model != null && model.getFilterList() != null) {
            if (!TextUtils.isEmpty(locationCity)) {
                for (FilterItemModel filterItemModel : model.getFilterList()) {
                    if (filterItemModel.getType() == FilterListAdapter.TYPE_CITY) {
                        if (filterItemModel.getFilterItemList() != null) {
                            if (!filterItemModel.getFilterItemList().isEmpty() &&
                                    (filterItemModel.getFilterItemList().get(0)).isLocation())
                                return;
                            FilterContentItemModel itemModel = new FilterContentItemModel();
                            itemModel.setSelect(false);
                            itemModel.setItemname(locationCity);
                            itemModel.setItemvalue(locationCity);
                            itemModel.setLocation(true);
                            filterItemModel.getFilterItemList().add(0, itemModel);

                            // 在部分手机，会出现整个页面不再刷新的bug，经过研究需要通过 handler 处理才正常
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    ((Activity)mContext).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mFilterAdapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            });
                            return;
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        locationUtil.stopLocation();
    }

    private SearchSureListener listener;

    public void setListener(SearchSureListener listener) {
        this.listener = listener;
    }

    @Override
    public void locationSucc(BDLocation location) {
        locationCity = location.getCity();
        addLocation(model);
    }

    @Override
    public void locationFail() {
        locationCity = "";
    }

    public interface SearchSureListener {
        void onSure();
    }
}
