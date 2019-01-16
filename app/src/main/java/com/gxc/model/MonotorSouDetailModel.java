package com.gxc.model;

import java.util.List;

/**
 * @author liuguangdan
 * @version create at 2019/1/16/016 18:29
 * @Description ${}
 */
public class MonotorSouDetailModel {

    /**
     * total : 37
     * lcon : 1
     * data : [{"contont":"新变更事项：投资人","time":"2018-03-14"},{"contont":"新变更事项：董事（理事）、经理、监事","time":"2018-03-14"},{"contont":"新变更事项：董事（理事）、经理、监事","time":"2017-09-30"},{"contont":"新变更事项：住所","time":"2017-09-30"},{"contont":"新变更事项：投资人","time":"2017-07-24"},{"contont":"新变更事项：注册资本","time":"2017-07-24"},{"contont":"新变更事项：投资人","time":"2017-05-05"},{"contont":"新变更事项：投资人","time":"2016-10-18"},{"contont":"新变更事项：投资人","time":"2016-08-25"},{"contont":"新变更事项：投资人","time":"2016-08-25"}]
     * monitor_name : 变更信息
     */

    public int total;
    public int lcon;
    public String monitor_name;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * contont : 新变更事项：投资人
         * time : 2018-03-14
         */

        public String contont;
        public String time;
    }
}
