package com.gxc.model;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/808:52
 * @Email zyp@jusfoun.com
 * @Description ${信用报告model}
 */
public class CreditReportModel extends BaseModel {


    public CreditReportItemModel data;


//    {
//    "result": "0",
//            "msg": "成功",
//            "data": {
//        “isVIP”:”1”  //是否是VIP  1 是  0 否
//          “basicVersionDownloadNum”:”10”  //基础版下载剩余数量
//          “basicVersionDownloadAmount”:”10”  //基础版下载多少钱一份
//          “basicVersionSamplePreview”:”www.baidu.com”  //基础版样本预览地址
//          “professionVersionGetReport”:”www.baidu.com”  //基础版获取报告地址
//          “professionVersionDownloadAmount”:”10”  //专业版下载多少钱一份
//          “professionVersionSamplePreview”:”www.baidu.com”  //专业版样本预览地址
//          “professionVersionGetReport”:”www.baidu.com”  //专业版获取报告地址
//    }
//    }


    public static class CreditReportItemModel extends BaseModel {
        public String isVIP;
        public String basicVersionDownloadNum;
        public String basicVersionDownloadAmount;
        public String basicVersionSamplePreview;
        public String professionVersionDownloadAmount;
        public String professionVersionSamplePreview;
        public String professionVersionGetReport;
    }


}
