package com.gxc.model;

/**
 * @author liuguangdan
 * @version create at 2019/1/14/014 15:32
 * @Description ${}
 */
public class UserModel {

    public String headIcon;
    public int pushStatus; // 消息推送开关状态 0：开 1：关
    public int authStatus; // 认证状态 0：未认证 1：已认证
    public String userId;
    public String token;
    public String trade;
    public String phone;
    public String company;
    public String regId;
    public String department;
    public String job;
    public int vipStatus; // 用户vip状态 0：普通用户 1：vip用户
    public String email;
    public int vipLastDay;
}
