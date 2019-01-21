package com.gxc.model;

/**
 * @author liuguangdan
 * @version create at 2019/1/14/014 15:32
 * @Description ${}
 */
public class UserModel {

    public String headIcon;
    public int pushStatus; // 消息推送开关状态 0：开 1：关
    public int authStatus; // 认证状态 0：未认证1：审核中 2：审核失败 3：审核成功
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
    // 个人中心在企业认证中、和成功后显示企业名称，其他情况未认证、认证失败、显示手机号，个人资料中的公司只有在认证成功后不能修改，其他情况都能修改
    public String authCompany;
    public String companyId;


    public String taxid;
    public String states;
}
