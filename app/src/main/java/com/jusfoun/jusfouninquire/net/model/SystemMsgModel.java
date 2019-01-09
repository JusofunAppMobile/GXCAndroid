package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author lee
 * @version create time:2015/11/1115:24
 * @Email email
 * @Description $系统消息
 */

public class SystemMsgModel extends BaseModel{
    private List<SystemMsgItemModel> systemlist;
    private String ismore;

    public String getIsmore() {
        return ismore;
    }

    public void setIsmore(String ismore) {
        this.ismore = ismore;
    }

    public List<SystemMsgItemModel> getSystemlist() {
        return systemlist;
    }

    public void setSystemlist(List<SystemMsgItemModel> systemlist) {
        this.systemlist = systemlist;
    }

    @Override
    public String toString() {
        return "SystemMsgModel{" +
                "systemlist=" + systemlist +
                ", ismore='" + ismore + '\'' +
                '}';
    }
}
