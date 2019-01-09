package com.jusfoun.jusfouninquire.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jusfoun.jusfouninquire.ui.util.HomeFragmentUtil;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午3:07
 * @Email zyp@jusfoun.com
 * @Description ${首页fragment}
 */
public class HomeAdapter extends FragmentPagerAdapter {

    private int padding = 0;

    public HomeAdapter(FragmentManager fm, int padding) {
        super(fm);
        this.padding = padding;
    }

    @Override
    public Fragment getItem(int position) {
        return HomeFragmentUtil.getInstance(position,padding);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
