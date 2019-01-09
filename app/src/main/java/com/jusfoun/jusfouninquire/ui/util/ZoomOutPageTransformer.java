package com.jusfoun.jusfouninquire.ui.util;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * @author zhaoyapeng
 * @version create time:15/11/24上午9:10
 * @Email zyp@jusfoun.com
 * @Description ${首页 viewpager 渐变 效果}
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(-position);

        } else if (position <= 1) { //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {
                view.setAlpha(1 + position);
            } else {
                view.setAlpha(1 - position);
            }

        } else {
            // This page is way off-screen to the right.
            view.setAlpha(1 - position);
        }
    }
}
