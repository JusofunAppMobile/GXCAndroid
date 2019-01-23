package com.gxc.event;

import com.jusfoun.jusfouninquire.service.event.IEvent;

/**
 * @author liuguangdan
 * @version create at 2019/1/23/023 15:09
 * @Description ${}
 */
public class CompanySelectEvent implements IEvent {

    public String name;
    public String id;

    public CompanySelectEvent(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
