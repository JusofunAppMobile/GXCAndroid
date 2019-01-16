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
import com.gxc.ui.adapter.MyMonitorAdapter;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 14:10
 * @Email lgd@jusfoun.com
 * @Description ${我的监控}
 */
public class MyMonitorListActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    private View headView;
    private TextView tvNum;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MyMonitorAdapter(this);
    }

    @Override
    protected void initUi() {
        titleView.setTitle("我的监控");
        headView = View.inflate(this, R.layout.view_num_header, null);
        tvNum = headView.findViewById(R.id.textView);
        tvNum.setText(Html.fromHtml("数量： <font color='#E2292F'>0</font>条"));
    }

    private void setCount(int count) {
        tvNum.setText(Html.fromHtml("数量： <font color='#E2292F'>" + count + "</font>条"));
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
                    List<MonitorModel> list = model.dataToList("list", MonitorModel.class);
                    if (list != null && !list.isEmpty() && pageIndex == 1 && headView.getParent() == null) {
                        adapter.addHeaderView(headView);
                    }
                    if( pageIndex == 1 && (list == null || list.isEmpty()))
                        adapter.removeHeaderView(headView);
                    setCount(model.getDataByKey("totalCount", Integer.class));
                    completeLoadData(list);
                } else {
                    adapter.removeHeaderView(headView);
                    completeLoadDataError();
                }
            }

            @Override
            public void error() {
                adapter.removeHeaderView(headView);
                completeLoadDataError();
            }
        });
    }
}
