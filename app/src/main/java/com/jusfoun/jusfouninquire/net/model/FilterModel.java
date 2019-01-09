package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * FilterModel
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/10
 * @Description :筛选接口返回model
 */
public class FilterModel extends BaseModel{
    private List<FilterItemModel> filterList;

    public List<FilterItemModel> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<FilterItemModel> filterList) {
        this.filterList = filterList;
    }
}
