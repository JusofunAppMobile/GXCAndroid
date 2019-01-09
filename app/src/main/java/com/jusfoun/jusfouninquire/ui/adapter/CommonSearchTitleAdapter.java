package com.jusfoun.jusfouninquire.ui.adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jusfoun.jusfouninquire.ui.fragment.MyFocusesFragment;
import com.jusfoun.jusfouninquire.ui.fragment.SearchHistoryFragment;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class CommonSearchTitleAdapter extends FragmentStatePagerAdapter {
    public final static int SEARCH_HISTORY = 0;
    public final static int MY_FOCUSES = 1;

    public CommonSearchTitleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case SEARCH_HISTORY:
                return new SearchHistoryFragment();
            case MY_FOCUSES:
                return new MyFocusesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
