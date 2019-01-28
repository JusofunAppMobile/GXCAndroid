package com.gxc.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.MonitorMenuModel;
import com.siccredit.guoxin.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/11/011 18:35
 * @Description ${}
 */
public class MonitorMenuAdpater extends BaseQuickAdapter<MonitorMenuModel, BaseViewHolder> {

    public MonitorMenuAdpater() {
        super(R.layout.item_monitor_menu);
    }

    @Override
    protected void convert(BaseViewHolder helper, MonitorMenuModel item) {
        helper.setText(R.id.textView, item.monitor_condition_name);
        helper.getView(R.id.textView).setSelected(item.isSelect);
    }
}
