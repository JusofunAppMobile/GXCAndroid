package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Albert on 2015/12/1.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:首次进行搜索引导
 */
public class GuideSearchSharedPreference {

    private static final String GUIDE_SEARCH = "guide_search";
    private Context mContext;
    private SharedPreferences mSharedPreferencef;

    private String mFirstSearchName;
    public GuideSearchSharedPreference(Context context) {
        mContext = context;
        mFirstSearchName = mContext.getPackageName() + "GuideSearch";
        mSharedPreferencef = mContext.getSharedPreferences(mFirstSearchName,Context.MODE_PRIVATE);
    }

    public boolean isFirstSearch(){
        boolean value = mSharedPreferencef.getBoolean(GUIDE_SEARCH, true);
        return value;
    }

    public void setIsFirstSearch(boolean value){
        SharedPreferences.Editor editor = mSharedPreferencef.edit();
        editor.putBoolean(GUIDE_SEARCH,value);
        editor.commit();
    }
}
