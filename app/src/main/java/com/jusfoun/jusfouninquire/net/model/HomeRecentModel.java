package com.jusfoun.jusfouninquire.net.model;

import java.io.Serializable;
import java.util.List;

/**
 * 近期变更、企业雷达
 *
 * @时间 2017/11/8
 * @作者 LiuGuangDan
 */

public class HomeRecentModel extends BaseModel implements Serializable {

    public List<RecentBean> list;

    public static class RecentBean {
        public String count;
        public int type;
        public String title;
    }
}