package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/20.
 * Description
 */
public class HomeDishonestyModel extends BaseModel {

    private String name;
    private String dishonestyurl;
    private List<HomeDishonestyItemModel> dishonestylist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDishonestyurl() {
        return dishonestyurl;
    }

    public void setDishonestyurl(String dishonestyurl) {
        this.dishonestyurl = dishonestyurl;
    }

    public List<HomeDishonestyItemModel> getDishonestylist() {
        return dishonestylist;
    }

    public void setDishonestylist(List<HomeDishonestyItemModel> dishonestylist) {
        this.dishonestylist = dishonestylist;
    }
}
