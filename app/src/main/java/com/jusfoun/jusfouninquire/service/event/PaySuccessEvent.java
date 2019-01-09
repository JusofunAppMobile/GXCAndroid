package com.jusfoun.jusfouninquire.service.event;

/**
 * @author zhaoyapeng
 * @version create time:17/11/2810:10
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class PaySuccessEvent implements IEvent {

    public boolean isVip;

    public PaySuccessEvent(boolean isVip) {
        this.isVip = isVip;
    }
}
