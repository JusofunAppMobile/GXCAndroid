package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description : 首次搜索结果大于50
 */
public class FirstSearchPreference {
    private static final String IS_FIRST_SEARCH = "is_first_search";
    private Context mContext;
    private SharedPreferences mSharedPreferencef;

    private String mFirstSearchName;
    public FirstSearchPreference(Context context) {
        mContext = context;
        mFirstSearchName = mContext.getPackageName() + "FirstSearchPreference";
        mSharedPreferencef = mContext.getSharedPreferences(mFirstSearchName,Context.MODE_PRIVATE);
    }

    public boolean isFirstSearch(){
        boolean value = mSharedPreferencef.getBoolean(IS_FIRST_SEARCH, true);
        return value;
    }

    public void setIsFirstSearch(boolean value){
        SharedPreferences.Editor editor = mSharedPreferencef.edit();
        editor.putBoolean(IS_FIRST_SEARCH,value);
        editor.commit();
    }
}
