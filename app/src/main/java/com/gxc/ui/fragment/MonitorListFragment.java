package com.gxc.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListFragment;
import com.gxc.event.LoginChangeEvent;
import com.gxc.event.MonitorChangeEvent;
import com.gxc.event.MonitorStatusEvent;
import com.gxc.impl.ListResponseCall;
import com.gxc.model.MonitorModel;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.activity.LoginActivity;
import com.gxc.ui.activity.MonitorDetailActivity;
import com.gxc.ui.activity.PayActivity;
import com.gxc.ui.adapter.MonitorAdpater;
import com.gxc.ui.widgets.NavTitleView;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;

import java.util.HashMap;
import java.util.List;

/**
 * @author liuguangdan
 * @version create at 2019/1/4/004 19:39
 * @Email lgd@jusfoun.com
 * @Description ${监控动态}
 */
public class MonitorListFragment extends BaseListFragment {

    private NavTitleView headView;

    @Override
    public int getLayoutId() {
        return R.layout.frag_monitor;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        UserModel user = AppUtils.getUser();
        if (user != null)
            pageSize = 10;
        else
            pageSize = Integer.MAX_VALUE;
        return new MonitorAdpater(activity);
    }

    @Override
    protected void initUi() {
        headView = new NavTitleView(activity);
        headView.setLabel("企业动态");
        headView.hideImageView();
        headView.setTipClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel user = AppUtils.getUser();
                if (user == null) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(PayActivity.class);
            }
        });
    }

    private void refreshHeadViewTip() {
        UserModel user = AppUtils.getUser();
        if (user == null || user.vipStatus == 0)
            headView.setTip("成为VIP掌握企业风险动态");
        else
            headView.setTip("");
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

        RxManager.http(RetrofitUtils.getApi().monitorList(map), new ListResponseCall(this) {

            @Override
            public List getList(NetModel model) {
                return model.dataToList("monitor", MonitorModel.class);
            }

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    List<MonitorModel> list = getList(model);
                    if (list != null && !list.isEmpty() && pageIndex == 1 && headView.getParent() == null) {
                        adapter.addHeaderView(headView);
                    }
                    if (pageIndex == 1 && (list == null || list.isEmpty()))
                        adapter.removeHeaderView(headView);

                    refreshHeadViewTip();
                } else {
                    adapter.removeHeaderView(headView);
                }
                super.success(model);
            }
        });
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof LoginChangeEvent || event instanceof MonitorChangeEvent || event instanceof MonitorStatusEvent)
            refresh();
    }
}
