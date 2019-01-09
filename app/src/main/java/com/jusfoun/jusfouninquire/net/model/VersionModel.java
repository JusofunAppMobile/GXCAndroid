package com.jusfoun.jusfouninquire.net.model;

/**
 * @author lee
 * @version create time:2015/11/1211:26
 * @Email email
 * @Description $版本model
 */

public class VersionModel extends BaseModel{
    private  String describe,url,versionname,filter,titletext,cacletext,updatetext,updatetime;

    private int update;
    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTitletext() {
        return titletext;
    }

    public void setTitletext(String titletext) {
        this.titletext = titletext;
    }

    public String getCacletext() {
        return cacletext;
    }

    public void setCacletext(String cacletext) {
        this.cacletext = cacletext;
    }

    public String getUpdatetext() {
        return updatetext;
    }

    public void setUpdatetext(String updatetext) {
        this.updatetext = updatetext;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "VersionModel{" +
                "update='" + update + '\'' +
                ", describe='" + describe + '\'' +
                ", url='" + url + '\'' +
                ", versionname='" + versionname + '\'' +
                ", filter='" + filter + '\'' +
                ", titletext='" + titletext + '\'' +
                ", cacletext='" + cacletext + '\'' +
                ", updatetext='" + updatetext + '\'' +
                '}';
    }
}
