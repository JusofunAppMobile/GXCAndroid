package com.gxc.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.RiskModel;
import com.jusfoun.jusfouninquire.R;

import java.util.List;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 17:36
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class RiskAdapter extends BaseQuickAdapter<RiskModel, BaseViewHolder> {

    public RiskAdapter() {
        super(R.layout.item_risk);
    }

    @Override
    protected void convert(BaseViewHolder holder, RiskModel riskModel) {

    }
}
