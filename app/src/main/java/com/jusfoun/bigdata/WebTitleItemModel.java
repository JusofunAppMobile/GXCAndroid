package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

import java.util.ArrayList;

/**
 * Created by JUSFOUN on 2015/9/9.
 * Description
 */
public class WebTitleItemModel extends BaseModel {

    private int ID;

    private String ParentMenuName;

    private ArrayList<WebTitleConditionModel> SubclassMenu;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getParentMenuName() {
        return ParentMenuName;
    }

    public void setParentMenuName(String parentMenuName) {
        ParentMenuName = parentMenuName;
    }

    public ArrayList<WebTitleConditionModel> getSubclassMenu() {
        return SubclassMenu;
    }

    public void setSubclassMenu(ArrayList<WebTitleConditionModel> subclassMenu) {
        SubclassMenu = subclassMenu;
    }
}
