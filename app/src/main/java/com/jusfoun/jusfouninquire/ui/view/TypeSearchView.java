package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.adapter.TypeSearchAdapter;
import com.jusfoun.jusfouninquire.ui.widget.SearchViewPager;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :企业、法人股东、失信 view
 */
public class TypeSearchView extends RelativeLayout {
    private Context mContext;

    private SearchViewPager mViewPager;
    private RelativeLayout[] mRelativeLayout;

    private TypeSearchAdapter mAdapter;


    private int mCurrentTabIndex;

    private OnTableClickListener mOnTableClickListener;

    public TypeSearchView(Context context) {
        super(context);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public TypeSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public TypeSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TypeSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    private void initData(){
        mRelativeLayout = new RelativeLayout[3];
        mAdapter = new TypeSearchAdapter(((FragmentActivity)mContext).getSupportFragmentManager());



    }


    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.type_search_view_layout, this, true);

        mRelativeLayout[0] = (RelativeLayout)findViewById(R.id.industry_layout);
        mRelativeLayout[1] = (RelativeLayout)findViewById(R.id.legal_shareholder_layout);
        mRelativeLayout[2] = (RelativeLayout)findViewById(R.id.dishonest_layout);




        mViewPager = (SearchViewPager)findViewById(R.id.type_search_viewpager);
        mViewPager.setOffscreenPageLimit(3);
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
                if (mOnTableClickListener != null) {
                    mOnTableClickListener.onClick(0);
                }
            }
        });

        mRelativeLayout[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout[mCurrentTabIndex].setSelected(false);
                mRelativeLayout[1].setSelected(true);
                mCurrentTabIndex = 1;

                mViewPager.setCurrentItem(1, false);
                if (mOnTableClickListener != null) {
                    mOnTableClickListener.onClick(1);
                }
            }
        });

        mRelativeLayout[2].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout[mCurrentTabIndex].setSelected(false);
                mRelativeLayout[2].setSelected(true);
                mCurrentTabIndex = 2;

                mViewPager.setCurrentItem(2, false);
                if (mOnTableClickListener != null) {
                    mOnTableClickListener.onClick(2);
                }
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
                if (mOnTableClickListener != null) {
                    mOnTableClickListener.onClick(mCurrentTabIndex);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    public void setNotTouchScroll(boolean value){
        mViewPager.setmNotTouchScoll(value);
    }

    public void setSelectedTab(int i){
        mRelativeLayout[mCurrentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mRelativeLayout[i].setSelected(true);
        mCurrentTabIndex = i;
        mViewPager.setCurrentItem(i, true);
    }

    public void setmOnTableClickListener(OnTableClickListener mOnTableClickListener) {
        this.mOnTableClickListener = mOnTableClickListener;
    }


    public interface OnTableClickListener{
        public void onClick(int index);
    }


}
