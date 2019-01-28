package com.gxc.utils;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/22/022 18:25
 * @Description ${}
 */
public class HtmlUrlUtils {

    private static String getHost() {
        return InquireApplication.application.getResources().getString(R.string.http_host_html);
    }

    /**
     * 用户协议
     * @return
     */
    public static String getAgreeUrl() {
        return getHost() + "/creditReport/useragreement.html";
    }

    /**
     * 隐私协议
     * @return
     */
    public static String getSecretUrl() {
        return getHost() + "/creditReport/privacyprotectionagreement.html";
    }

    /**
     * 使用帮助
     * @return
     */
    public static String getHelpUrl() {
        return getHost() + "/dist/#/check/help";
    }

    /**
     * 关于我们
     * @return
     */
    public static String getAboutUrl() {
        return getHost() + "/dist/#/about";
    }

}
