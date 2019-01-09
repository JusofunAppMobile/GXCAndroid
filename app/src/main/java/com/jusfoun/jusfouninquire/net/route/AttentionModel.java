package com.jusfoun.jusfouninquire.net.route;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * Created by lsq on 2016/8/12.
 */
public class AttentionModel extends BaseModel{
   private String  isshow;
    private String  title;
    private String  imageurl;
    private String  content;
    private String  htmlurl;
    private String  cancle;
    private String  join;
    private String id;

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtmlurl() {
        return htmlurl;
    }

    public void setHtmlurl(String htmlurl) {
        this.htmlurl = htmlurl;
    }

    public String getCancle() {
        return cancle;
    }

    public void setCancle(String cancle) {
        this.cancle = cancle;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
