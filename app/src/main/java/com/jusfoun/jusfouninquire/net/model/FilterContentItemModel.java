package com.jusfoun.jusfouninquire.net.model;

/**
 * FilterContentItemModel
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/10
 * @Description :筛选条件 内容的 item  model
 */
public class FilterContentItemModel {
    private String name,value,key;
    private boolean select;
    private boolean isLocation=false;

    public String getItemname() {
        return name;
    }

    public void setItemname(String itemname) {
        this.name = itemname;
    }

    public String getItemvalue() {
        return value;
    }

    public void setItemvalue(String itemvalue) {
        this.value = itemvalue;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }
}
