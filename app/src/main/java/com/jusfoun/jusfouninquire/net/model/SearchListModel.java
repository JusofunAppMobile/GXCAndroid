package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1310:29
 * @Email zyp@jusfoun.com
 * @Description ${搜索listmodel}
 */
public class SearchListModel extends BaseModel {
    private int count;
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
