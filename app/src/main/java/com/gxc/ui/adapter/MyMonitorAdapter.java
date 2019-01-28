package com.gxc.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.base.BaseListActivity;
import com.gxc.event.MonitorStatusEvent;
import com.gxc.model.MonitorModel;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.AppUtils;
import com.gxc.utils.ToastUtils;
import com.siccredit.guoxin.R;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 14:10
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MyMonitorAdapter extends BaseQuickAdapter<MonitorModel, BaseViewHolder> {
    private BaseListActivity activity;

    public MyMonitorAdapter(BaseListActivity activity) {
        super(R.layout.item_monitor_company);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, final MonitorModel model) {
        final TextView tvStatus = holder.getView(R.id.tvStatus);
        TextView tvName = holder.getView(R.id.tvName);
        final View vMonitor = holder.getView(R.id.vMonitor);
        if (model.isUserMonitor == 0) {
            vMonitor.setSelected(true);
            tvStatus.setText("监控");
        } else {
            vMonitor.setSelected(false);
            tvStatus.setText("取消监控");
        }

        tvName.setText(model.companyName);
        vMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitorHandle(model, tvStatus, vMonitor);
            }
        });
    }

    private void monitorHandle(final MonitorModel model, final TextView tvStatus, final View parent) {
        UserModel user = AppUtils.getUser();
        if (user == null) {
            mContext.startActivity(new Intent(mContext, com.gxc.ui.activity.LoginActivity.class));
            return;
        }

        activity.showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyid", model.companyId);
        map.put("companyname", model.companyName);
        map.put("monitorType", model.isUserMonitor == 0 ? 1 : 0); // TEST

        RxManager.http(RetrofitUtils.getApi().monitorUpdate(map), new ResponseCall() {

            @Override
            public void success(NetModel net) {
                activity.hideLoadDialog();
                if (net.success()) {
                    EventBus.getDefault().post(new MonitorStatusEvent());
                    activity.refresh();
                } else {
                    ToastUtils.show(net.msg);
                }
            }

            @Override
            public void error() {
                activity.hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });
    }
}
