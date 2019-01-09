package com.gxc.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.RiskModel;
import com.jusfoun.jusfouninquire.R;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/808:51
 * @Email zyp@jusfoun.com
 * @Description ${企业详情 股东列表adapter}
 */
public class ShareholderAdapter extends BaseQuickAdapter<RiskModel, BaseViewHolder> {

    public ShareholderAdapter() {
        super(R.layout.item_shareholder);
    }

    @Override
    protected void convert(BaseViewHolder holder, RiskModel riskModel) {

    }
}
