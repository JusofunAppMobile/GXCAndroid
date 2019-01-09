package com.jusfoun.jusfouninquire.net.model;

import java.io.Serializable;

/**
 * @author zhaoyapeng
 * @version create time:16/3/31上午9:16
 * @Email zyp@jusfoun.com
 * @Description ${广告model}
 */
public class AdModel extends BaseModel implements Serializable{
    private String Id;
    private String ImageUrl;
    private String Type;
    private String Url;
    private String Title;
    private String Content;
    private String BtnImgUrl;

    public String getBtnImgUrl() {
        return BtnImgUrl;
    }

    public void setBtnImgUrl(String btnImgUrl) {
        BtnImgUrl = btnImgUrl;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

}
