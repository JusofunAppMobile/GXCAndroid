package com.jusfoun.jusfouninquire.service.event;

/**
 * Created by Albert on 2015/11/16.
 * Mail : lbh@jusfoun.com
 * TODO :用户点击查看企业详情后，发送该事件，“我的关注”中，更新状态消失
 */
public class HideUpdateEvent implements IEvent {
    private String companyid;

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }
}
