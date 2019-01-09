package com.jusfoun.jusfouninquire.net.model;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/17.
 * Description
 */
public class HomeDishonestyItemModel extends BaseModel {
    private String year;
    private String month;
    private String title;
    private String url;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
