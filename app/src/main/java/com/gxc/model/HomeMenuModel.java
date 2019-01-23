package com.gxc.model;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 11:40
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class HomeMenuModel {


    /**
     * menuName : 股东高管
     * menuImage : http//:46575.png
     * menuType : 1
     */

    public String menuName;
    public String menuImage;
    public int menuImageId;
    public int menuType; // 1：股东高管 2：主营产品 3：失信查询 4：查关系 5：风险分析 6：查税号 7：招聘
    public String menuUrl;
    public String webUrl;

    public HomeMenuModel(String menuName, int menuType) {
        this.menuName = menuName;
        this.menuType = menuType;
    }


    public HomeMenuModel(int id, String menuName) {
        this.menuName = menuName;
        this.menuImageId = id;
    }

    public HomeMenuModel(int id, String menuName, int menuType) {
        this.menuName = menuName;
        this.menuImageId = id;
        this.menuType = menuType;
    }


    public HomeMenuModel() {
    }
}
