package com.gxc.ui.widgets;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxc.model.PriceModel;
import com.siccredit.guoxin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 16:12
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class PriceView extends LinearLayout {

    @BindView(R.id.tvLabel)
    TextView tvLabel;
    @BindView(R.id.tvOriPrice)
    TextView tvOriPrice;
    @BindView(R.id.tvEvery)
    TextView tvEvery;
    @BindView(R.id.tvCurPrice)
    TextView tvCurPrice;

    public PriceView(Context context) {
        super(context);
        init();
    }

    public PriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_price, this);
        ButterKnife.bind(this);

        tvOriPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
    }

    public void setLabel(String label) {
        tvLabel.setText(label);
    }

    public void setData(PriceModel model, int type) {
        if (type == 1) {
            tvOriPrice.setText("¥" + model.oneOriPrice);
            tvCurPrice.setText("¥" + model.oneCurPrice);
            tvEvery.setText("低至 ¥" + model.oneDayPrice + "/天");
        } else if (type == 2) {
            tvOriPrice.setText("¥" + model.twoOriPrice);
            tvCurPrice.setText("¥" + model.twoCurPrice);
            tvEvery.setText("低至 ¥" + model.twoDayPrice + "/天");
        } else {
            tvOriPrice.setText("¥" + model.threeOriPrice);
            tvCurPrice.setText("¥" + model.threeCurPrice);
            tvEvery.setText("低至 ¥" + model.threeDayPrice + "/天");
        }
    }
}
