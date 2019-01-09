package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by lsq on 2016/8/11.
 */
public class HotCompanyListModel extends BaseModel {
    private  int count;
    private List<HomeDataItemModel> businesslist;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<HomeDataItemModel> getBusinesslist() {
        return businesslist;
    }

    public void setBusinesslist(List<HomeDataItemModel> businesslist) {
        this.businesslist = businesslist;
    }
}
