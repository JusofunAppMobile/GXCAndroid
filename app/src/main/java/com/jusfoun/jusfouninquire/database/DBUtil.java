package com.jusfoun.jusfouninquire.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.net.model.HomeDataModel;
import com.jusfoun.jusfouninquire.net.model.HomeInfoModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Albert on 2015/12/8.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:数据库工具类
 */
public class DBUtil {
    public static final String SEARCH_HISTORY_ITEM_INSERT = "insert into table_search_history(search_key,search_scope_id,search_scope_name,search_timestamp,search_type) values(?,?,?,?,?)";


    //企信宝2.0分类搜索，上限为10
    private static final int MAX_HISTORY_COUNT = 10;


    /**
     * 首页缓存相关
     */
    public static final String INSERT_HOME = "insert into " + DBCanstant.TABLE_NAME_HOME + "(" + DBCanstant.JSON_HOME_ID + "," + DBCanstant.JSON_HOME + ") values(?,?);";

    public static final String GET_HOME = "select *from " + DBCanstant.TABLE_NAME_HOME + " where " + DBCanstant.JSON_HOME_ID + "=?;";

    public static final String UPDATE_HOME = "update " + DBCanstant.TABLE_NAME_HOME + " set " + DBCanstant.JSON_HOME + "=? where " + DBCanstant.JSON_HOME_ID + "=?;";

    /**
     * 插入首页json数据
     */
    public static void insertHome(Context context, HomeInfoModel model) {

        String json = new Gson().toJson(model);
        try {
            if (!isExistHomeJosn(context)) {
                DBSqlDataUtil.getInstance(context).getDB().execSQL(INSERT_HOME, new String[]{"json_home_id_value", json});
            } else {
                DBSqlDataUtil.getInstance(context).getDB().execSQL(UPDATE_HOME, new String[]{json, "json_home_id_value"});
            }
        } catch (Exception e) {
        }


    }


    /**
     * 替换 我的关注，热门企业，征信
     */
    public static HomeInfoModel SpliceHomeModel(Context context, HomeInfoModel model) {
        HomeInfoModel localModel = getHomeData(context);
        if (localModel != null) {
            List<HomeDataModel> dataList = localModel.getDataList();
            if (dataList != null) {
                for (int i = 0; i < dataList.size(); i++) {
                    HomeDataModel homeDataModel = dataList.get(i);
                    Log.e("tag","type="+homeDataModel.getType());
                    if (model != null) {
                        if ("0".equals(homeDataModel.getType())) {
                            dataList.set(i, findTypeIndex(model, "0"));
                        } else if ("1".equals(homeDataModel.getType())) {
                            dataList.set(i, findTypeIndex(model, "1"));
                        } else if ("2".equals(homeDataModel.getType())) {
                            dataList.set(i, findTypeIndex(model, "2"));
                        }
                    }
                }
            }
            localModel.setDataList(dataList);
        } else {
            if(model!=null) {
                model.setDataList(removeSpecial(model));
                insertHome(context, model);
            }
            return model;
        }

        insertHome(context, localModel);
        return localModel;
    }

