package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.FilterModel;
import com.jusfoun.jusfouninquire.net.model.SearchDisHonestModel;
import com.jusfoun.jusfouninquire.net.route.SearchRoute;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.service.event.DishonestResultEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.adapter.DishonestAdapter;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.view.FilterDrawerView;
import com.jusfoun.jusfouninquire.ui.widget.SearchViewPager;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/31.
 * Email wcc@jusfoun.com
 * Description 失信搜索结果页
 */
public class DishonestResultActivity extends BaseInquireActivity implements View.OnClickListener {

    public static final String SEARCHKEY="searchkey";
    public static final String MODEL="model";
    protected ImageView imgBack;
    protected TextView editSearch;
    protected TextView all;
    protected TextView person;
    protected TextView company;
    protected TextView filter;
    protected ImageView clear;

    private DishonestAdapter adapter;
    private SearchViewPager viewPager;
    private FilterDrawerView filterDrawerView;
    private DrawerLayout drawerLayout;
    private HashMap<String,String> params=new HashMap<>();
    private String searchkey;
    private SearchDisHonestModel model;

    @Override
    protected void initData() {
        super.initData();
        searchkey=getIntent().getStringExtra(SEARCHKEY);
        searchkey=searchkey==null?"":searchkey;
        model= (SearchDisHonestModel) getIntent().getSerializableExtra(MODEL);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_dishonest_result);
        imgBack = (ImageView) findViewById(R.id.img_back);
        clear = (ImageView) findViewById(R.id.clear);
        editSearch = (TextView) findViewById(R.id.edit_search);
        all = (TextView) findViewById(R.id.all);
        person = (TextView) findViewById(R.id.person);
        company = (TextView) findViewById(R.id.company);
        filter = (TextView) findViewById(R.id.filter);
        viewPager= (SearchViewPager) findViewById(R.id.viewpager);
        filterDrawerView= (FilterDrawerView) findViewById(R.id.filter_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    protected void initWidgetActions() {
        editSearch.setOnClickListener(DishonestResultActivity.this);
        all.setOnClickListener(DishonestResultActivity.this);
        person.setOnClickListener(DishonestResultActivity.this);
        imgBack.setOnClickListener(DishonestResultActivity.this);
        company.setOnClickListener(DishonestResultActivity.this);
        clear.setOnClickListener(this);
        filter.setOnClickListener(this);

        TouchUtil.createTouchDelegate(clear, PhoneUtil.dip2px(mContext,50));
        TouchUtil.createTouchDelegate(imgBack, PhoneUtil.dip2px(mContext,50));

        editSearch.setText(searchkey);
        adapter=new DishonestAdapter(getSupportFragmentManager(),searchkey,model);
        viewPager.setmNotTouchScoll(true);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        initTitleColor();
        all.setTextColor(getResources().getColor(R.color.search_name_color));
        viewPager.setCurrentItem(0);
        drawerLayout.closeDrawer(filterDrawerView);
        filterNet();

        filterDrawerView.setParams(params);
        filterDrawerView.setListener(new FilterDrawerView.SearchSureListener() {
            @Override
            public void onSure() {
                drawerLayout.closeDrawer(filterDrawerView);
                DishonestResultEvent event=new DishonestResultEvent();
                event.setParams(params);
                event.setPosition(viewPager.getCurrentItem());
                EventBus.getDefault().post(event);
            }
        });
    }

    private void initTitleColor(){
        all.setTextColor(getResources().getColor(R.color.black));
        person.setTextColor(getResources().getColor(R.color.black));
        company.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            setResult(SearchDishonestActivity.RESULT_FINISH);
            onBackPressed();
        } else if (view.getId() == R.id.edit_search) {

        } else if (view.getId() == R.id.layout_search) {

        } else if (view.getId() == R.id.all) {
            initTitleColor();
            all.setTextColor(getResources().getColor(R.color.search_name_color));
            viewPager.setCurrentItem(0);
        } else if (view.getId() == R.id.person) {
            initTitleColor();
            person.setTextColor(getResources().getColor(R.color.search_name_color));
            viewPager.setCurrentItem(1);
        } else if (view.getId() == R.id.company) {
            initTitleColor();
            company.setTextColor(getResources().getColor(R.color.search_name_color));
            viewPager.setCurrentItem(2);
        }else if (view.getId()==R.id.filter){
            if (drawerLayout.isDrawerOpen(filterDrawerView))
                drawerLayout.closeDrawer(filterDrawerView);
            else
                drawerLayout.openDrawer(filterDrawerView);
        }else if (view.getId()==R.id.clear){
            setResult(SearchDishonestActivity.RESULT_CLEAR);
            onBackPressed();
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
                FilterModel model= (FilterModel) data;
                if (model.getResult()==0){
                    filterDrawerView.setData(model,false);
                }else {
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
