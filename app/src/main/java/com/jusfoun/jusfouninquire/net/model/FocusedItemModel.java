package com.jusfoun.jusfouninquire.net.model;

/**
 * Created by Albert on 2015/11/10.
 * Mail : lbh@jusfoun.com
 * TODO :我的关注中某个公司的item model
 */
public class FocusedItemModel extends BaseModel {
    private String companyname,companyid,legal,updatetime,companystate,url;
    private int isupdate;

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

    public int getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(int isupdate) {
        this.isupdate = isupdate;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
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

    @Override
    public String toString() {
        return "FocusedItemModel{" +
                "companyname='" + companyname + '\'' +
                ", companyid='" + companyid + '\'' +
                ", legal='" + legal + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", companystate='" + companystate + '\'' +
                ", url='" + url + '\'' +
                ", isupdate=" + isupdate +
                '}';
    }
}
