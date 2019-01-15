package com.gxc.model;

import com.gxc.retrofit.NetModel;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1516:45
 * @Email zyp@jusfoun.com
 * @Description ${信用报告model}
 */
public class CreditDataModel extends NetModel {


    public static class CompanyInfo extends NetModel {
        public String companyName;
        public String code;
        public String type;
        public String companyId;
        public String status;//认证状态  0：未认证 1：审核中 2：审核失败 3：审核成功
        public String changeNum;
    }


    public CompanyInfo companyInfo;
    public List<HomeMenuModel> serviceList;
    public List<HomeMenuModel> inquiryList;




}
