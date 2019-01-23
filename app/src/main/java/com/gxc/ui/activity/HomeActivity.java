package com.gxc.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.gxc.model.VersionModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.dialog.VersionDialog;
import com.gxc.ui.fragment.CreditFragment;
import com.gxc.ui.fragment.HomeFragment;
import com.gxc.ui.fragment.MonitorListFragment;
import com.gxc.ui.fragment.MyFragment;
import com.gxc.ui.widgets.ScrollableViewPager;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import netlib.util.PreferenceUtils;

/**
 * @author liuguangdan
 * @version create at 2018/8/29/029 19:49
 * @Email lgd@jusfoun.com
 * @Description ${首页}
 */
public class HomeActivity extends BaseActivity {

    @BindView(R.id.pager)
    ScrollableViewPager pager;

    @BindView(R.id.navigation)
    BottomNavigationBar navigation;

    String[] titles = new String[]{"首页", "监控动态", "信用服务", "我的"};

    public void selectTab(int position) {
        navigation.selectTab(position);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_home;
    }

    @Override
    public void initActions() {
        navigation
                .addItem(new BottomNavigationItem(R.drawable.selector_tab1, titles[0]))
                .addItem(new BottomNavigationItem(R.drawable.selector_tab2, titles[1]))
                .addItem(new BottomNavigationItem(R.drawable.selector_tab3, titles[2]))
                .addItem(new BottomNavigationItem(R.drawable.selector_tab4, titles[3]))
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setInActiveColor("#ADADAD") // 默认颜色
                .setActiveColor("#E1272E") // 选中颜色
                .setBarBackgroundColor("#FFFFFF") // 导航栏整个背景颜色
                .setFirstSelectedPosition(0) // 默认选中第几个选项卡
                .initialise();

        navigation.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                pager.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        setDoubleClickExitApp(true);

        PreferenceUtils.setString(this, Constants.REGID, "TEST");

        checkUpdate();
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> list = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            list.add(new HomeFragment());
            list.add(new MonitorListFragment());
            list.add(new CreditFragment());
            list.add(new MyFragment());
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    private void checkUpdate() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("versionname", AppUtils.getVersionName(activity));
        map.put("versioncode", AppUtils.getVersionCode(activity));
        map.put("channel", AppUtils.getChannel());
        map.put("from", "Android");
        RxManager.http(RetrofitUtils.getApi().checkUpdate(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    VersionModel versionModel = model.dataToObject(VersionModel.class);
                    if (versionModel != null)
                        new VersionDialog(activity, versionModel).show();
                }
            }

            @Override
            public void error() {

            }
        });
    }
}
