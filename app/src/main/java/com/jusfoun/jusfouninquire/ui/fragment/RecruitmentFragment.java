package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.RecruitListModel;
import com.jusfoun.jusfouninquire.net.route.BiddingNetSource;
import com.jusfoun.jusfouninquire.net.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.adapter.RecruitmentAdapter;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

/**
 * @author zhaoyapeng
 * @version create time:17/9/717:08
 * @Email zyp@jusfoun.com
 * @Description ${招聘 fragment}
 */
public class RecruitmentFragment extends BaseViewPagerFragment {
    private XListView listView;
    private RecruitmentAdapter biddingAdapter;

    private int pageIndex = 1;
    private int pageSize = 20;
    private NetWorkErrorView netErrorLayout;
    private CompanyDetailModel companyDetailModel;

    public static RecruitmentFragment getInstace(Bundle argument) {
        RecruitmentFragment fragment = new RecruitmentFragment();
        fragment.setArguments(argument);
        return fragment;

    }

    @Override
    protected void initData() {
        biddingAdapter = new RecruitmentAdapter(mContext);
        Bundle arguments = getArguments();
        if (arguments != null) {
            companyDetailModel = (CompanyDetailModel) arguments.getSerializable(CompanyDetailsActivity.COMPANY);
        }
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bidding, container, false);
        listView = (XListView) view.findViewById(R.id.list);
        netErrorLayout = (NetWorkErrorView) view.findViewById(R.id.net_work_error);
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
        listView.setPullLoadEnable(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecruitListModel.RecruitItemModel model = (RecruitListModel.RecruitItemModel) biddingAdapter.getItem(i - 1);
                if(model == null) return;
                AppUtil.startDetialActivity(mContext, model.url, "招聘详情", model);
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
        BiddingNetSource.getRecruitmentList(mContext, params, tag, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                RecruitListModel model = (RecruitListModel) data;
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
