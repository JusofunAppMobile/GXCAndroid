package com.jusfoun.jusfouninquire.net.model;

/**
 * Created by Albert on 2016/1/12.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:设置、个人中心底部view model
 */
public class BottomMenuModel extends BaseModel {
    private String title,url,umeng_analytics;
    private int tag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getUmeng_analytics() {
        return umeng_analytics;
    }

    public void setUmeng_analytics(String umeng_analytics) {
        this.umeng_analytics = umeng_analytics;
    }
}
