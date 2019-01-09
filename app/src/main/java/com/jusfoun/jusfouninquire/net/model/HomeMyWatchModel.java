package com.jusfoun.jusfouninquire.net.model;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/17.
 * Description 首页数据Model
 */
public class HomeMyWatchModel extends BaseModel {

    private String companyname;
    private String companyid;
    private String legal;
    private String companystate;
    private String url;
    private String isupdate;
    private String updatetime;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(String isupdate) {
        this.isupdate = isupdate;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
