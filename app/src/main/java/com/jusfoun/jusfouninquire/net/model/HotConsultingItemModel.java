package com.jusfoun.jusfouninquire.net.model;

/**
 * Created by lsq on 2016/9/22.
 *    “newsid”: “资讯id”,
 “newsimgurl”:”资讯图片链接”,
 “newstitle”:”资讯标题”,
 “newreadcount”:”资讯的阅读数量” //转换好单位
 “newdetailurl”:”资讯对应的url地址”
 */

public class HotConsultingItemModel extends BaseModel {
    private String newsid;
    private String newsimgurl;
    private String newstitle;
    private int newreadcount;
    private String newdetailurl;

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getNewsimgurl() {
        return newsimgurl;
    }

    public void setNewsimgurl(String newsimgurl) {
        this.newsimgurl = newsimgurl;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public int getNewreadcount() {
        return newreadcount;
    }

    public void setNewreadcount(int newreadcount) {
        this.newreadcount = newreadcount;
    }

    public String getNewdetailurl() {
        return newdetailurl;
    }

    public void setNewdetailurl(String newdetailurl) {
        this.newdetailurl = newdetailurl;
    }
}
