package com.jusfoun.jusfouninquire.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BrandListModel;
import com.jusfoun.jusfouninquire.net.model.BrandModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.route.AssetsRoute;
import com.jusfoun.jusfouninquire.net.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.adapter.BrandAdapter;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

/**
 * CreateDate 2017年9月6日13:54:01
 * Description 商标列表页面
 */
public class BrandListFragment extends BaseViewPagerFragment implements View.OnClickListener {

    private XListView listView;

    private BrandAdapter brandAdapter;

    private int pageIndex = 1;

    private boolean isRefreshing = false; // 是否是正在下拉刷新

    private View rootView;

    private boolean isFirstLoad = true;

    private CompanyDetailModel companyDetailModel;

    private View vEmpty;
    private NetWorkErrorView vHttpError;

    @Override
    protected void initData() {
        brandAdapter = new BrandAdapter(mContext);
        Bundle arguments = getArguments();
        if (arguments != null) {
            companyDetailModel = (CompanyDetailModel) arguments.getSerializable(CompanyDetailsActivity.COMPANY);
        }
    }

    public static BrandListFragment getInstace(Bundle argument) {
        BrandListFragment fragment = new BrandListFragment();
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
                    BrandModel model = (BrandModel) brandAdapter.getItem(i - 1);
                    if(model == null) return;
                    AppUtil.startDetialActivity(getContext(), model.url, "商标详情", model);
                }
            });
        }
        return rootView;
    }

    @Override
    protected void initWeightActions() {
        listView.setAdapter(brandAdapter);
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
        params.put("nameConcat", "");
        params.put("registationNum", "");
        params.put("pageIndex", String.valueOf(pageIndex));
        AssetsRoute.brandListNet(mContext, params, TAG, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                if (isRefreshing) {
                    brandAdapter.getDatas().clear();
                }
                listView.stopRefresh();
                listView.stopLoadMore();

                BrandListModel model = (BrandListModel) data;
                listView.setPullLoadEnable(true);
                if (model.success()) {
                    if (model.dataResult != null && !model.dataResult.isEmpty()) {
                        brandAdapter.addDatas(model.dataResult);
                        pageIndex++;
                    } else {
                        if (isRefreshing)
                            brandAdapter.notifyDataSetChanged();
                        if (pageIndex == 1)
                            vEmpty.setVisibility(View.VISIBLE);
                    }
                    if (model.totalCount == brandAdapter.getCount())
                        listView.setPullLoadEnable(false);
                } else {
                    if (isRefreshing)
                        brandAdapter.notifyDataSetChanged();
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
            brandAdapter.cleanAllData();
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
