package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明
 *
 * @日期 2016/8/29
 * @作者 LiuGuangDan
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragList = new ArrayList<>();

    public void addFragment(Context context, Class<? extends Fragment>... clazz) {
        if (clazz != null && clazz.length > 0) {
            for (Class<? extends Fragment> aClass : clazz) {
                fragList.add(Fragment.instantiate(context, aClass.getName()));
            }
        }
    }

    public void addFragment(Context context, Class<? extends Fragment> clazz, Bundle bundle) {
        if (clazz != null) {
            fragList.add(Fragment.instantiate(context, clazz.getName(), bundle));
        }
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 这里Destroy的是Fragment的视图层次，并不是Destroy Fragment对象
        super.destroyItem(container, position, object);
    }
}
