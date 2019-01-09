package com.jusfoun.jusfouninquire.ui.util.crawl;

import com.jusfoun.jusfouninquire.service.event.IEvent;

/**
 * @author zhaoyapeng
 * @version create time:17/8/219:01
 * @Email zyp@jusfoun.com
 * @Description ${抓取task event}
 */
public class TaskEvent implements IEvent {
    private String state, url, time, content;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
