package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BiddingListModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.route.BiddingNetSource;
import com.jusfoun.jusfouninquire.net.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.adapter.BiddingAdapter;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

/**
 * @author zhaoyapeng
 * @version create time:17/9/717:08
 * @Email zyp@jusfoun.com
 * @Description ${招投标 fragment}
 */
public class BiddingFragment extends BaseViewPagerFragment {
    private XListView listView;
    private BiddingAdapter biddingAdapter;

    private int pageIndex = 1;
    private int pageSize = 20;

    private CompanyDetailModel companyDetailModel;
    private NetWorkErrorView netErrorLayout;

    public static BiddingFragment getInstace(Bundle argument) {
        BiddingFragment fragment = new BiddingFragment();
        fragment.setArguments(argument);
        return fragment;

    }

    @Override
    protected void initData() {
        biddingAdapter = new BiddingAdapter(mContext);
        Bundle arguments = getArguments();
        if (arguments != null) {
            companyDetailModel = (CompanyDetailModel) arguments.getSerializable(CompanyDetailsActivity.COMPANY);
        }
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bidding, container, false);
        netErrorLayout = (NetWorkErrorView) view.findViewById(R.id.net_work_error);
        listView = (XListView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    protected void initWeightActions() {
        listView.setAdapter(biddingAdapter);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BiddingListModel.BiddingItemModel model = (BiddingListModel.BiddingItemModel) biddingAdapter.getItem(i - 1);
                if(model == null) return;
                AppUtil.startDetialActivity(getContext(), model.url, "中标详情", model);
            }
        });

        netErrorLayout.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                pageIndex = 1;
                loadData();
            }
        });

    }

    private void loadData() {
        if (companyDetailModel == null) {
            return;
        }
        if (pageIndex == 1)
            showLoading();

        HashMap<String, String> params = new HashMap<>();

        params.put("pageSize", pageSize + "");
        params.put("entName", companyDetailModel.getCompanyname());
        params.put("pageIndex", pageIndex + "");
        String tag = "";
        if (mContext != null) {
            tag = ((Activity) mContext).getLocalClassName();
        }
        BiddingNetSource.getBiddimgList(mContext, params, tag, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                listView.stopRefresh();
                listView.stopLoadMore();
                hideLoadDialog();
                BiddingListModel model = (BiddingListModel) data;
                if (model.getResult() == 0) {
                    if (pageIndex == 1) {
                        biddingAdapter.refresh(model.dataResult);
                    } else {
                        biddingAdapter.addData(model.dataResult);
                    }
                    pageIndex++;
                    if (biddingAdapter.getCount() >= model.totalCount) {
                        listView.setPullLoadEnable(false);
                    } else {
                        listView.setPullLoadEnable(true);
                    }
                } else {
                    if (pageIndex == 1) {
                        netErrorLayout.setServerError();
                        netErrorLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (!TextUtils.isEmpty(model.getMsg()))
                            Toast.makeText(mContext, model.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFail(String error) {
                listView.stopRefresh();
                listView.stopLoadMore();
                hideLoadDialog();
                if (pageIndex == 1) {
                    netErrorLayout.setNetWorkError();
                    netErrorLayout.setVisibility(View.VISIBLE);
                } else {
                    if (!TextUtils.isEmpty(error))
                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void setViewHint() {

    }

    @Override
    protected void refreshData() {
        loadData();
    }
}
