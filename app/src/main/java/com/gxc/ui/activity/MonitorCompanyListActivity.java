package com.gxc.ui.activity;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.MonitorModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.MonitorCompanyAdapter;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 14:10
 * @Email lgd@jusfoun.com
 * @Description ${我的监控、我的收藏}
 */
public class MonitorCompanyListActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MonitorCompanyAdapter();
    }

    @Override
    protected void initUi() {
        titleView.setTitle("我的监控");
        View view = View.inflate(this, R.layout.view_num_header, null);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(Html.fromHtml("数量： <font color='#E2292F'>2</font>条"));
        adapter.addHeaderView(view);
    }

    @Override
    protected void startLoadData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageSize", pageSize);
        map.put("pageIndex", pageIndex);
        RxManager.http(RetrofitUtils.getApi().myMonitorList(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    completeLoadData(model.dataToList("list", MonitorModel.class));
                } else {
                    completeLoadDataError();
                }
            }

            @Override
            public void error() {
                completeLoadDataError();
            }
        });
    }
}
