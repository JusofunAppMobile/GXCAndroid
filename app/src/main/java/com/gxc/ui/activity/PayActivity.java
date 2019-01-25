package com.gxc.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gxc.base.BaseActivity;
import com.gxc.event.PaySucEvent;
import com.gxc.inter.OnCallListener;
import com.gxc.model.GlideApp;
import com.gxc.model.PriceModel;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.widgets.PayTypeView;
import com.gxc.ui.widgets.PriceView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.PayUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import org.json.JSONException;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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
    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvLast)
    TextView tvLast;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    @BindView(R.id.vSubmit)
    TextView vSubmit;

    private PriceModel priceModel;


    @Override
    protected int getLayoutId() {
        return R.layout.act_pay;
    }

    @Override
    public void initActions() {
        titleView.setTitle("成为VIP");
//        titleView.setRightText("VIP介绍");

        priceView1.setLabel("1年VIP会员");
        priceView2.setLabel("2年VIP会员");
        priceView3.setLabel("3年VIP会员");


        UserModel user = AppUtils.getUser();
        if (user != null) {
            RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop())
                    .placeholder(R.drawable.me_head_default_loggedin)
                    .error(R.drawable.me_head_default_loggedin);
            GlideApp.with(InquireApplication.application).load(user.headIcon).apply(options).into(ivIcon);

            tvPhone.setText(user.phone);
            if (user.vipStatus == 1)
                vSubmit.setText("续费");

        }

        vAlipay.setSelected(true);
        load();
    }

    private int getLevelType() {
        if (priceView1.isSelected())
            return 1;
        if (priceView2.isSelected())
            return 2;
        if (priceView3.isSelected())
            return 3;
        return 0;
    }

    private int getPayType() {
        if (vAlipay.isSelected())
            return 2;
        if (vWechat.isSelected())
            return 1;
        return 0;
    }

    @OnClick(R.id.vSubmit)
    public void vSubmit() {
        if (priceModel == null)
            return;

        if (getPayType() == 0) {
            showToast("请选择支付方式");
            return;
        }

        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("levelType", getLevelType());
        map.put("payType", getPayType());

        RxManager.http(RetrofitUtils.getApi().orderPay(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    try {
                        String order = model.getDataJSONObject().getString("order");
                        new PayUtils(activity, new PayUtils.CallBack() {
                            @Override
                            public void paySuccess() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppUtils.checkUserStatus(new OnCallListener() {
                                            @Override
                                            public void call() {
                                                hideLoadDialog();
                                                EventBus.getDefault().post(new PaySucEvent());
                                                finish();
                                            }
                                        });
                                    }
                                }, 1000);
                            }

                            @Override
                            public void payFail() {
                                hideLoadDialog();
                            }
                        }).alipay(order);
                    } catch (JSONException e) {
                        hideLoadDialog();
                        e.printStackTrace();
                    }
                } else {
                    hideLoadDialog();
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });
    }

    private void load() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", 1);
        RxManager.http(RetrofitUtils.getApi().getOrderMsg(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    priceModel = model.dataToObject(PriceModel.class);
                    if (priceModel != null) {
                        priceView1.setSelected(true);
                        priceView1.setData(priceModel, 1);
                        priceView2.setData(priceModel, 2);
                        priceView3.setData(priceModel, 3);
                        tvMoney.setText("¥" + priceModel.oneCurPrice);
                        UserModel user = AppUtils.getUser();
                        if (user != null && user.vipStatus == 1)
                            tvLast.setText("VIP剩余天数：" + priceModel.vipLastDay + "天");
                    }
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });
    }

    @OnClick({R.id.priceView1, R.id.priceView2, R.id.priceView3})
    public void onViewClicked(View view) {
        if (priceModel == null) return;
        priceView1.setSelected(priceView1 == view);
        priceView2.setSelected(priceView2 == view);
        priceView3.setSelected(priceView3 == view);

        String money = "";
        if (priceView1.isSelected())
            money = "¥" + priceModel.oneCurPrice;
        else if (priceView2.isSelected())
            money = "¥" + priceModel.twoCurPrice;
        else if (priceView3.isSelected())
            money = "¥" + priceModel.threeCurPrice;
        tvMoney.setText(money);
    }

    @OnClick({R.id.vAlipay, R.id.vWechat})
    public void onViewClicked2(View view) {
        if (priceModel == null) return;
        vAlipay.setSelected(vAlipay == view);
        vWechat.setSelected(vWechat == view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
