package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * FilterItemModel
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/10
 * @Description :获取筛选条件的 item model
 */
public class FilterItemModel {
    private int type;
    private String name;
    private List<FilterContentItemModel> filterItemList;
    private boolean isUnfold=false;
    private String key;
    private boolean select=false;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilterContentItemModel> getFilterItemList() {
        return filterItemList;
    }

    public void setFilterItemList(List<FilterContentItemModel> filterItemList) {
        this.filterItemList = filterItemList;
    }

    public boolean isUnfold() {
        return isUnfold;
    }

    public void setUnfold(boolean unfold) {
        isUnfold = unfold;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
