package com.gxc.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.impl.ListResponseCall;
import com.gxc.model.HistoryModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.MyHistoryAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 14:10
 * @Email lgd@jusfoun.com
 * @Description ${浏览历史}
 */
public class MyHistoryListActivity extends BaseListActivity implements TitleView.OnRightClickListener {

    @BindView(R.id.titleView)
    TitleView titleView;

    private View headView;
    private TextView tvNum;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MyHistoryAdapter();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        HistoryModel model = (HistoryModel) adapter.getItem(position);
        startActivity(CompanyDetailActivity.getIntent(this, model.companyid, model.companyname));
    }

    @Override
    protected void initUi() {
        titleView.setTitle("浏览历史");
        headView = View.inflate(this, R.layout.view_num_header, null);
        tvNum = headView.findViewById(R.id.textView);
        tvNum.setText(AppUtils.getNumFont2(activity, 0));

        titleView.setRightText("清空");
        titleView.setRightClickListener(this);
    }

    private void setCount(int count) {
        tvNum.setText(AppUtils.getNumFont2(activity, count));
    }

    @Override
    protected void startLoadData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageSize", pageSize);
        map.put("pageIndex", pageIndex);
        RxManager.http(RetrofitUtils.getApi().myHistory(map), new ListResponseCall(this) {

            @Override
            public List getList(NetModel model) {
                return model.dataToList("list", HistoryModel.class);
            }

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    List<HistoryModel> list = getList(model);
                    if (list != null && !list.isEmpty() && pageIndex == 1 && headView.getParent() == null) {
                        adapter.addHeaderView(headView);
                    }
                    if( pageIndex == 1 && (list == null || list.isEmpty()))
                        adapter.removeHeaderView(headView);
                    setCount(model.getDataByKey("totalCount", Integer.class));
                } else {
                    adapter.removeHeaderView(headView);
                }
                super.success(model);
            }
        });
    }

    @Override
    public void onClick(View v) {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        RxManager.http(RetrofitUtils.getApi().delBrowseHistory(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                   refresh();
                } else {
                   showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                completeLoadDataError();
            }
        });
    }
}
