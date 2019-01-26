package com.jusfoun.jusfouninquire.net.model;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class SearchHistoryItemModel {

    // 1：股东高管 2：主营产品 3：失信查询 4：查税号 5：招聘 6：企业通讯录 7：查关系  8：风险分析   11 ：地址电话 15 ：中标信息  16：裁判文书 17：行政处罚  18：商标查询

    public static final String SEARCH_COMMON = "0";//模糊搜索
    public static final String SEARCH_PRODUCT = "1";//企业产品
    public static final String SEARCH_SHAREHOLDER = "2";//股东高管
    public static final String SEARCH_ADDRESS = "3";//地址电话
    public static final String SEARCH_INTERNET = "4";//企业网址
    public static final String SEARCH_DISHONEST = "5";//失信查询

    public static final String SEARCH_TAXID = "6";//查税号
    public static final String SEARCH_RECRUITMENT = "7";//招聘
    public static final String SEARCH_CONTACT = "8";//企业通讯录
    public static final String SEARCH_SHAREHOLDER_RIFT = "9";//股东穿透

    public static final String SEARCH_RISK = "10";//风险分析
    public static final String SEARCH_RELATION = "11";//查关系
    public static final String SEARCH_WINNING_BID = "15";//中标
    public static final String SEARCH_REFEREE = "16";//裁判文书
    public static final String SEARCH_ADMINISTRATIVE = "17";//行政处罚
    public static final String SEARCH_TRADEMARK = "18";//商标查询

    private String searchkey,scope,scopename;
    private String type;
    private long timestamp;


    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScopename() {
        return scopename;
    }

    public void setScopename(String scopename) {
        this.scopename = scopename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
