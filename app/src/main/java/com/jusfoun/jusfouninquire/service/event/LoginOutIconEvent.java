package com.jusfoun.jusfouninquire.service.event;

/**
 * @author zhaoyapeng
 * @version create time:16/9/2510:41
 * @Email zyp@jusfoun.com
 * @Description ${退出登录 event}
 */
public class LoginOutIconEvent implements IEvent {
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
