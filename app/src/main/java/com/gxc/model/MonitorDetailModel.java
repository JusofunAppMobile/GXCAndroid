package com.gxc.model;

/**
 * @author liuguangdan
 * @version create at 2019/1/11/011 15:36
 * @Description ${}
 */
public class MonitorDetailModel {

    public int type; // 1:父布局 2：子布局
    public int status;// 1:法律诉讼 2专利信息 3变更信息
    public int total;
    public String contont;
    public String time;

    public MonitorDetailModel() {
    }

    /**
     * 父布局
     * @param status
     * @param total
     */
    public MonitorDetailModel(int status, int total, String title) {
        type = 1;
        this.status = status;
        this.total = total;
        this.contont = title;
    }

    /**
     * 子布局
     * @param contont
     * @param time
     */
    public MonitorDetailModel(String contont, String time) {
        type = 2;
        this.contont = contont;
        this.time = time;
    }
}
