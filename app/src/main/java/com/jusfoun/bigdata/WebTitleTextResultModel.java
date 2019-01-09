package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

import java.util.List;

/**
 * Created by JUSFOUN on 2015/9/9.
 * Description
 */
public class WebTitleTextResultModel extends BaseModel {

    private List<WebTitleItemModel> data;

    private int total;

    public List<WebTitleItemModel> getData() {
        return data;
    }

    public void setData(List<WebTitleItemModel> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
