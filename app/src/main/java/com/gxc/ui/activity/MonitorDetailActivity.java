package com.gxc.ui.activity;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.MonitorDetailModel;
import com.gxc.ui.adapter.MonitorDetailAdapter;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MonitorDetailAdapter(this);
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
    }

    private void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(vMenu))
            mDrawerLayout.closeDrawer(vMenu);
        else
            mDrawerLayout.openDrawer(vMenu);
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
    }
}
