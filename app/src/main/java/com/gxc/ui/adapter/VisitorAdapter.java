package com.gxc.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.VisitorModel;
import com.siccredit.guoxin.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/10/010 9:14
 * @Description ${}
 */
public class VisitorAdapter extends BaseQuickAdapter<VisitorModel,BaseViewHolder> {

    public VisitorAdapter() {
        super(R.layout.item_visitor);
    }

    @Override
    protected void convert(BaseViewHolder helper, VisitorModel item) {
        helper.setText(R.id.tvName, item.name);
        helper.setText(R.id.tvTime, item.date);
    }
}
