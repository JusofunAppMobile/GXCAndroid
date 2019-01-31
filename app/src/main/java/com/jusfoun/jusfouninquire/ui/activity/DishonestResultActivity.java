package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gxc.ui.widgets.ScrollableViewPager;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.FilterModel;
import com.jusfoun.jusfouninquire.net.route.SearchRoute;
import com.jusfoun.jusfouninquire.service.event.DishonestResultEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.adapter.DishonestAdapter;
import com.jusfoun.jusfouninquire.ui.view.FilterDrawerView;
import com.jusfoun.jusfouninquire.ui.view.SearchTitleView;
import com.siccredit.guoxin.R;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/31.
 * Email wcc@jusfoun.com
 * Description 失信搜索结果页
 */
public class DishonestResultActivity extends BaseInquireActivity implements View.OnClickListener {

    protected TextView all;
    protected TextView person;
    protected TextView company;

    private DishonestAdapter adapter;
    private ScrollableViewPager viewPager;
    private FilterDrawerView filterDrawerView;
    private DrawerLayout drawerLayout;
    private HashMap<String, String> params = new HashMap<>();
    private String searchkey;
    private SearchTitleView titleView;

    @Override
    protected void initData() {
        super.initData();
        searchkey = getIntent().getStringExtra(SearchResultActivity.SEARCH_KEY);
        searchkey = searchkey == null ? "" : searchkey;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_dishonest_result);
        titleView =  findViewById(R.id.titleView);
        all = (TextView) findViewById(R.id.all);
        person = (TextView) findViewById(R.id.person);
        company = (TextView) findViewById(R.id.company);
        viewPager = findViewById(R.id.viewpager);
        filterDrawerView = (FilterDrawerView) findViewById(R.id.filter_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    protected void initWidgetActions() {
        all.setOnClickListener(DishonestResultActivity.this);
        person.setOnClickListener(DishonestResultActivity.this);
        company.setOnClickListener(DishonestResultActivity.this);

        titleView.setEditText(searchkey);
        adapter = new DishonestAdapter(getSupportFragmentManager(), searchkey);
        viewPager.setAdapter(adapter);

        initTitleColor();
        all.setTextColor(getResources().getColor(R.color.search_name_color));
        drawerLayout.closeDrawer(filterDrawerView);
        filterNet();

        filterDrawerView.setParams(params);
        filterDrawerView.setListener(new FilterDrawerView.SearchSureListener() {
            @Override
            public void onSure() {
                drawerLayout.closeDrawer(filterDrawerView);
                DishonestResultEvent event = new DishonestResultEvent();
                event.setParams(params);
                event.setPosition(viewPager.getCurrentItem());
                EventBus.getDefault().post(event);
            }
        });

        titleView.setOnFilterClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(filterDrawerView))
                    drawerLayout.closeDrawer(filterDrawerView);
                else
                    drawerLayout.openDrawer(filterDrawerView);
            }
        });
    }

    private void initTitleColor() {
        all.setTextColor(getResources().getColor(R.color.black));
        person.setTextColor(getResources().getColor(R.color.black));
        company.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.all) {
            initTitleColor();
            all.setTextColor(getResources().getColor(R.color.search_name_color));
            viewPager.setCurrentItem(0, false);
        } else if (view.getId() == R.id.person) {
            initTitleColor();
            person.setTextColor(getResources().getColor(R.color.search_name_color));
            viewPager.setCurrentItem(1, false);
        } else if (view.getId() == R.id.company) {
            initTitleColor();
            company.setTextColor(getResources().getColor(R.color.search_name_color));
            viewPager.setCurrentItem(2, false);
        }
    }

    private void filterNet() {
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }
        SearchRoute.getDisFilter(this, params, this.getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                FilterModel model = (FilterModel) data;
                if (model.getResult() == 0) {
                    filterDrawerView.setData(model, false);
                } else {
//                    showToast(model.getMsg());
                }

            }

            @Override
            public void onFail(String error) {
//                showToast(error);
            }
        });
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarRed();
    }
}
