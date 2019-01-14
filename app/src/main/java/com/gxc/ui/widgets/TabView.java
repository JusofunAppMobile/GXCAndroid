package com.gxc.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 18:43
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class TabView extends LinearLayout {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.vLine)
    View vLine;

    public TabView(Context context) {
        super(context);
        init(null);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setGravity(Gravity.CENTER);
        LayoutInflater.from(getContext()).inflate(R.layout.view_tab, this);
        ButterKnife.bind(this);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TabView);
            boolean selected = array.getBoolean(R.styleable.TabView_tab_selected, false);
            String text = array.getString(R.styleable.TabView_tab_label);
            setSelected(selected);
            textView.setText(text);

            array.recycle();
        }
    }

    public void setLabel(String label) {
        textView.setText(label);
    }
}
