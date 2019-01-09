package com.jusfoun.jusfouninquire.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jusfoun.jusfouninquire.ui.fragment.SearchDishonestFragment;
import com.jusfoun.jusfouninquire.ui.fragment.SearchIndustryFragment;
import com.jusfoun.jusfouninquire.ui.fragment.SearchLegalShareHolderFragment;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class TypeSearchAdapter extends FragmentStatePagerAdapter {
    public final static int INDUSTRY = 0;
    public final static int LEGAL_SHAREHOLDER = 1;
    public final static int DISHONEST = 2;
    public TypeSearchAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case INDUSTRY:
                return new SearchIndustryFragment();

            case LEGAL_SHAREHOLDER:
                return new SearchLegalShareHolderFragment();
            case DISHONEST:
                return new SearchDishonestFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
