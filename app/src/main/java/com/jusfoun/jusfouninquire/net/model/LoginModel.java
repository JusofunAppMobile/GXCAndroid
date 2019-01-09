package com.jusfoun.jusfouninquire.net.model;

/**
 * @author lee
 * @version create time:2015/11/1110:06
 * @Email email
 * @Description $登录的model
 */

public class LoginModel extends BaseModel {

    private UserInfoModel userinfo;



    public UserInfoModel getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfoModel userinfo) {
        this.userinfo = userinfo;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "userinfo=" + userinfo +
                '}';
    }
}
