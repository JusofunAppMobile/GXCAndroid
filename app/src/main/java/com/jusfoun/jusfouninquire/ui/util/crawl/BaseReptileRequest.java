package com.jusfoun.jusfouninquire.ui.util.crawl;

/**
 * @author zhaoyapeng
 * @version create time:17/7/3116:12
 * @Email zyp@jusfoun.com
 * @Description ${请求基类}
 */
public interface BaseReptileRequest {
    void start();

    void setDispatcher(ReptileDispatcher reptileDispatcher);

    void setAtLast(boolean isAtLast);
}
