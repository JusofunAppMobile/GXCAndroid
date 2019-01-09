package com.jusfoun.jusfouninquire.service.event;

import java.util.HashMap;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/31.
 * Email wcc@jusfoun.com
 * Description 失信结果页切换event
 */
public class DishonestResultEvent implements IEvent {

    private HashMap<String,String> params;
    private int position;

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
