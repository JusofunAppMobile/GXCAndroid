package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by Albert on 2015/11/16.
 * Mail : lbh@jusfoun.com
 * TODO :高级搜索结果model
 */
public class AdvancedSearchModel extends BaseModel{
    private String count;
    private List<BusinessItemModel> businesslist;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<BusinessItemModel> getBusinesslist() {
        return businesslist;
    }

    public void setBusinesslist(List<BusinessItemModel> businesslist) {
        this.businesslist = businesslist;
    }
}
