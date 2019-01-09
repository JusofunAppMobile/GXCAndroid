package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1017:15
 * @Email zyp@jusfoun.com
 * @Description ${新首页Model}
 */
public class NewHomeModel extends BaseModel {
    private List<HomeDataItemModel> hotlist;
    private List<HomeDataItemModel> newaddlist;
    private List<HotConsultingItemModel> hotnewslist;

    private List<AdItemModel> adlist;
    private HomeDishonestyModel dishonesty;

    public List<HomeDataItemModel> getNewaddlist() {
        return newaddlist;
    }

    public void setNewaddlist(List<HomeDataItemModel> newaddlist) {
        this.newaddlist = newaddlist;
    }

    public List<HomeDataItemModel> getHotlist() {
        return hotlist;
    }

    public void setHotlist(List<HomeDataItemModel> hotlist) {
        this.hotlist = hotlist;
    }

    public List<AdItemModel> getAdlist() {
        return adlist;
    }

    public void setAdlist(List<AdItemModel> adlist) {
        this.adlist = adlist;
    }

    public HomeDishonestyModel getDishonesty() {
        return dishonesty;
    }

    public void setDishonesty(HomeDishonestyModel dishonesty) {
        this.dishonesty = dishonesty;
    }

    public List<HotConsultingItemModel> getHotnewslist() {
        return hotnewslist;
    }

    public void setHotnewslist(List<HotConsultingItemModel> hotnewslist) {
        this.hotnewslist = hotnewslist;
    }
}
