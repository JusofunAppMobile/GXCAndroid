package com.gxc.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.base.BaseListActivity;
import com.gxc.model.CollectModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.activity.MyCollectListActivity;
import com.gxc.utils.ToastUtils;
import com.siccredit.guoxin.R;

import java.util.HashMap;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 14:10
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MyCollectAdapter extends BaseQuickAdapter<CollectModel, BaseViewHolder> {
    private BaseListActivity activity;

    public MyCollectAdapter(MyCollectListActivity activity) {
        super(R.layout.item_my_collect);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, final CollectModel model) {
        TextView tvName = holder.getView(R.id.tvName);
        final View vMonitor = holder.getView(R.id.vMonitor);
        vMonitor.setSelected(false);

        tvName.setText(model.companyname);
        vMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitorHandle(model);
            }
        });
    }

    private void monitorHandle(final CollectModel model) {
        activity.showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyid", model.companyid);
        map.put("companyname", model.companyname);
        map.put("monitorType", 0);

        RxManager.http(RetrofitUtils.getApi().addOrCancelCollection(map), new ResponseCall() {

            @Override
            public void success(NetModel net) {
                activity.hideLoadDialog();
                if (net.success()) {
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
