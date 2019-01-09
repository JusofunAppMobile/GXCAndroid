package com.gxc.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.gxc.base.BaseActivity;
import com.gxc.model.PriceModel;
import com.gxc.ui.widgets.PayTypeView;
import com.gxc.ui.widgets.PriceView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 15:53
 * @Email lgd@jusfoun.com
 * @Description ${成为VIP}
 */
public class PayActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.priceView1)
    PriceView priceView1;
    @BindView(R.id.priceView2)
    PriceView priceView2;
    @BindView(R.id.priceView3)
    PriceView priceView3;
    @BindView(R.id.vAlipay)
    PayTypeView vAlipay;
    @BindView(R.id.vWechat)
    PayTypeView vWechat;


    @Override
    protected int getLayoutId() {
        return R.layout.act_pay;
    }

    @Override
    public void initActions() {
        titleView.setTitle("成为VIP");
        titleView.setRightText("VIP介绍");

        priceView1.setSelected(true);
        priceView1.setLabel("1年VIP会员");
        priceView2.setLabel("2年VIP会员");
        priceView3.setLabel("3年VIP会员");

        PriceModel model = new PriceModel();
        priceView1.setData(model, 1);
        priceView2.setData(model, 2);
        priceView3.setData(model, 3);
    }

    @OnClick({R.id.priceView1, R.id.priceView2, R.id.priceView3})
    public void onViewClicked(View view) {
        priceView1.setSelected(priceView1 == view);
        priceView2.setSelected(priceView2 == view);
        priceView3.setSelected(priceView3 == view);
    }

    @OnClick({R.id.vAlipay, R.id.vWechat})
    public void onViewClicked2(View view) {
        vAlipay.setSelected(vAlipay == view);
        vWechat.setSelected(vWechat == view);
    }
}
