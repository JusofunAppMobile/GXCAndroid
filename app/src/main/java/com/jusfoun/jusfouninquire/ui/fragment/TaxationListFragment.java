package com.jusfoun.jusfouninquire.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.TaxationItemModel;
import com.jusfoun.jusfouninquire.net.model.TaxationModel;
import com.jusfoun.jusfouninquire.net.route.BiddingNetSource;
import com.jusfoun.jusfouninquire.net.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.adapter.TaxationAdapter;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

/**
 * CreateDate 2017年9月6日13:54:01
 * Description 税务 公告
 */
public class TaxationListFragment extends BaseViewPagerFragment implements View.OnClickListener {

    private XListView listView;

    private TaxationAdapter taxationAdapter;

    private int pageIndex = 1;

    private boolean isRefreshing = false; // 是否是正在下拉刷新

    private View rootView;

    private boolean isFirstLoad = true;

    private CompanyDetailModel companyDetailModel;

    private View vEmpty;
    private NetWorkErrorView vHttpError;

    @Override
    protected void initData() {
        taxationAdapter = new TaxationAdapter(mContext);
        Bundle arguments = getArguments();
        if (arguments != null) {
            companyDetailModel = (CompanyDetailModel) arguments.getSerializable(CompanyDetailsActivity.COMPANY);
        }
    }

    public static TaxationListFragment getInstace(Bundle argument) {
        TaxationListFragment fragment = new TaxationListFragment();
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = View.inflate(mContext, R.layout.activity_brand, null);
            listView = (XListView) rootView.findViewById(R.id.list);
            vEmpty = rootView.findViewById(R.id.vEmpty);
            vHttpError = (NetWorkErrorView) rootView.findViewById(R.id.vHttpError);
            vHttpError.setListener(new NetWorkErrorView.OnRefreshListener() {
                @Override
                public void OnNetRefresh() {
                    reset();
                    vHttpError.setVisibility(View.GONE);
                    isFirstLoad = true;
                    loadData();
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TaxationItemModel model = (TaxationItemModel) taxationAdapter.getItem(i - 1);
                    if (model == null) return;
                    AppUtil.startDetialActivity(getContext(), model.url, "欠税信息", model);
                }
            });
        }
        return rootView;
    }

    @Override
    protected void initWeightActions() {
        listView.setAdapter(taxationAdapter);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                reset();
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });

    }

    private void reset() {
        pageIndex = 1;
    }

    @Override
    public void onClick(View view) {
    }

    private void loadData() {

        if (companyDetailModel == null)
            return;

        if (isFirstLoad) {
            showLoading();
            isFirstLoad = false;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("pageSize", "20");
        params.put("entName", companyDetailModel.getCompanyname());
        params.put("taxCode", "");
        params.put("pageIndex", pageIndex + "");
        BiddingNetSource.getTaxationNet(mContext, params, TAG, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                if (isRefreshing) {
                    taxationAdapter.getDatas().clear();
                }
                listView.stopRefresh();
                listView.stopLoadMore();

                TaxationModel model = (TaxationModel) data;
                listView.setPullLoadEnable(true);
                if (model.success()) {
                    if (model.dataResult != null && !model.dataResult.isEmpty()) {
                        taxationAdapter.addDatas(model.dataResult);
                        pageIndex++;
                    } else {
                        if (isRefreshing)
                            taxationAdapter.notifyDataSetChanged();
                        if (pageIndex == 1)
                            vEmpty.setVisibility(View.VISIBLE);
                    }
                    if (model.totalCount == taxationAdapter.getCount())
                        listView.setPullLoadEnable(false);
                } else {
                    if (isRefreshing)
                        taxationAdapter.notifyDataSetChanged();
                    handleError();
                }

                isRefreshing = false;
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                isRefreshing = false;
                handleError();
                listView.stopRefresh();
                listView.stopLoadMore();
            }
        });
    }

    private void handleError() {
        if (pageIndex == 1) {
            vHttpError.setVisibility(View.VISIBLE);
            taxationAdapter.cleanAllData();
            listView.setPullLoadEnable(false);
        } else
            showToast("网络连接失败");
    }

    @Override
    protected void setViewHint() {

    }

    @Override
    protected void refreshData() {
        loadData();
    }
}
