package com.gxc.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 12:36
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class NavTitleView extends LinearLayout {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tvLabel)
    TextView tvLabel;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.vLine)
    View vLine;

    public NavTitleView(Context context) {
        super(context);
        init(null);
    }

    public NavTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NavTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_nav_title, this);
        ButterKnife.bind(this);

        if (isInEditMode()) return;

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.NavTitleView);
            String text = array.getString(R.styleable.NavTitleView_text);
            String tip = array.getString(R.styleable.NavTitleView_tip);
            boolean showImage = array.getBoolean(R.styleable.NavTitleView_showImage, false);
            boolean showLine = array.getBoolean(R.styleable.NavTitleView_showLine, true);
            int id = array.getResourceId(R.styleable.NavTitleView_image_id, 0);

            textView.setText(text);
            image.setVisibility(showImage ? View.VISIBLE : View.GONE);
            vLine.setVisibility(showLine ? View.VISIBLE : View.GONE);
            tvLabel.setText(tip);
            if (id != 0)
                image.setImageResource(id);

            array.recycle();
        }
    }

    public void setLabel(String label) {
        textView.setText(label);
    }

    public void setTip(String tip) {
        tvLabel.setText(tip);
    }

    public void setTipClickListener(OnClickListener listener) {
        tvLabel.setOnClickListener(listener);
    }

    public void setImageVisibility(int visibility) {
        image.setVisibility(visibility);
    }

    public void setImageResource(int id) {
        image.setImageResource(id);
    }

    public void hideImageView() {
        image.setVisibility(View.GONE);
    }

    public void setImageClickListener(OnClickListener listener) {
        image.setOnClickListener(listener);
    }

}
