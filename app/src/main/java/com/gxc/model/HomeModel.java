package com.gxc.model;

import java.util.List;

/**
 * @author liuguangdan
 * @version create at 2019/1/14/014 19:24
 * @Description ${}
 */
public class HomeModel {

    public List<AdverModel> adImages;
    public List<HomeMonitorModel> monitor;
    public List<HomeNewsModel> news;
    public List<String> keywords;
    public List<HomeMenuModel> menu;

    public static  class AdverModel{
        public String imageURL;
        public String webURL;

        public AdverModel(String imageURL, String webURL) {
            this.imageURL = imageURL;
            this.webURL = webURL;
        }

        public AdverModel() {
        }
    }

}
