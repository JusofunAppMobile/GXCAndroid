package com.gxc.model;

import java.util.List;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 13:24
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class HomeNewsModel {


    /**
     * newsFrom : 嗷嗷嗷
     * newsTime : 2019-01-08 10:13
     * newsURL : http://39.106.181.46:9090/pagehtml/statichtml/24a60fc712eb11e9beaa22b70187b366.html
     * id : 24a60fc712eb11e9beaa22b70187b366
     * newsName : 热点行业资讯
     */

    public String newsFrom;
    public String newsTime;
    public String newsURL;
    public String id;
    public String newsName;
    public int newsType; // 是否直连  1：是  0 ：否   当类型为直连时返回参数参照热点图
    public List<String> newsImage;
}
