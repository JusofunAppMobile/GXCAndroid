package com.jusfoun.jusfouninquire.net.model;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1311:14
 * @Email zyp@jusfoun.com
 * @Description ${筛选Item}
 */
public class ZFilterItemModel extends BaseModel {
    private String id;
    private String city;
    private String province;
    private String industryname;
    private String industryid;
    private String foundsname;
    private String submit;
    private String time;
    private String submittime;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubmittime() {
        return submittime;
    }

    public void setSubmittime(String submittime) {
        this.submittime = submittime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIndustryname() {
        return industryname;
    }

    public void setIndustryname(String industryname) {
        this.industryname = industryname;
    }

    public String getIndustryid() {
        return industryid;
    }

    public void setIndustryid(String industryid) {
        this.industryid = industryid;
    }

    public String getFoundsname() {
        return foundsname;
    }

    public void setFoundsname(String foundsname) {
        this.foundsname = foundsname;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }
}
