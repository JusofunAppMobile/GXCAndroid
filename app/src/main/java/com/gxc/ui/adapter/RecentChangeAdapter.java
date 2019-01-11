package com.gxc.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.RecentChangeItemModel;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1117:33
 * @Email zyp@jusfoun.com
 * @Description ${近期变更}
 */
public class RecentChangeAdapter extends BaseQuickAdapter<RecentChangeItemModel.DataResultBean,BaseViewHolder> {

    public RecentChangeAdapter() {
        super(R.layout.item_recent_change);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecentChangeItemModel.DataResultBean item) {

    }
}
