package com.jusfoun.jusfouninquire.service.event;

/**
 * Created by Albert on 2015/11/11.
 * Mail : lbh@jusfoun.com
 * TODO :搜索完成发送event，SearchActivity接收event，根据携带的resultcount信息设置 search scope tips
 *
 */
public class SearchFinishEvent implements IEvent {
    private String searchkey;
    private int resultcount;

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    public int getResultcount() {
        return resultcount;
    }

    public void setResultcount(int resultcount) {
        this.resultcount = resultcount;
    }
}
