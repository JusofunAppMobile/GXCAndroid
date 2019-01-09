package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/17.
 * Description
 */
public class HomeInfoModel extends BaseModel {

    private String version;
    private HomeTopDataModel topData;
    private List<HomeDataModel> dataList;

    private String startnumber;
    private String rate;
    private String bignumber;
    private String dishonestyurl;
    private List<HomeMyWatchModel> mywatchlist;
    private List<HomeHotBusinessModel> hotbusinesslist;
    private HomeDishonestyModel dishonesty;
    private List<AdItemModel> adlist;

    public List<AdItemModel> getAdlist() {
        return adlist;
    }

    public void setAdlist(List<AdItemModel> adlist) {
        this.adlist = adlist;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HomeTopDataModel getTopData() {
        return topData;
    }

    public void setTopData(HomeTopDataModel topData) {
        this.topData = topData;
    }

    public List<HomeDataModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<HomeDataModel> dataList) {
        this.dataList = dataList;
    }

    public String getStartnumber() {
        return startnumber;
    }

    public void setStartnumber(String startnumber) {
        this.startnumber = startnumber;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getBignumber() {
        return bignumber;
    }

    public void setBignumber(String bignumber) {
        this.bignumber = bignumber;
    }

    public String getDishonestyurl() {
        return dishonestyurl;
    }

    public void setDishonestyurl(String dishonestyurl) {
        this.dishonestyurl = dishonestyurl;
    }

    public List<HomeMyWatchModel> getMywatchlist() {
        return mywatchlist;
    }

    public void setMywatchlist(List<HomeMyWatchModel> mywatchlist) {
        this.mywatchlist = mywatchlist;
    }

    public List<HomeHotBusinessModel> getHotbusinesslist() {
        return hotbusinesslist;
    }

    public void setHotbusinesslist(List<HomeHotBusinessModel> hotbusinesslist) {
        this.hotbusinesslist = hotbusinesslist;
    }

    public HomeDishonestyModel getDishonesty() {
        return dishonesty;
    }

    public void setDishonesty(HomeDishonestyModel dishonesty) {
        this.dishonesty = dishonesty;
    }
}
