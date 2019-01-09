package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Author  wangchenchen
 * CreateDate 2016/1/12.
 * Email wcc@jusfoun.com
 * Description
 */
public class HomeDataModel extends BaseModel{

    public static final String TYPE_FOL="0";//我的关注
    public static final String TYPE_HOT="1";//热门企业
    public static final String TYPE_DIS="2";//失信列表
    public static final String TYPE_SPE="3";//专题
    private String url;
    private String type;
    private String name;
    private String umeng_analytics;
    private List<HomeDataItemModel> list;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HomeDataItemModel> getList() {
        return list;
    }

    public void setList(List<HomeDataItemModel> list) {
        this.list = list;
    }

    public String getUmeng_analytics() {
        return umeng_analytics;
    }

    public void setUmeng_analytics(String umeng_analytics) {
        this.umeng_analytics = umeng_analytics;
    }
}
