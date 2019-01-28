package com.jusfoun.jusfouninquire.ui.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.service.event.LogoutEvent;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import netlib.util.EventUtils;

/**
 * @author zhaoyapeng
 * @version create time:15/11/23下午1:35
 * @Email zyp@jusfoun.com
 * @Description ${首页 顶部组件}
 */
public class HomeTopView extends LinearLayout {

    private ViewPager viewPager;
    private RelativeLayout rootLayout;
    private Context mContext;
    private List<BaseTopPublic> imageArray;
    private int width = 0;
    private RelativeLayout paddingLayout;
    private Handler handler;

    public HomeTopView(Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public HomeTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public HomeTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initViews();
        initActions();
    }


    private void initData() {
        width = PhoneUtil.getDisplayWidth(mContext);
        handler = new Handler();
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.homt_title_top, this, true);
        rootLayout = (RelativeLayout) findViewById(R.id.layout_root);
        paddingLayout = (RelativeLayout) findViewById(R.id.layout_padding);
    }


    private void initActions() {
        initImageViews();
    }

    private void initImageViews() {
        imageArray = new ArrayList<>();
        OneImageView imageViews1 = new OneImageView(mContext);
//        imageViews1.setImgRes(R.mipmap.img_set_noremal, R.mipmap.img_set_select);
        imageViews1.setImgRes(R.drawable.img_set_select, R.drawable.img_set_select);
        imageViews1.setBig(0, 0);
        imageViews1.setX(width / 2 - imageViews1.getImageWidth() / 2 - PhoneUtil.dip2px(mContext, 10));

        imageArray.add(imageViews1);


        HomeTopTitleView myTitleView = new HomeTopTitleView(mContext);
        myTitleView.setX(width - (myTitleView.getImageTitleWidth() - myTitleView.getImageWidth()) / 2 - myTitleView.getImageWidth() - PhoneUtil.dip2px(mContext, 20));
        myTitleView.setBig(0, 0);
        imageArray.add(myTitleView);


        OneImageView imageViews3 = new OneImageView(mContext);
//        imageViews3.setImgRes(R.mipmap.img_persinal_normal, R.mipmap.img_persinal_niremal);
        imageViews3.setImgRes(R.drawable.img_persinal_niremal, R.drawable.img_persinal_niremal);
//        imageViews3.setImgRes(R.mipmap.comiing_out_personal, R.mipmap.comiing_out_personal);
        imageViews3.setSelectAlpah(0f);
        imageViews3.setX(3 * (width / 2 - imageViews1.getImageWidth() / 2) - PhoneUtil.dip2px(mContext, 30));

        imageArray.add(imageViews3);


        OneImageView imageViews4 = new OneImageView(mContext);
//        imageViews3.setImgRes(R.mipmap.img_persinal_normal, R.mipmap.img_persinal_niremal);
        imageViews4.setImgRes(R.drawable.comiing_out_personal, R.drawable.comiing_out_personal);
        imageViews4.setSelectAlpah(0f);
        imageViews4.setX(4 * (width / 2 - imageViews1.getImageWidth() / 2) - PhoneUtil.dip2px(mContext, 40));
        imageArray.add(imageViews4);


        for (int i = 0; i < 4; i++) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            imageArray.get(i).setLayoutParams(params);
            rootLayout.addView(imageArray.get(i));

        }
    }

     boolean isInit = true;
    public void setViewPager(final ViewPager viewPager) {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position,final  float positionOffset,final int positionOffsetPixels) {

                Log.e("tag","setViewPagersetViewPager1=="+positionOffset);

               /* Log.e("tag", "positionOffset=" + positionOffset);
                Log.e("tag", "positionOffsetPixels=" + positionOffsetPixels + " postion=" + position);*/
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PhoneUtil.dip2px(mContext, 48));
                params.setMargins(-(int) ((width / 2 - (imageArray.get(position).getImageWidth() + PhoneUtil.dip2px(mContext, 20)) / 2) * position + (width / 2 - (imageArray.get(position).getImageWidth() + PhoneUtil.dip2px(mContext, 20)) / 2) / (width * 1.0) * positionOffsetPixels), 0, 0, 0);
                rootLayout.setLayoutParams(params);
                rootLayout.invalidate();

                imageArray.get(position).setSelectAlpah(1 - positionOffset);
                imageArray.get(position).setNormalAlpah(positionOffset);
                imageArray.get(position).setBig((1 - positionOffset), position);

                if ((position + 1) < imageArray.size()) {
                    imageArray.get(position + 1).setSelectAlpah(positionOffset);
                    imageArray.get(position + 1).setNormalAlpah(1 - positionOffset);
                    imageArray.get(position + 1).setBig(positionOffset, position + 1);

                }

