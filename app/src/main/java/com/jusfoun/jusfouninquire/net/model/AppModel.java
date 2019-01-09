package com.jusfoun.jusfouninquire.net.model;

/**
 * @author lee
 * @version create time:2015/11/1210:17
 * @Email email
 * @Description $应用model
 */

public class AppModel extends BaseModel{
    private String appname,appicon,appintro,appurl;

    @Override
    public String toString() {
        return "AppModel{" +
                "appname='" + appname + '\'' +
                ", appicon='" + appicon + '\'' +
                ", appintro='" + appintro + '\'' +
                ", appurl='" + appurl + '\'' +
                '}';
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getAppicon() {
        return appicon;
    }

    public void setAppicon(String appicon) {
        this.appicon = appicon;
    }

    public String getAppintro() {
        return appintro;
    }

    public void setAppintro(String appintro) {
        this.appintro = appintro;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }
}
