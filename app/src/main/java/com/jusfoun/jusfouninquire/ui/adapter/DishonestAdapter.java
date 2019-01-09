package com.jusfoun.jusfouninquire.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jusfoun.jusfouninquire.net.model.SearchDisHonestModel;
import com.jusfoun.jusfouninquire.ui.fragment.DishonestResultFragment;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/31.
 * Email wcc@jusfoun.com
 * Description
 */
public class DishonestAdapter extends FragmentPagerAdapter {
    private String searchkey;
    private SearchDisHonestModel model;
    public DishonestAdapter(FragmentManager fm, String searchkey, SearchDisHonestModel model) {
        super(fm);
        this.searchkey=searchkey;
        this.model=model;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle argument=new Bundle();
        argument.putString(DishonestResultFragment.SEARCHKEY,searchkey);
        argument.putInt(DishonestResultFragment.TYPE,position+1);
        argument.putSerializable(DishonestResultFragment.MODEL,model);
        return DishonestResultFragment.getInstance(argument);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
