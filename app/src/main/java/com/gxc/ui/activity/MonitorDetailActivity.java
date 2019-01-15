package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.MonitorDetailModel;
import com.gxc.model.MonitorMenuModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.MonitorDetailAdapter;
import com.gxc.ui.adapter.MonitorMenuAdpater;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/11/011 15:34
 * @Description ${监控动态详情}
 */
public class MonitorDetailActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.vMenu)
    View vMenu;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.menuRecycler)
    RecyclerView menuRecycler;

    private MonitorMenuAdpater menuAdpater;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MonitorDetailAdapter(this);
    }

    public static Intent getIntent(Context context, String companyId, String companyName) {
        Intent intent = new Intent(context, MonitorDetailActivity.class);
        intent.putExtra("companyid", companyId);
        intent.putExtra("companyName", companyName);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_monitor_detail;
    }

    @Override
    protected void initUi() {
        titleView.setTitle("动态详情");
        setStatusBarRed();
        titleView.setRightImage(R.drawable.details_icon_shaixuan);
        titleView.setRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });
        menuAdpater = new MonitorMenuAdpater();
        menuRecycler.setAdapter(menuAdpater);
        menuRecycler.setLayoutManager(new GridLayoutManager(this, 3));

        scrollView.setNestedScrollingEnabled(false);

        loadFilter();
    }

    private void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(vMenu))
            mDrawerLayout.closeDrawer(vMenu);
        else
            mDrawerLayout.openDrawer(vMenu);
    }

    private void loadFilter() {
        HashMap<String, Object> map = new HashMap<>();

        RxManager.http(RetrofitUtils.getApi().getFilterCondition(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    menuAdpater.setNewData(model.dataToList("filter", MonitorMenuModel.class));
                }
            }

            @Override
            public void error() {
            }
        });
    }

    private String getFilterIds() {
        return "";
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    @Override
    protected boolean isLoadMoreEnable() {
        return false;
    }

    @Override
    protected void startLoadData() {
        List<MonitorDetailModel> list = new ArrayList<>();
        list.add(new MonitorDetailModel(1, 1));
        list.add(new MonitorDetailModel());
        list.add(new MonitorDetailModel());
        list.add(new MonitorDetailModel());
        list.add(new MonitorDetailModel(1, 2));
        list.add(new MonitorDetailModel());
        list.add(new MonitorDetailModel());
        list.add(new MonitorDetailModel());
        list.add(new MonitorDetailModel(1, 3));
        list.add(new MonitorDetailModel());
        list.add(new MonitorDetailModel());
        list.add(new MonitorDetailModel());
        completeLoadData(list);


        HashMap<String, Object> map = new HashMap<>();
        map.put("companyid", getIntent().getStringExtra("companyid"));
        map.put("companyName", getIntent().getStringExtra("companyName"));
        map.put("filterId", "107");
//        map.put("filterId", getFilterIds());
        RxManager.http(RetrofitUtils.getApi().dynamicDetails(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                }
            }

            @Override
            public void error() {
            }
        });
    }

    @OnClick({R.id.vReset, R.id.vSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vReset:
                toggleDrawer();
                refresh();
                break;
            case R.id.vSure:
                toggleDrawer();
                refresh();
                break;
        }
    }
}
