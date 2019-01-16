package com.gxc.model;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 9:36
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class OrderModel {


    /**
     * duration : 12
     * no : 2019011517324361033
     * money : 0.01
     * name :
     * format :
     * time : 2019-01-15 17:32:43
     * title : VIP会员服务
     * type : 1
     * email :
     * url :
     * orderState : 已支付
     * status : 1
     */

    public String duration;
    public String no;
    public String money;
    public String name;
    public String format;
    public String time;
    public String title;
    public int type; // 类型 0：企业报告 1：会员服务
    public String email;
    public String url;
    public String orderState;
    public int status; // 企业报告生成状态 0：未生成 1：已生成
}
