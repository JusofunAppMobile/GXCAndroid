package com.jusfoun.jusfouninquire.service.event;

/**
 * @author lee
 * @version create time:2015/11/1818:03
 * @Email email
 * @Description $登录完成之后的event
 */

public class CompleteLoginEvent implements IEvent {
    private int isLogin;

    public CompleteLoginEvent(int isLogin) {
        this.isLogin = isLogin;
    }

    public int getIsLogin() {
        return isLogin;
    }

}
