package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:17/9/1117:54
 * @Email zyp@jusfoun.com
 * @Description ${招投标model}
 */
public class RecruitListModel extends BaseModel {
    public List<RecruitItemModel> dataResult;
    public int totalCount;

    public static class RecruitItemModel extends BaseModel {
//        参数(key)	含义
//        job	招聘岗位
//        welfare	招聘福利
//        salary	职位月薪
//        workPlace	工作地点
//        publishDate	发布日期
//        jobPropertity	工作性质
//        jobExperience	工作经验
//        lowEducationBackgroud	最低学历
//        count	招聘人数
//        jobLevel	职位类别
//        jobRequire	工作描述


        public String url;
        public String id;
        public String associatedCompanyId;
        public String job;
        public String welfare;
        public String salary;
        public String workPlace;
        public String publishDate;
        public String jobPropertity;
        public String jobExperience;
        public String lowEducationBackgroud;
        public String count;
        public String jobLevel;
        public String createTime;
        public String updateTime;
        public String jobRequire;
        public String companyName;
        public String scale;
        public String propertity;
        public String industry;
        public String website;
        public String address;
        public String sourceSite;
        public String enterpriseIntroduce;
    }
}

