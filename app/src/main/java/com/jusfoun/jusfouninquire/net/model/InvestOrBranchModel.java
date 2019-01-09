package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/13.
 * Description
 */
public class InvestOrBranchModel extends BaseModel {
    private String ismore;
    private List<InvestOrBranchItemModel> list;

    public List<InvestOrBranchItemModel> getList() {
        return list;
    }

    public void setList(List<InvestOrBranchItemModel> list) {
        this.list = list;
    }

    public String getIsmore() {
        return ismore;
    }

    public void setIsmore(String ismore) {
        this.ismore = ismore;
    }
}
