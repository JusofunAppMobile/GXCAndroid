package com.jusfoun.jusfouninquire.service.event;

/**
 * Created by Albert on 2015/11/11.
 * Mail : lbh@jusfoun.com
 * TODO :发送搜索event，接收者根据携带的参数进行对应的网络请求
 */
public class SearchEvent implements IEvent {
    private String scope,searchkey,scopename;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }
}
