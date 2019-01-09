package com.jusfoun.jusfouninquire.service.event;

/**
 * Author  JUSFOUN
 * CreateDate 2015/12/3.
 * Description
 */
public class FollowSucceedEvent implements IEvent {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
