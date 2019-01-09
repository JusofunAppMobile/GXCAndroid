package com.jusfoun.jusfouninquire.service.event;

/**
 * DoSearchEvent
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/15
 * @Description :需要进行搜索的时候发送此事件
 */
public class DoSearchEvent implements IEvent{
    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
