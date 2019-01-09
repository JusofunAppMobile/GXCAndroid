package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class SearchDisHonestModel extends BaseModel {
    private String ismore;
    List<DisHonestItemModel> dishonestylist;

    public String getIsmore() {
        return ismore;
    }

    public void setIsmore(String ismore) {
        this.ismore = ismore;
    }

    public List<DisHonestItemModel> getDishonestylist() {
        return dishonestylist;
    }

    public void setDishonestylist(List<DisHonestItemModel> dishonestylist) {
        this.dishonestylist = dishonestylist;
    }
}
