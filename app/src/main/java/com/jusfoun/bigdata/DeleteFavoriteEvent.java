package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.service.event.IEvent;

/**
 * @author lee
 * @version create time:2015/12/109:51
 * @Email email
 * @Description 删除我的收藏后，触发其他列表的数据展示
 */

public class DeleteFavoriteEvent implements IEvent{
    private int eventType;

    public int getEventType() {
        return eventType;
    }

    public DeleteFavoriteEvent(int eventType) {
        this.eventType = eventType;
    }
}
