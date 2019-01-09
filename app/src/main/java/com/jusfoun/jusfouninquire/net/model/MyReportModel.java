package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * 说明
 *
 * @时间 2017/9/6
 * @作者 LiuGuangDan
 */

public class MyReportModel extends BaseModel {


    /**
     * totalCount : 1
     * dataResult : [{"entName":"小米科技有限责任公司","entId":"","reportTime":"2017年10月19日 3:26","reportId":"3","reportUrl":"http://192.168.1.6:9981/Html/report.html?entName=小米科技有限责任公司&userid=1"}]
     */

    public long totalCount;
    public List<DataResultBean> dataResult;

    public static class DataResultBean {
        /**
         * entName : 小米科技有限责任公司
         * entId :
         * reportTime : 2017年10月19日 3:26
         * reportId : 3
         * reportUrl : http://192.168.1.6:9981/Html/report.html?entName=小米科技有限责任公司&userid=1
         */

        public String entName;
        public String entId;
        public String reportTime;
        public String reportId;
        public String reportUrl;
    }
}
