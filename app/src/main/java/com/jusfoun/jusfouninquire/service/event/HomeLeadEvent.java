package com.jusfoun.jusfouninquire.service.event;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/30.
 * Description
 */
public class HomeLeadEvent implements IEvent {
    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    private int padding = 0;


}
