package com.gxc.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gxc.base.BaseActivity;
import com.gxc.ui.fragment.CreditFragment;
import com.gxc.ui.fragment.HomeFragment;
import com.gxc.ui.fragment.MonitorFragment;
import com.gxc.ui.fragment.MyFragment;
import com.gxc.ui.fragment.RiskListFragment;
import com.gxc.ui.widgets.ScrollableViewPager;
import com.gxc.ui.widgets.TabView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 18:41
 * @Email lgd@jusfoun.com
 * @Description ${企业风险分析}
 */
public class RiskTabActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.tab1)
    TabView tab1;
    @BindView(R.id.tab2)
    TabView tab2;
    @BindView(R.id.viewPager)
    ScrollableViewPager viewPager;


    @Override
    protected int getLayoutId() {
        return R.layout.act_risk_tab;
    }

    @Override
    public void initActions() {
        titleView.setTitle("企业风险分析");

        tab1.setLabel("自身风险（2）");
        tab2.setLabel("关联风险（3）");

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    @OnClick({R.id.tab1, R.id.tab2})
    public void onViewClicked(View view) {
        tab1.setSelected(tab1 == view);
        tab2.setSelected(tab2 == view);
        if (tab1.isSelected()) {
            if (viewPager.getCurrentItem() != 0)
                viewPager.setCurrentItem(0, false);
        } else if (tab2.isSelected()) {
            if (viewPager.getCurrentItem() != 1)
                viewPager.setCurrentItem(1, false);
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> list = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            list.add(new RiskListFragment());
            list.add(new RiskListFragment());
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
