package com.jusfoun.jusfouninquire.database;
/**
 * @author zhaoyapeng
 * @version create time:2015-7-21下午7:21:15
 * @Email zyp@jusfoun.com
 * @Description 有关数据库常量类
 */
public class DBCanstant {
    public static final String DB_NAME = "db_search_history";
    public static final int VERSION = 9;
    public static final String TABLE_SEARCH_HISTORY_NAME = "table_search_history";
    public static final String SEARCH_KEY = "search_key";
    public static final String SEARCH_SCOPEID ="search_scope_id";
    public static final String SEARCH_SCOPENAME = "search_scope_name";
    public static final String SEARCH_TIMESTAMAP = "search_timestamp";

    //企信宝2.0搜索历史需要进行分类
    public static final String SEARCH_TYPE = "search_type";




    /**
     * 首页缓存相关
     * */
    public static final String TABLE_NAME_HOME ="table_name_home";
    public static final String JSON_HOME = "json_home";
    public static final String JSON_HOME_ID = "json_home_id";
    public static final String JSON_HOME_ID_VALUE = "json_home_id_value";
}
