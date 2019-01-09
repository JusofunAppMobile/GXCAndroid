package com.jusfoun.jusfouninquire.net.model;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class BusinessItemModel extends BaseModel {
    private String companyname,companyid,legal,companystate;

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

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getCompanystate() {
        return companystate;
    }

    public void setCompanystate(String companystate) {
        this.companystate = companystate;
    }
}
