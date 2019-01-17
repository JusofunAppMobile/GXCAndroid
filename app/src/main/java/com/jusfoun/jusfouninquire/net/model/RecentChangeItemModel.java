package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * 说明
 *
 * @时间 2017/9/6
 * @作者 LiuGuangDan
 */

public class RecentChangeItemModel extends BaseModel {


    /**
     * totalCount : 5015776
     * dataResult : [{"id":"5dd550059bc10d7d7d30c1f24516c453","areaId":"11","businessLicenseId":"64312837","ename":"建德市农村信用合作联社李家信用社长林分社","changeItem":"法定代表人变更","changeDate":"253392422400000","createDate":"1504771434000","updateDate":"1504771434000","emptyRatio":null,"dataStatus":null,"changeBefore":"法定代表人:郎美华","changeAfter":"法定代表人:黄建忠"},{"id":"6b64700a1a9b927957f31e423976ee89","areaId":"11","businessLicenseId":"32777212","ename":"建德市农村信用合作联社大同信用社溪口分社","changeItem":"法定代表人变更","changeDate":"253392422400000","createDate":"1504771273000","updateDate":"1504771273000","emptyRatio":null,"dataStatus":null,"changeBefore":"法定代表人:严海平 身份证编号:***************","changeAfter":"法定代表人:许利平 身份证编号:***************"}]
     */

    public int totalCount;
    public List<DataResultBean> dataResult;
    public List<DataResultBean> changeList;

    public static class DataResultBean {
        /**
         * id : 5dd550059bc10d7d7d30c1f24516c453
         * areaId : 11
         * businessLicenseId : 64312837
         * ename : 建德市农村信用合作联社李家信用社长林分社
         * changeItem : 法定代表人变更
         * changeDate : 253392422400000
         * createDate : 1504771434000
         * updateDate : 1504771434000
         * emptyRatio : null
         * dataStatus : null
         * changeBefore : 法定代表人:郎美华
         * changeAfter : 法定代表人:黄建忠
         */

        public String id;
        public String areaId;
        public String businessLicenseId;
        public String ename;
        public String changeItem;
        public long changeDate;
        public long createDate;
        public String updateDate;
        public String emptyRatio;
        public String dataStatus;
        public String changeBefore;
        public String changeAfter;
    }
}
