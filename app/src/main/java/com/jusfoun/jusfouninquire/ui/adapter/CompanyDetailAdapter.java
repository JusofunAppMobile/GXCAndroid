package com.jusfoun.jusfouninquire.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jusfoun.jusfouninquire.net.model.CompanyDetailMenuModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant;
import com.jusfoun.jusfouninquire.ui.util.CompanyDetailUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/9.
 * Description
 */
public class CompanyDetailAdapter extends FragmentPagerAdapter implements CompanyDetailTypeConstant{
    private Bundle argument;
    private List<CompanyDetailMenuModel> list;

    public CompanyDetailAdapter(FragmentManager fm, Bundle argument) {
        super(fm);
        this.argument = argument;
        list = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return CompanyDetailUtil.getInstance(position, argument,list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void refresh(List<CompanyDetailMenuModel> list){
        this.list.clear();
        for(CompanyDetailMenuModel model : list ){
            if(types.contains(model.getType())){
                this.list.add(model);
            }
        }
        notifyDataSetChanged();
    }

}