    public static HomeDataModel findTypeIndex(HomeInfoModel model, String type) {
        List<HomeDataModel> dataList = model.getDataList();
        if (dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                HomeDataModel homeDataModel = dataList.get(i);
                if (type.equals(homeDataModel.getType())) {
                    return homeDataModel;
                }
            }
        }
        return null;

    }

    public static List<HomeDataModel> removeSpecial(HomeInfoModel model) {
        List<HomeDataModel> list = new ArrayList<>();
        if (model != null && model.getDataList() != null) {
            for (int i = 0; i < model.getDataList().size(); i++) {
                HomeDataModel homeDataModel = model.getDataList().get(i);
                if (homeDataModel != null && ("0".equals(homeDataModel.getType()) || "1".equals(homeDataModel.getType()) || "2".equals(homeDataModel.getType()))) {
                    list.add(homeDataModel);
                }
            }
        }
        return list;
    }


    /**
     * 获取首页数据
     */
    public static HomeInfoModel getHomeData(Context context) {
        String json ;
        Cursor cursor;
        try {
            cursor = DBSqlDataUtil.getInstance(context).getDB().rawQuery(GET_HOME, new String[]{"json_home_id_value"});
            cursor.moveToFirst();
            Log.e("tag","cursor_count="+cursor.getCount());
            json = cursor.getString(cursor.getColumnIndex(DBCanstant.JSON_HOME));
            HomeInfoModel model  = new Gson().fromJson(json,HomeInfoModel.class);
            return model;
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean isExistHomeJosn(Context context) {
        Cursor cursor = DBSqlDataUtil.getInstance(context).getDB().rawQuery(GET_HOME, new String[]{"json_home_id_value"});

        try {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } catch (Exception e) {

        } finally {
            cursor.close();
//            return false;
        }
        return false;

    }

    /**
     * 插入搜索历史
     * @param context
     * @param model
     */
    public static void insertItem(Context context, SearchHistoryItemModel model) {
        String DELETE_ITEM = "delete from table_search_history where search_key=" + "'" + model.getSearchkey() + "'" + "  AND search_type=" + "'" + model.getType() + "'";
        List<SearchHistoryItemModel> list = getAll(context,model.getType());
        if (list.size() > 0) {
            if (list.size() == MAX_HISTORY_COUNT) {
                List<SearchHistoryItemModel> sortlist = new ArrayList<>();
                sortlist.addAll(getSortList(context,model.getType()));
                if (sortlist.get(MAX_HISTORY_COUNT - 1) != null) {
                    String DELETE_FIRST_ITEM = "delete from table_search_history where search_timestamp=" + "'" + sortlist.get(MAX_HISTORY_COUNT - 1).getTimestamp() + "'";
                    DBSqlDataUtil.getInstance(context).getDB().execSQL(DELETE_FIRST_ITEM);
                }

            }
            for (SearchHistoryItemModel itemModel : list) {
                if ((itemModel.getSearchkey().equals(model.getSearchkey())) && (itemModel.getType().equals(model.getType()))) {
                    try {
                        DBSqlDataUtil.getInstance(context).getDB().execSQL(DELETE_ITEM);
                    } catch (Exception e) {

                    }
                    break;
                }
            }
        }
        try {
            DBSqlDataUtil.getInstance(context).getDB().execSQL(SEARCH_HISTORY_ITEM_INSERT,
                    new String[]{model.getSearchkey(), model.getScope(), model.getScopename(), String.valueOf(model.getTimestamp()),model.getType()});
        } catch (Exception e) {
        }
    }

    public static void deleteAll(Context mContext,String type) {
        String delete_type_all = "delete from table_search_history where search_type=" + "'" + type + "'";
        try {
            if (IsTableExist(mContext, DBCanstant.TABLE_SEARCH_HISTORY_NAME)) {
                DBSqlDataUtil.getInstance(mContext).getDB().execSQL(delete_type_all);
            }
        } catch (Exception e) {

        }

    }

    public static boolean IsTableExist(Context mContext, String tableName) {
        boolean result = false;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim()
                    + "' ";
            cursor = DBSqlDataUtil.getInstance(mContext).getDB().rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 获取某个搜索类型的全部历史记录
     * @param mContext
     * @param type
     * @return
     */
    public static List<SearchHistoryItemModel> getAll(Context mContext,String type) {
        // 模糊查询
        String HISTORY_SELECT = "select * from table_search_history";
        List<SearchHistoryItemModel> historyList = new ArrayList<SearchHistoryItemModel>();
        Cursor cursor = DBSqlDataUtil.getInstance(mContext).getDB().rawQuery(HISTORY_SELECT, new String[]{});
        try {

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                if (type.equals(cursor.getString(cursor.getColumnIndex(DBCanstant.SEARCH_TYPE)))){
                    SearchHistoryItemModel model = new SearchHistoryItemModel();
                    model.setSearchkey(cursor.getString(cursor.getColumnIndex(DBCanstant.SEARCH_KEY)));
                    model.setScope(cursor.getString(cursor.getColumnIndex(DBCanstant.SEARCH_SCOPEID)));
                    model.setScopename(cursor.getString(cursor.getColumnIndex(DBCanstant.SEARCH_SCOPENAME)));
                    model.setType(cursor.getString(cursor.getColumnIndex(DBCanstant.SEARCH_TYPE)));
                    if (!TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(DBCanstant.SEARCH_TIMESTAMAP)))) {
                        model.setTimestamp(Long.parseLong(cursor.getString(cursor.getColumnIndex(DBCanstant.SEARCH_TIMESTAMAP))));
                    }

                    historyList.add(model);
                }

            }

        } catch (Exception e) {

        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return historyList;

    }

    /**
     * 对某个类型搜索历史进行排序
     * @param context
     * @param type
     * @return
     */
    public static List<SearchHistoryItemModel> getSortList(Context context,String type) {
        List<SearchHistoryItemModel> sortlist = new ArrayList<>();
        sortlist.addAll(getAll(context,type));
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
