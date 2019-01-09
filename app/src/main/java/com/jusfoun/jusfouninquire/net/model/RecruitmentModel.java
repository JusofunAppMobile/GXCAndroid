package com.jusfoun.jusfouninquire.net.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1110:19
 * @Email zyp@jusfoun.com
 * @Description ${招聘model}
 */
public class RecruitmentModel extends BaseModel implements Serializable{


    public int totalCount;
    public List<RecruitmentItemModel> dataResult;

    public class RecruitmentItemModel extends BaseModel implements Serializable {
        public String workPlace;
        public String publishDate;
        public String jobExperience;
        public String job;
        public String salary;
        public String companyName;
        public String  associatedCompanyId;
        public String url;
        public String id;
        public String jobPropertity;
        public String lowEducationBackgroud;
        public String count;
        public String jobLevel;
        public String createTime;
        public String updateTime;
        public String jobRequire;
        public String scale;
        public String propertity;
        public String industry;
        public String website;
        public String address;
        public String enterpriseIntroduce;
        public String sourceSite;
        public String welfare;






    }

}


