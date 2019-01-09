package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1311:13
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class ZFilterListModel extends BaseModel {
    private List<ZFilterItemModel> citylist,province,industrylist,registerfunds,cratetime;

    public List<ZFilterItemModel> getCitylist() {
        return citylist;
    }

    public void setCitylist(List<ZFilterItemModel> citylist) {
        this.citylist = citylist;
    }

    public List<ZFilterItemModel> getProvince() {
        return province;
    }

    public void setProvince(List<ZFilterItemModel> province) {
        this.province = province;
    }

    public List<ZFilterItemModel> getIndustrylist() {
        return industrylist;
    }

    public void setIndustrylist(List<ZFilterItemModel> industrylist) {
        this.industrylist = industrylist;
    }

    public List<ZFilterItemModel> getRegisterfunds() {
        return registerfunds;
    }

    public void setRegisterfunds(List<ZFilterItemModel> registerfunds) {
        this.registerfunds = registerfunds;
    }

    public List<ZFilterItemModel> getCratetime() {
        return cratetime;
    }

    public void setCratetime(List<ZFilterItemModel> cratetime) {
        this.cratetime = cratetime;
    }
}