//                Log.e("tag", "onPageScrolled");

//                 后加需求
                ((HomeTopTitleView) imageArray.get(1)).setSearchState(VISIBLE);

                if(isInit) {
                    isInit = false;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PhoneUtil.dip2px(mContext, 48));
                            params.setMargins(-(int) ((width / 2 - (imageArray.get(position).getImageWidth() + PhoneUtil.dip2px(mContext, 20)) / 2) * position + (width / 2 - (imageArray.get(position).getImageWidth() + PhoneUtil.dip2px(mContext, 20)) / 2) / (width * 1.0) * positionOffsetPixels), 0, 0, 0);
                            rootLayout.setLayoutParams(params);
                            rootLayout.invalidate();
                        }
                    }, 500);
                }

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("tag","setViewPagersetViewPager2");
                EventUtils.event(mContext,EventUtils.HOMEPAGE14);
                // 日志中，会出现报错的情况
                if (mContext instanceof Activity) {
                    try {
                        KeyBoardUtil.hideSoftKeyboard((Activity) mContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (position == 1) {
                    imageArray.get(0).setNormalAlpah(1f);
                    imageArray.get(0).setSelectAlpah(0f);

                    ((HomeTopTitleView) imageArray.get(1)).setSearchState(GONE);
                } else {
                    ((HomeTopTitleView) imageArray.get(1)).setSearchState(VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("tag","setViewPagersetViewPager3");
                if (viewPager.getCurrentItem() == 1) {
//                    if(!TextUtils.isEmpty(android.os.Build.BRAND)&&!android.os.Build.BRAND.contains("vivo")) {
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                imageArray.get(0).setNormalAlpah(1f);
//                                imageArray.get(0).setSelectAlpah(0f);
//
//                                imageArray.get(2).setNormalAlpah(1f);
//                                imageArray.get(2).setSelectAlpah(0f);
//                            }
//                        }, 500);
//                    }
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    }, 500);

                    imageArray.get(0).setNormalAlpah(1f);
                    imageArray.get(0).setSelectAlpah(0f);

                    imageArray.get(2).setNormalAlpah(1f);
                    imageArray.get(2).setSelectAlpah(0f);
                } else {
                    ((HomeTopTitleView) imageArray.get(1)).setSearchState(VISIBLE);
                    imageArray.get(1).setNormalAlpah(1f);
                    imageArray.get(1).setSelectAlpah(0f);
                    imageArray.get(1).setBig(0, 0);
                }



//                 后加需求
//                ((HomeTopTitleView)imageArray.get(1)).setSearchState(VISIBLE);
            }
        });

        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(),
                    new LinearInterpolator());
            field.set(viewPager, scroller);
            scroller.setmDuration(200);
        } catch (Exception e) {
//            LogUtils.e(TAG, "", e);
        }

        for (int i = 0; i < 4; i++) {
            final int j = i;

            if (j == 1) {
                ((HomeTopTitleView) imageArray.get(j)).setOnClick(viewPager);
            } else {
                imageArray.get(i).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (j == 3) {
                            EventUtils.event(mContext,EventUtils.ME96);
                            EventBus.getDefault().post(new LogoutEvent());
                        } else {
                            viewPager.setCurrentItem(j);

                            if (j == 0) {
                                EventUtils.event(mContext,EventUtils.HOME18);
                            } else if (j == 2) {
                                EventUtils.event(mContext,EventUtils.HOME19);
                            }
                        }
                    }
                });

            }
        }




    }

    public void setPadding(int padding) {
        paddingLayout.setPadding(0, padding, 0, 0);
    }

    public void setLoginOutIconState(boolean islogin){
        if(imageArray!=null&&imageArray.get(3)!=null){
            if(islogin){
                imageArray.get(3).setVisibility(View.VISIBLE);
            }else{
                imageArray.get(3).setVisibility(View.GONE);
            }
        }
    }
}
