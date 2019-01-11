package com.gxc.model;

/**
 * @author liuguangdan
 * @version create at 2019/1/11/011 15:36
 * @Description ${}
 */
public class MonitorDetailModel {

    public int type; // 1:父布局 2：子布局
    public int status;// 1:法律诉讼 2专利信息 3变更信息

    public MonitorDetailModel() {
    }

    public MonitorDetailModel(int type,int status) {
        this.type = type;
        this.status = status;
    }
}
