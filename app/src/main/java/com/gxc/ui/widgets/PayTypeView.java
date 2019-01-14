package com.gxc.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 16:51
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class PayTypeView extends LinearLayout {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tvLabel)
    TextView tvLabel;

    public PayTypeView(Context context) {
        super(context);
        init(null);
    }

    public PayTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PayTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_pay_type, this);
        ButterKnife.bind(this);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PayTypeView);
            int id = array.getResourceId(R.styleable.PayTypeView_image, 0);
            String text = array.getString(R.styleable.PayTypeView_label);

            image.setImageResource(id);
            tvLabel.setText(text);

            array.recycle();
        }
    }


}
