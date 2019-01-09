package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by Albert on 2016/1/14.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description: 获取设置、个人中心数据model
 */
public class HomeVersionModel extends BaseModel {
    private String page_name,app_introduction,header_picture,header_picture_link;
    private List<BottomMenuModel> bottom_menu;

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getApp_introduction() {
        return app_introduction;
    }

    public void setApp_introduction(String app_introduction) {
        this.app_introduction = app_introduction;
    }

    public String getHeader_picture() {
        return header_picture;
    }

    public void setHeader_picture(String header_picture) {
        this.header_picture = header_picture;
    }

    public String getHeader_picture_link() {
        return header_picture_link;
    }

    public void setHeader_picture_link(String header_picture_link) {
        this.header_picture_link = header_picture_link;
    }

    public List<BottomMenuModel> getBottom_menu() {
        return bottom_menu;
    }

    public void setBottom_menu(List<BottomMenuModel> bottom_menu) {
        this.bottom_menu = bottom_menu;
    }
}
