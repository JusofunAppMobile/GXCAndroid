package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by Albert on 2015/11/10.
 * Mail : lbh@jusfoun.com
 * TODO :获取我的关注model
 */
public class FocusedModel extends BaseModel {
    private String ismore;
    private List<FocusedItemModel> mywatchlist;

    public String getIsmore() {
        return ismore;
    }

    public void setIsmore(String ismore) {
        this.ismore = ismore;
    }

    public List<FocusedItemModel> getMywatchlist() {
        return mywatchlist;
    }

    public void setMywatchlist(List<FocusedItemModel> mywatchlist) {
        this.mywatchlist = mywatchlist;
    }
}
