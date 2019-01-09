package com.jusfoun.jusfouninquire.service.event;

/**
 * GoTypeSearchEvent
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/16
 * @Description :搜索页面关闭，发送此event，进入分类搜索页面,分类搜索页面  和  搜索页面   为同一个页面，根据此事件进行不同显示处理逻辑
 */
public class GoTypeSearchEvent implements IEvent{
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
