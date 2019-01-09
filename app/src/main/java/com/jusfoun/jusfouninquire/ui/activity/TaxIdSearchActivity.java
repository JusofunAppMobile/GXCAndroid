package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.model.TaxationDataModel;
import com.jusfoun.jusfouninquire.net.model.TaxationItemModel;
import com.jusfoun.jusfouninquire.net.route.SearchRoute;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.adapter.TaxIdAdapter;
import com.jusfoun.jusfouninquire.ui.view.CommonSearchTitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

import netlib.util.EventUtils;
import com.jusfoun.jusfouninquire.TimeOut;

/**
 * @author zhaoyapeng
 * @version create time:18/1/415:45
 * @Email zyp@jusfoun.com
 * @Description ${税号 搜索页面}
 */
public class TaxIdSearchActivity extends BaseInquireActivity {

    protected CommonSearchTitleView searchTitleView;
    protected XListView searchResultListview;
    protected TextView textCount;
    private TaxIdAdapter taxIdAdapter;
    private String searchKey = "";
    public static String SEARCH_RESULT = "search_result";
    private TaxationDataModel taxationDataModel;
    private int pageIndex = 1;

    @Override
    protected void initData() {
        super.initData();
        taxIdAdapter = new TaxIdAdapter(mContext);

        if (getIntent() != null) {
            if (getIntent().getSerializableExtra(SEARCH_RESULT) != null &&
                    getIntent().getSerializableExtra(SEARCH_RESULT) instanceof TaxationDataModel) {
                taxationDataModel = (TaxationDataModel) getIntent().getSerializableExtra(SEARCH_RESULT);
            }
            searchKey = getIntent().getStringExtra("searchKey");
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search_taxid);
        searchTitleView = (CommonSearchTitleView) findViewById(R.id.search_title_view);
        searchResultListview = (XListView) findViewById(R.id.search_result_listview);
        textCount = (TextView) findViewById(R.id.text_count);


    }

    @Override
    protected void initWidgetActions() {
        searchResultListview.setAdapter(taxIdAdapter);

        searchResultListview.setPullLoadEnable(false);
        searchResultListview.setPullRefreshEnable(true);
        if (taxationDataModel != null && taxationDataModel.dataResult != null) {
            taxIdAdapter.refresh(taxationDataModel.dataResult);
            if (taxIdAdapter.getCount() >= taxationDataModel.totalCount) {
                searchResultListview.setPullLoadEnable(false);
            } else {
                pageIndex++;
                searchResultListview.setPullLoadEnable(true);
            }
            textCount.setText(taxationDataModel.totalCount+"");
        }


        if (!TextUtils.isEmpty(searchKey)) {
            searchTitleView.setEditHint(searchKey);
        }


        searchResultListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getTag() instanceof TaxIdAdapter.ViewHolder) {
                    TaxIdAdapter.ViewHolder holder = (TaxIdAdapter.ViewHolder) view.getTag();
                    if (holder == null) {
                        return;
                    }
                    TaxationItemModel model = holder.model;
                    if (model != null) {
                        Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                        if(!TextUtils.isEmpty(model.companyid))
                        intent.putExtra(CompanyDetailActivity.COMPANY_ID, model.companyid);
                        if(!TextUtils.isEmpty(model.companyname))
                        intent.putExtra(CompanyDetailActivity.COMPANY_NAME, model.companyname);
                        startActivity(intent);
                    }

                }
            }
        });


        searchTitleView.setTitleListener(new CommonSearchTitleView.TitleListener() {
            @Override
            public void onTextChange(String key) {

            }

            @Override
            public void onDoSearch(String key) {

            }
        });

        searchResultListview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                searchTaxidNet(searchKey);
            }

            @Override
            public void onLoadMore() {
                searchTaxidNet(searchKey);
            }
        });


        searchTitleView.showSearchText();
    }

    /**
     * 搜索税号
     */
    private void searchTaxidNet(final String key) {
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

        params.put("searchkey", key);
        params.put("type", SearchHistoryItemModel.SEARCH_TAXID);
        params.put("pageSize", "20");
        params.put("pageIndex", pageIndex+"");
        showLoading();
        EventUtils.event(mContext, EventUtils.SEARCH29);
        SearchRoute.searchTaxIdNet(this, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                searchResultListview.stopLoadMore();
                searchResultListview.stopRefresh();
                hideLoadDialog();
                if (data instanceof TaxationDataModel) {
                    TaxationDataModel model = (TaxationDataModel) data;
                    if (model.getResult() == 0) {
                        if (pageIndex == 1) {
                            taxIdAdapter.refresh(model.dataResult);
                        } else {
                            taxIdAdapter.addData(model.dataResult);
                        }
                        if (taxIdAdapter.getCount() >= model.totalCount) {
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
