package com.gxc.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.HistoryModel;
import com.siccredit.guoxin.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 14:10
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MyHistoryAdapter extends BaseQuickAdapter<HistoryModel, BaseViewHolder> {

    public MyHistoryAdapter() {
        super(R.layout.item_my_history);
    }

    @Override
    protected void convert(BaseViewHolder holder, final HistoryModel model) {
        TextView tvTitle = holder.getView(R.id.tvName);
        tvTitle.setText(model.companyname);
    }

}
