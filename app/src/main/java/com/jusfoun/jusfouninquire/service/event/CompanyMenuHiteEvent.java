package com.jusfoun.jusfouninquire.service.event;

/**
 * Author  JUSFOUN
 * CreateDate 2015/12/4.
 * Description
 */
public class CompanyMenuHiteEvent implements IEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
