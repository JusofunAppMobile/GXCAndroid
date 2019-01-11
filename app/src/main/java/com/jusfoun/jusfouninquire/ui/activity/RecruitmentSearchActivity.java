package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.RecruitmentModel;
import com.jusfoun.jusfouninquire.net.route.SearchRoute;
import com.jusfoun.jusfouninquire.net.util.AppUtil;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.adapter.RecruitmentListAdapter;
import com.jusfoun.jusfouninquire.ui.view.CommonSearchTitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

import netlib.util.EventUtils;

/**
 * @author zhaoyapeng
 * @version create time:18/1/415:45
 * @Email zyp@jusfoun.com
 * @Description ${招聘 搜索页面}
 */
public class RecruitmentSearchActivity extends BaseInquireActivity {

    protected CommonSearchTitleView searchTitleView;
    protected TextView textCount;
    protected XListView searchResultListview;

    private RecruitmentListAdapter adapter;

    public static String SEARCH_RESULT = "search_result";

    private RecruitmentModel recruitmentModel;
    private String searchKey;

    private int pageIndex = 1;

    @Override
    protected void initData() {
        super.initData();
        adapter = new RecruitmentListAdapter(mContext);

        if (getIntent() != null) {
            if (getIntent().getSerializableExtra(SEARCH_RESULT) != null &&
                    getIntent().getSerializableExtra(SEARCH_RESULT) instanceof RecruitmentModel) {
                recruitmentModel = (RecruitmentModel) getIntent().getSerializableExtra(SEARCH_RESULT);
            }
            searchKey = getIntent().getStringExtra("searchKey");
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search_recruitment);
        searchTitleView = (CommonSearchTitleView) findViewById(R.id.search_title_view);
        textCount = (TextView) findViewById(R.id.text_count);
        searchResultListview = (XListView) findViewById(R.id.search_result_listview);
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
        searchResultListview.setAdapter(adapter);
        searchResultListview.setPullLoadEnable(false);
        searchResultListview.setPullRefreshEnable(true);

        if (recruitmentModel != null) {
            if (recruitmentModel.dataResult != null) {
                adapter.refresh(recruitmentModel.dataResult);
                if (adapter.getCount() >= recruitmentModel.totalCount) {
                    searchResultListview.setPullLoadEnable(false);
                } else {
                    pageIndex++;
                    searchResultListview.setPullLoadEnable(true);
                }
            }
            textCount.setText(recruitmentModel.totalCount + "");
        }
        searchResultListview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                searchRecruitmentNet(searchKey);
            }

            @Override
            public void onLoadMore() {
                searchRecruitmentNet(searchKey);
            }
        });

        searchResultListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getTag() instanceof RecruitmentListAdapter.ViewHolder) {
                    RecruitmentListAdapter.ViewHolder holder = (RecruitmentListAdapter.ViewHolder) view.getTag();
                    if (holder == null) {
                        return;
                    }
                    RecruitmentModel.RecruitmentItemModel model = holder.model;
                    if (model != null) {
//                        Intent intent = new Intent(mContext, CompanyDetailActivity.class);
//                        if (!TextUtils.isEmpty(model.associatedCompanyId))
//                            intent.putExtra(CompanyDetailActivity.COMPANY_ID, model.associatedCompanyId);
//                        if (!TextUtils.isEmpty(model.companyName))
//                            intent.putExtra(CompanyDetailActivity.COMPANY_NAME, model.companyName);
//                        startActivity(intent);

                        AppUtil.startDetialActivity(mContext,model.url,"招聘详情",model);
                    }

                }
            }
        });


        searchTitleView.showSearchText();
        if (!TextUtils.isEmpty(searchKey)) {
            searchTitleView.setEditHint(searchKey);
        }

    }

    /**
     * 搜索招聘
     */
    private void searchRecruitmentNet(final String key) {
        TimeOut timeOut = new TimeOut(mContext);
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }
        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");

        params.put("entName", key);
        params.put("pageSize", "20");
        params.put("pageIndex", ""+pageIndex);
        showLoading();
        EventUtils.event(mContext, EventUtils.SEARCH29);
        SearchRoute.searchRecruitmentNet(this, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                searchResultListview.stopLoadMore();
                searchResultListview.stopRefresh();
                if (data instanceof RecruitmentModel) {

                    RecruitmentModel model = (RecruitmentModel) data;
                    if (model.getResult() == 0) {
                        if (pageIndex == 1) {
                            adapter.refresh(model.dataResult);
                        } else {
                            adapter.addData(model.dataResult);
                        }
                        if (adapter.getCount() >= model.totalCount) {
                            searchResultListview.setPullLoadEnable(false);
                        } else {
                            searchResultListview.setPullLoadEnable(true);
                        }
                        pageIndex++;
                    } else {
                        if (model.getResult() != -1) {
                            if (!TextUtils.isEmpty(model.getMsg())) {
                                showToast(model.getMsg());
                            } else {
                                showToast("网络不给力，请稍后重试");
                            }
                        }

                    }
                }
            }

            @Override
            public void onFail(String error) {
                searchResultListview.stopLoadMore();
                searchResultListview.stopRefresh();
                hideLoadDialog();
                showToast("网络不给力，请稍后重试");
            }
        });
    }

}
