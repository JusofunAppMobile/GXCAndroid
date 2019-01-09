package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.service.event.IEvent;

/**
 * @author lee
 * @version create time:2015/12/1010:43
 * @Email email
 * @Description 从收藏进企业详情——>企业图谱——>查看公司详情——>收藏成功——>
 */

public class AddToFavoriteEvent implements IEvent{
    private String favoriteId = "";

    public String getFavoriteId() {
        return favoriteId;
    }

    public AddToFavoriteEvent(String favoriteId) {
        this.favoriteId = favoriteId;
    }
    public AddToFavoriteEvent() {

    }
}
