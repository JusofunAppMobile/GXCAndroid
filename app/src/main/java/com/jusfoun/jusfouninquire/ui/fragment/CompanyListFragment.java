package com.jusfoun.jusfouninquire.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BidsModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.CompanyListModel;
import com.jusfoun.jusfouninquire.net.model.PublishModel;
import com.jusfoun.jusfouninquire.net.model.StockModel;
import com.jusfoun.jusfouninquire.net.route.AssetsRoute;
import com.jusfoun.jusfouninquire.net.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.adapter.CompanyAdapter;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

import static com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant.TYPE_BIDS;
import static com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant.TYPE_PUBLISH;
import static com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant.TYPE_STOCK;

/**
 * CreateDate 2017年9月6日13:54:01
 * Description 企业详情中子功能列表
 */
public class CompanyListFragment extends BaseViewPagerFragment implements View.OnClickListener {

    private XListView listView;

    private CompanyAdapter adapter;

    private int pageIndex = 1;

    private boolean isRefreshing = false; // 是否是正在下拉刷新

    private View rootView;

    private boolean isFirstLoad = true;

    private CompanyDetailModel companyDetailModel;

    private View vEmpty;
    private NetWorkErrorView vHttpError;

    private int type;

    private TextView tvLabelType, tvCount;
    private RelativeLayout headerLayout;
    @Override
    protected void initData() {
        type = getArguments().getInt("type");
        adapter = new CompanyAdapter(mContext, type);
        Bundle arguments = getArguments();
        if (arguments != null) {
            companyDetailModel = (CompanyDetailModel) arguments.getSerializable(CompanyDetailsActivity.COMPANY);
        }
    }

    public static CompanyListFragment getInstace(Bundle argument, int type) {
        argument.putInt("type", type);
        CompanyListFragment fragment = new CompanyListFragment();
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = View.inflate(mContext, R.layout.activity_brand, null);
            listView = (XListView) rootView.findViewById(R.id.list);
            vEmpty = rootView.findViewById(R.id.vEmpty);

            headerLayout = (RelativeLayout)rootView.findViewById(R.id.vHeader);
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
                    i--;
                    if (i >= adapter.getCount())
                        return;
                    Gson gson = new Gson();
                    if (type == TYPE_PUBLISH) {// 行政处罚
                        PublishModel model = gson.fromJson(gson.toJson(adapter.getItem(i)), PublishModel.class);
                        if(model == null) return;
                        AppUtil.startDetialActivity(getContext(), model.url, "行政处罚", model);
                    } else if (type == TYPE_STOCK) { // 股权出质
                        StockModel model = gson.fromJson(gson.toJson(adapter.getItem(i)), StockModel.class);
                        if(model == null) return;
                        AppUtil.startDetialActivity(getContext(), model.url, "股权出质", model);
                    } else if (type == TYPE_BIDS) {// 招标
                        BidsModel model = gson.fromJson(gson.toJson(adapter.getItem(i)), BidsModel.class);
                        if(model == null) return;
                        AppUtil.startDetialActivity(getContext(), model.url, "招标公告", model);
                    }
                }
            });

            TextView tvLabelInfo = (TextView) rootView.findViewById(R.id.tvLabelInfo);
            tvLabelInfo.setText("共");
            tvLabelType = (TextView) rootView.findViewById(R.id.tvLabelType);
            tvCount = (TextView) rootView.findViewById(R.id.tvCount);
            setLabel();
        }
        return rootView;
    }

    @Override
    protected void initWeightActions() {
        headerLayout.setVisibility(View.VISIBLE);


        listView.setAdapter(adapter);
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

    private void setTotalCount(long count) {
        tvCount.setText(String.valueOf(count));
    }

    private void setLabel() {
        if (type == TYPE_PUBLISH) // 行政处罚
            tvLabelType.setText("个行政处罚");
        else if (type == TYPE_STOCK) // 股权出质
            tvLabelType.setText("个股权出质");
        else if (type == TYPE_BIDS) // 招标
            tvLabelType.setText("个招标");
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

        String url = "";
        if (type == TYPE_PUBLISH) // 行政处罚
            url = "/api/entdetail/GetEntXZPunishmentInfoModel";
        else if (type == TYPE_STOCK) // 股权出质
            url = "/api/entdetail/GetEstateMortgageByEntName";
        else if (type == TYPE_BIDS) // 招标
            url = "/api/entdetail/GetTenderNoticeRawByProcurementCompany";
        AssetsRoute.companyItem(mContext, params, TAG, url, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                if (isRefreshing) {
                    adapter.getDatas().clear();
                }
                listView.stopRefresh();
                listView.stopLoadMore();

                CompanyListModel model = (CompanyListModel) data;
                listView.setPullLoadEnable(true);
                if (model.success()) {
                    if (model.dataResult != null && !model.dataResult.isEmpty()) {
                        adapter.addDatas(model.dataResult);
                        pageIndex++;
                    } else {
                        if (isRefreshing)
                            adapter.notifyDataSetChanged();
                        if (pageIndex == 1)
                            vEmpty.setVisibility(View.VISIBLE);
                    }
                    if (model.totalCount == adapter.getCount())
                        listView.setPullLoadEnable(false);
                    setTotalCount(model.totalCount);
                } else {
                    if (isRefreshing)
                        adapter.notifyDataSetChanged();
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
            adapter.cleanAllData();
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
