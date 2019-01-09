package com.jusfoun.jusfouninquire.service.event;

/**
 * Created by Albert on 2016-3-31.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:下载通知图片成功的时候发送该event
 */
public class NoticeImageLoadEvent implements IEvent{
    private int count;

    public NoticeImageLoadEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
