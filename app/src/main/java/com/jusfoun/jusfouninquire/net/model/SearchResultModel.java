package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class SearchResultModel extends BaseModel {
    String count;
    List<BusinessItemModel> businesslist;
    List<ShareHolderItemModel> shareholderlist;

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

    public List<ShareHolderItemModel> getShareholderlist() {
        return shareholderlist;
    }

    public void setShareholderlist(List<ShareHolderItemModel> shareholderlist) {
        this.shareholderlist = shareholderlist;
    }
}
