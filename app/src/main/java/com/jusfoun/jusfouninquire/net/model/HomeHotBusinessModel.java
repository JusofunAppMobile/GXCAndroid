package com.jusfoun.jusfouninquire.net.model;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/17.
 * Description
 */
public class HomeHotBusinessModel extends BaseModel {
    private String companyname;
    private String companyid;
    private String CompanyState;
    private String attentioncount;
    private String ratings;

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getAttentioncount() {
        return attentioncount;
    }

    public void setAttentioncount(String attentioncount) {
        this.attentioncount = attentioncount;
    }

    public String getCompanyState() {
        return CompanyState;
    }

    public void setCompanyState(String companyState) {
        CompanyState = companyState;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }
}
