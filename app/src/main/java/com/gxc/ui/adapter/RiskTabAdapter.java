package com.gxc.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.RiskTabModel;
import com.siccredit.guoxin.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 19:21
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class RiskTabAdapter extends BaseQuickAdapter<RiskTabModel, BaseViewHolder> {

    public RiskTabAdapter() {
        super(R.layout.item_risk_tab);
    }

    @Override
    protected void convert(BaseViewHolder holder, RiskTabModel riskTabModel) {

    }
}
