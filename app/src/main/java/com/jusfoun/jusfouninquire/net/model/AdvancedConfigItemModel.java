package com.jusfoun.jusfouninquire.net.model;

/**
 * Created by Albert on 2015/11/16.
 * Mail : lbh@jusfoun.com
 * TODO :高级搜索条件item model
 */
public class AdvancedConfigItemModel extends BaseModel{
    private String SearchName;
    private boolean IsShow;

    public String getSearchName() {
        return SearchName;
    }

    public void setSearchName(String searchName) {
        SearchName = searchName;
    }

    public boolean isShow() {
        return IsShow;
    }

    public void setIsShow(boolean isShow) {
        IsShow = isShow;
    }
}
