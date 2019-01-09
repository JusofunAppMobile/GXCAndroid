package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by Albert on 2015/11/16.
 * Mail : lbh@jusfoun.com
 * TODO :高级搜索条件model
 */
public class AdvancedConfigModel extends BaseModel{
    List<AdvancedConfigItemModel> searchlist;

    public List<AdvancedConfigItemModel> getSearchlist() {
        return searchlist;
    }

    public void setSearchlist(List<AdvancedConfigItemModel> searchlist) {
        this.searchlist = searchlist;
    }
}
