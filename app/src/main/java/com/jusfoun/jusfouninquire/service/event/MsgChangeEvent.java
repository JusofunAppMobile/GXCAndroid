package com.jusfoun.jusfouninquire.service.event;

/**
 * MsgChangeEvent
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/10/17
 * @Description :有新的消息到来或者点击未读消息后都需要发送此事件
 */
public class MsgChangeEvent implements IEvent{
    private int delta;

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }
}
