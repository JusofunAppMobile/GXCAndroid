package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author lee
 * @version create time:2015/11/1010:12
 * @Email email
 * @Description $搜索公司
 */

public class SearchCompanyModel extends BaseModel {

    private List<CompanyModel> companylist;
    private int totalcount;

    @Override
    public String toString() {
        return "SearchCompanyModel{" +
                "companylist=" + companylist +
                '}';
    }

    public List<CompanyModel> getCompanylist() {
        return companylist;
    }

    public void setCompanylist(List<CompanyModel> companylist) {
        this.companylist = companylist;
    }

    public int getCount() {
        return totalcount;
    }

    public void setCount(int count) {
        this.totalcount = count;
    }
}
