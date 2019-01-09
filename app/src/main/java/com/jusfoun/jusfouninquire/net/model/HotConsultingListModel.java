package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by lsq on 2016/9/22.
 *  “count”:”搜索结果总数”，//客户端会根据此字段 判断逻辑
 “hotnewslist”:[
 */

public class HotConsultingListModel extends BaseModel {
    private int count;
    private List<HotConsultingItemModel> hotnewslist;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<HotConsultingItemModel> getHotnewslist() {
        return hotnewslist;
    }

    public void setHotnewslist(List<HotConsultingItemModel> hotnewslist) {
        this.hotnewslist = hotnewslist;
    }
}
