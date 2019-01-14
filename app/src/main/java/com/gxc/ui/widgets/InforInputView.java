package com.gxc.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 18:19
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class InforInputView extends LinearLayout {
    @BindView(R.id.tvLabel)
    TextView tvLabel;
    @BindView(R.id.tvValue)
    TextView tvValue;
    @BindView(R.id.image)
    ImageView image;

    public InforInputView(Context context) {
        super(context);
        init(null);
    }

    public InforInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public InforInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_infor_input, this);
        ButterKnife.bind(this);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.InforView);
            String label = array.getString(R.styleable.InforView_infor_label);
            String value = array.getString(R.styleable.InforView_infor_value);
            int id = array.getResourceId(R.styleable.InforView_infor_image, 0);

            tvLabel.setText(label);
            tvValue.setText(value);
            if (id != 0)
                image.setImageResource(id);
            else
                image.setVisibility(View.GONE);

            array.recycle();
        }
    }

    public void loadImage(String url) {
        Glide.with(InquireApplication.application).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(image);
    }

    public void setValue(String value) {
        tvValue.setText(value);
    }

    public String getValue() {
        return tvValue.getText().toString();
    }

}
