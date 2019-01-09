package com.jusfoun.jusfouninquire.net.model;

import java.io.Serializable;
import java.util.List;

public class SearchContactListModel extends BaseModel implements Serializable {

    public int totalCount;
    public List<DataBean> data;

    public static class DataBean implements Serializable {
        /**
         * id : 7727314
         * type : 个体工商户
         * operatingPeriodStart : null
         * establishDate : 1268668800000
         * legalPerson : 王芳芳
         * name : 百度面馆
         * phone : 15109774464,18946877680
         */

        public String id;
        public String type;
        public Object operatingPeriodStart;
        public String establishDate;
        public String legalPerson;
        public String name;
        public List<String> phoneArr;

        public boolean isOpen = false;
        public boolean isSelect = false;
    }
}
