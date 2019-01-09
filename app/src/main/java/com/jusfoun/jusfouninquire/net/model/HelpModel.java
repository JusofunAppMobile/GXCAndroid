package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1115:07
 * @Email zyp@jusfoun.com
 * @Description ${帮助model}
 */
public class HelpModel extends BaseModel {
    private HomeTopDataModel topData;
    private String appintroduction;
    private List<AppModel> apprecommenlist;
    private String customerqq;
    private String customermail;
    private String customerphone;
    private String qqgroup;
    private String customeravatar;

    public HomeTopDataModel getTopData() {
        return topData;
    }

    public void setTopData(HomeTopDataModel topData) {
        this.topData = topData;
    }

    public String getAppintroduction() {
        return appintroduction;
    }

    public void setAppintroduction(String appintroduction) {
        this.appintroduction = appintroduction;
    }

    public List<AppModel> getApprecommenlist() {
        return apprecommenlist;
    }

    public void setApprecommenlist(List<AppModel> apprecommenlist) {
        this.apprecommenlist = apprecommenlist;
    }

    public String getCustomerqq() {
        return customerqq;
    }

    public void setCustomerqq(String customerqq) {
        this.customerqq = customerqq;
    }

    public String getCustomermail() {
        return customermail;
    }

    public void setCustomermail(String customermail) {
        this.customermail = customermail;
    }

    public String getCustomerphone() {
        return customerphone;
    }

    public void setCustomerphone(String customerphone) {
        this.customerphone = customerphone;
    }

    public String getQqgroup() {
        return qqgroup;
    }

    public void setQqgroup(String qqgroup) {
        this.qqgroup = qqgroup;
    }

    public String getCustomeravatar() {
        return customeravatar;
    }

    public void setCustomeravatar(String customeravatar) {
        this.customeravatar = customeravatar;
    }
}
