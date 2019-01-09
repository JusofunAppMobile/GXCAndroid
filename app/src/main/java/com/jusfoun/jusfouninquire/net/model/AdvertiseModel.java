package com.jusfoun.jusfouninquire.net.model;

/**
 * @author lee
 * @version create time:2015/11/1915:51
 * @Email email
 * @Description $获取广告
 */

public class AdvertiseModel extends BaseModel{
    private String imageurl,adurl;

    @Override
    public String toString() {
        return "AdvertiseModel{" +
                "imageurl='" + imageurl + '\'' +
                ", adurl='" + adurl + '\'' +
                '}';
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getAdurl() {
        return adurl;
    }

    public void setAdurl(String adurl) {
        this.adurl = adurl;
    }
}
