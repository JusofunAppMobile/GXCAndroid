package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.adapter.CommonSearchTitleAdapter;
import com.jusfoun.jusfouninquire.ui.widget.SearchViewPager;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :搜索记录和我的关注view
 */
public class CommonSearchView extends LinearLayout {
    private Context mContext;
    private SearchViewPager mViewPager;
    private CommonSearchTitleAdapter mAdapter;

    private RelativeLayout[] mRelativeLayout;
    private int mCurrentTabIndex;

    public CommonSearchView(Context context) {
        super(context);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public CommonSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public CommonSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommonSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    private void initData(){
        mAdapter = new CommonSearchTitleAdapter(((FragmentActivity)mContext).getSupportFragmentManager());
        mRelativeLayout = new RelativeLayout[2];
    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.common_search_view_layout, this, true);
        mRelativeLayout[0] = (RelativeLayout)findViewById(R.id.search_history_layout);
        mRelativeLayout[1] = (RelativeLayout)findViewById(R.id.my_focuses_layout);
        mViewPager = (SearchViewPager)findViewById(R.id.common_search_viewpager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setmNotTouchScoll(false);
        mViewPager.setAdapter(mAdapter);

        mCurrentTabIndex = 0;
        mViewPager.setCurrentItem(mCurrentTabIndex, false);
        mRelativeLayout[mCurrentTabIndex].setSelected(true);
    }

    private void initWidgetAction(){
        mRelativeLayout[0].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout[mCurrentTabIndex].setSelected(false);
                mRelativeLayout[0].setSelected(true);
                mCurrentTabIndex = 0;
                mViewPager.setCurrentItem(0, false);
            }
        });

        mRelativeLayout[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout[mCurrentTabIndex].setSelected(false);
                mRelativeLayout[1].setSelected(true);
                mCurrentTabIndex = 1;
                mViewPager.setCurrentItem(1, false);
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mRelativeLayout[mCurrentTabIndex].setSelected(false);

                // 把当前tab设为选中状态
                mRelativeLayout[i].setSelected(true);
                mCurrentTabIndex = i;
                mViewPager.setCurrentItem(i, true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }




}
