package com.jusfoun.jusfouninquire.net.model;

/**
 * @author zhaoyapeng
 * @version create time:15/11/26上午11:01
 * @Email zyp@jusfoun.com
 * @Description ${分享model}
 */
public class ShareModel  {
    private String title;
    private String content;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String image;
}
