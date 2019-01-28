package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.adapter.HomeIconAdapter;
import com.jusfoun.jusfouninquire.ui.widget.DividerGridItemDecoration;
import com.jusfoun.jusfouninquire.ui.widget.FullyGridLayoutManger;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2817:45
 * @Email zyp@jusfoun.com
 * @Description ${首页 icon}
 */
public class HomeIconView extends BaseView {
    protected RecyclerView recyclerview;
    private HomeIconAdapter homeIconAdapter;

    public HomeIconView(Context context) {
        super(context);
    }

    public HomeIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        homeIconAdapter = new HomeIconAdapter(mContext);
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_home_icon, this, true);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
    }

    @Override
    protected void initActions() {
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setLayoutManager(new FullyGridLayoutManger(mContext, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerview.setAdapter(homeIconAdapter);
    }

}
