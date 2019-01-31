package com.jusfoun.jusfouninquire.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jusfoun.jusfouninquire.ui.activity.SearchResultActivity;
import com.jusfoun.jusfouninquire.ui.fragment.DishonestResultFragment;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/31.
 * Email wcc@jusfoun.com
 * Description
 */
public class DishonestAdapter extends FragmentPagerAdapter {
    private String searchkey;

    public DishonestAdapter(FragmentManager fm, String searchkey) {
        super(fm);
        this.searchkey = searchkey;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle argument = new Bundle();
        argument.putString(SearchResultActivity.SEARCH_KEY, searchkey);
        argument.putInt(DishonestResultFragment.TYPE, position + 1);
        return DishonestResultFragment.getInstance(argument);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
