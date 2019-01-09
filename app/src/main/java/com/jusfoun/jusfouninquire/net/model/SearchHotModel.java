package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * SearchHotModel
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/15
 * @Description :热门搜索model
 */
public class SearchHotModel extends BaseModel{
    private List<HotWordItemModel> hotlist;

    public List<HotWordItemModel> getHotlist() {
        return hotlist;
    }

    public void setHotlist(List<HotWordItemModel> hotlist) {
        this.hotlist = hotlist;
    }
}
