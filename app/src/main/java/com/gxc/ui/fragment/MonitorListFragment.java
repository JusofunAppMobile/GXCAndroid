package com.gxc.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListFragment;
import com.gxc.model.MonitorModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.activity.MonitorDetailActivity;
import com.gxc.ui.adapter.MonitorAdpater;
import com.jusfoun.jusfouninquire.R;

import java.util.HashMap;

/**
 * @author liuguangdan
 * @version create at 2019/1/4/004 19:39
 * @Email lgd@jusfoun.com
 * @Description ${监控动态}
 */
public class MonitorListFragment extends BaseListFragment {

    @Override
    public int getLayoutId() {
        return R.layout.frag_monitor;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MonitorAdpater(activity);
    }

    @Override
    protected void initUi() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        MonitorModel model = (MonitorModel) adapter.getItem(position);
        startActivity(MonitorDetailActivity.getIntent(activity, model.companyId, model.companyName));
    }

    @Override
    protected void startLoadData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageSize", pageSize);
        map.put("pageIndex", pageIndex);

        RxManager.http(RetrofitUtils.getApi().monitorList(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success())
                    completeLoadData(model.dataToList("monitor", MonitorModel.class));
                else
                    completeLoadDataError();
            }

            @Override
            public void error() {
                completeLoadDataError();
            }
        });
    }

}
