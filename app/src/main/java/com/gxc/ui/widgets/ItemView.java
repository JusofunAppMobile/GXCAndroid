package com.gxc.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 15:00
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class ItemView extends LinearLayout {
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.tvValue)
    TextView tvValue;
    @BindView(R.id.vLine)
    View vLine;

    public ItemView(Context context) {
        super(context);
        init(null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_item, this);
        ButterKnife.bind(this);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ItemView);
            String text = array.getString(R.styleable.ItemView_item_text);
            String value = array.getString(R.styleable.ItemView_item_value);
            boolean isShowLine = array.getBoolean(R.styleable.ItemView_item_show_line, true);
            int id = array.getResourceId(R.styleable.ItemView_item_image, 0);

            textView.setText(text);
            tvValue.setText(value);
            if (id != 0)
                imageView.setImageResource(id);
            else
                imageView.setVisibility(View.GONE);
            vLine.setVisibility(isShowLine ? View.VISIBLE : View.GONE);

            array.recycle();
        }
    }

    public void setValue(String value) {
        tvValue.setText(value);
    }
}
