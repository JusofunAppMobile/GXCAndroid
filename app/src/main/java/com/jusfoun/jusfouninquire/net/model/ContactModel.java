package com.jusfoun.jusfouninquire.net.model;


public class ContactModel extends BaseModel {

    /**
     * data : {"contactsUrl":"https://qixinbaointranettest.jusfoun.com/Html/companyPhonePay.html?userid=41779557","contactsType":0}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * contactsUrl : https://qixinbaointranettest.jusfoun.com/Html/companyPhonePay.html?userid=41779557
         * contactsType : 0
         */

        public String contactsUrl;
        public int contactsType;
        public String url;
        public int type;
    }
}
