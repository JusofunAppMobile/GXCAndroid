package com.jusfoun.jusfouninquire.net.model;

/**
 * Author  wangchenchen
 * CreateDate 2016/9/23.
 * Email wcc@jusfoun.com
 * Description 热门咨询统计model
 */
public class ConsultingStatisticsModel extends BaseModel {

    private String newreadcount;//”:“当前该条资讯的阅读数”  //转换好单位

    public String getNewreadcount() {
        return newreadcount;
    }

    public void setNewreadcount(String newreadcount) {
        this.newreadcount = newreadcount;
    }
}
