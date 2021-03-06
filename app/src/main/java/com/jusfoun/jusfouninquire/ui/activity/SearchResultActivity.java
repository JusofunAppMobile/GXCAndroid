package com.jusfoun.jusfouninquire.ui.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;

import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.FilterModel;
import com.jusfoun.jusfouninquire.net.route.SearchRoute;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.fragment.SearchResultFragment;
import com.jusfoun.jusfouninquire.ui.view.FilterDrawerView;
import com.jusfoun.jusfouninquire.ui.view.SearchTitleView;
import com.siccredit.guoxin.R;

import java.util.HashMap;

import netlib.util.EventUtils;

/**
 * SearchResultActivity
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/9
 * @Description :搜索结果页面
 */
//@RuntimePermissions
public class SearchResultActivity extends BaseInquireActivity {

    public static final String SEARCH_RESULT = "search_result";
    public static final String SEARCH_TYPE = "search_type";
    public static final String SEARCH_KEY = "search_key";

    private DrawerLayout mDrawerLayout;
    private FilterDrawerView mFilterView;
    private SearchResultFragment contentFragment;
    private SearchTitleView mTitle;

    //    private SearchListModel mData;
    private String mCurrentType;
    private String mSearchKey;
    private HashMap<String, String> params;


    @Override
    protected void initData() {
        super.initData();

        if (getIntent() != null) {
//            if (getIntent().getSerializableExtra(SEARCH_RESULT) != null &&
//                    getIntent().getSerializableExtra(SEARCH_RESULT) instanceof SearchListModel) {
//                mData = (SearchListModel) getIntent().getSerializableExtra(SEARCH_RESULT);
//            }
            mCurrentType = getIntent().getStringExtra(SEARCH_TYPE);
            mSearchKey = getIntent().getStringExtra(SEARCH_KEY);
        }
        params = new HashMap<>();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search_result);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFilterView = (FilterDrawerView) findViewById(R.id.filter_drawer);
        mTitle = (SearchTitleView) findViewById(R.id.search_title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarRed();
    }

    @Override
    public boolean isBarDark() {
        return false;
    }


    @Override
    protected void initWidgetActions() {
        contentFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SearchResultFragment.SEARCH_RESULT, getIntent().getSerializableExtra(SEARCH_RESULT));
        if (!TextUtils.isEmpty(mCurrentType)) {
            bundle.putString(SEARCH_TYPE, mCurrentType);
        }
        if (!TextUtils.isEmpty(mSearchKey)) {
            bundle.putString(SEARCH_KEY, mSearchKey);
        }
        contentFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.search_result_content, contentFragment).commit();

        mDrawerLayout.closeDrawer(mFilterView);

        if (!TextUtils.isEmpty(mSearchKey)) {
            mTitle.setEditText(mSearchKey);
        }

        mTitle.setOnFilterClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.SEARCH47);
                if (mDrawerLayout.isDrawerOpen(mFilterView)) {
                    mDrawerLayout.closeDrawer(mFilterView);
                } else {
                    mDrawerLayout.openDrawer(mFilterView);
                }
            }
        });

//        mFilterView.addHeader();
        mFilterView.setParams(params);
        mFilterView.setListener(new FilterDrawerView.SearchSureListener() {
            @Override
            public void onSure() {
                //TODO 搜索企业
                mDrawerLayout.closeDrawer(mFilterView);
                contentFragment.doFilterSearch(params);

            }
        });
        filterNet();

        // 延迟开始定位，避免权限弹出框弹出后 出现黑色背景
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SearchResultActivityPermissionsDispatcher.getPermissionWithPermissionCheck(SearchResultActivity.this);
//            }
//        }, 500);
    }

    private void filterNet() {
//        showLoading();
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }
        SearchRoute.getFilter(this, params, this.getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
//                hideLoadDialog();
                FilterModel model = (FilterModel) data;
                if (model.getResult() == 0) {
                    mFilterView.setData(model, true);
                } else {
//                    showToast(model.getMsg());
                }

            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(error);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    //    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//    void getPermission() {
//        mFilterView.startLocation();
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        SearchResultActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }
}
