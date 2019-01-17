package com.gxc.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1714:16
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class BottomBarView extends BaseView {
    public BottomBarView(Context context) {
        super(context);
    }

    public BottomBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

        LayoutInflater.from(mContext).inflate(R.layout.view_bottom_bar, this, true);
    }

    @Override
    protected void initActions() {
        findViewById(R.id.layout_1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tabSelectedListener != null) {
                    tabSelectedListener.onTabSelected(0);
                }
            }
        });

        findViewById(R.id.layout_2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabSelectedListener != null) {
                    tabSelectedListener.onTabSelected(1);
                }
            }
        });

        findViewById(R.id.layout_3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabSelectedListener != null) {
                    tabSelectedListener.onTabSelected(2);
                }
            }
        });

        findViewById(R.id.layout_4).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabSelectedListener != null) {
                    tabSelectedListener.onTabSelected(3);
                }
            }
        });
    }

    public void setTabSelectedListener(TabSelectedListener tabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener;
    }

    public interface TabSelectedListener {
         void onTabSelected(int position);
    }

    private TabSelectedListener tabSelectedListener;

}