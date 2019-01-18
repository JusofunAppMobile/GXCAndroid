package com.gxc.model;

import com.gxc.retrofit.NetModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1810:24
 * @Email zyp@jusfoun.com
 * @Description ${国信查 详情model}
 */
public class CorporateInfoModel extends NetModel implements Serializable {


    public CompanyInfo companyInfo;

    public static class CompanyInfo {

        public String monitorType;//是否监控 0未监控 1已监控
        public String creditScore;// 信用分
        public String isCollect;//是否收藏 0未收藏 1收藏
        public String ownRisk;//自身风险
        public String relateRisk;//相关风险
        public String AtlasH5Address; //企业图谱跳转地址
        public String RiskH5Address; //企业风险分析—关联风险(H5)跳转地址
        public String CorrelationH5Address;//关联关系跳转地址
        public String InfoH5Address;//企业信息-更多信息H5跳转地址
        public String OwnershipStructureH5Address;//股权结构跳转地址

        public List<ShareholderItemModel> shareholder;
        public List<MainStaffItemModel> mainStaff;
    }

    public static class ShareholderItemModel {
        public String name;//股动名字
        public String holdRatio;//持股比例
        public String strongHolder;//是否大股东  1 是  0 否
    }


    public static class MainStaffItemModel {
        public String name;//股动名字
        public String job;//持股比例
    }

}
