package com.gxc.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.base.BaseActivity;
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

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 11:43
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MonitorAdpater extends BaseQuickAdapter<MonitorModel, BaseViewHolder> {

    private BaseActivity activity;

    public MonitorAdpater(BaseActivity activity) {
        super(R.layout.item_monitor);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, final MonitorModel model) {
        final TextView tvStatus = holder.getView(R.id.tvStatus);
        TextView tvTitle = holder.getView(R.id.tvTitle);
        TextView tvInfo = holder.getView(R.id.tvInfo);
        TextView tvTime = holder.getView(R.id.tvTime);
        final View vMonitor = holder.getView(R.id.vMonitor);
        if (model.isUserMonitor == 0) {
            vMonitor.setSelected(true);
            tvStatus.setText("监控");
        } else {
            vMonitor.setSelected(false);
            tvStatus.setText("取消监控");
        }
        tvTime.setText(model.changeDate);
        tvTitle.setText(model.companyName);
        vMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitorHandle(model, tvStatus, vMonitor);
            }
        });
        tvInfo.setText(AppUtils.getNumFont(mContext, model.changeCount));
    }

    private void monitorHandle(final MonitorModel model, final TextView tvStatus, final View parent) {
        UserModel user = AppUtils.getUser();
        if (user == null) {
            mContext.startActivity(new Intent(mContext, com.gxc.ui.activity.LoginActivity.class));
            return;
        }

//        if (user.vipStatus != 1) {
//            new VIPDialog((Activity) mContext).show();
//            return;
//        }

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
                    model.isUserMonitor = (model.isUserMonitor == 0 ? 1 : 0);
                    if (model.isUserMonitor == 0) {
                        parent.setSelected(true);
                        tvStatus.setText("监控");
                    } else {
                        parent.setSelected(false);
                        tvStatus.setText("取消监控");
                    }
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
