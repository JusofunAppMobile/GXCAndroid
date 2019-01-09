package com.jusfoun.jusfouninquire.sharedpreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class SearchHistoryPreference {
    private final static String SEARCH_HISTORY = "search_history";
    private final static int MAX_HISTORY_SIZE = 50;



    public static void saveItem(Context context,SearchHistoryItemModel model){
        if (TextUtils.isEmpty(model.getSearchkey())){
            return;
        }
        Map<String, SearchHistoryItemModel> map = getAll(context);
        if ((map.get(model.getSearchkey())) != null){
            model.setTimestamp(System.currentTimeMillis());
        }

        if (map.size() == MAX_HISTORY_SIZE){

            List<SearchHistoryItemModel> sortList = getSortList(context);
            if (sortList.get(MAX_HISTORY_SIZE - 1) != null){
                removeItem(context,sortList.get(MAX_HISTORY_SIZE - 1).getSearchkey());
            }

        }

        doSaveItem(context,model);
    }

    private static void doSaveItem(Context context ,SearchHistoryItemModel model){
        String value = new Gson().toJson(model);
        SharedPreferences lastTimePreferences = context.getSharedPreferences(context.getPackageName() + SEARCH_HISTORY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = lastTimePreferences.edit();
        editor.putString(model.getSearchkey(), value);
        editor.commit();
    }

    private static Map<String,SearchHistoryItemModel> getAll(Context context){
        SharedPreferences lastTimePreferences = context.getSharedPreferences(context.getPackageName() + SEARCH_HISTORY, Activity.MODE_PRIVATE);
        return (Map<String, SearchHistoryItemModel>) lastTimePreferences.getAll();
    }

    public  static void removeAll(Context context){
        Map<String, SearchHistoryItemModel> map = getAll(context);
        Iterator entries = map.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String)entry.getKey();
            removeItem(context,key);
        }
    }

    public static void removeItem(Context context,String key){
        if (TextUtils.isEmpty(key)){
            return;
        }
        SharedPreferences lastTimePreferences = context.getSharedPreferences(context.getPackageName() + SEARCH_HISTORY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = lastTimePreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static List<SearchHistoryItemModel> getSortList(Context context){
        List<SearchHistoryItemModel> sortlist = new ArrayList<>();
        Map<String,SearchHistoryItemModel> map = getAll(context);
        Type listType = new TypeToken<SearchHistoryItemModel>() {
        }.getType();
        Iterator entries = map.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String value = (String)entry.getValue();
            SearchHistoryItemModel itemModel = new Gson().fromJson(value, listType);
            sortlist.add(itemModel);
        }
        Collections.sort(sortlist, new Comparator<SearchHistoryItemModel>() {
            @Override
            public int compare(SearchHistoryItemModel first, SearchHistoryItemModel second) {
                if (first.getTimestamp() > second.getTimestamp()) {
                    return -1;
                } else if (first.getTimestamp() < second.getTimestamp()) {
                    return 1;
                }
                return 0;
            }
        });
        return sortlist;
    }
}
