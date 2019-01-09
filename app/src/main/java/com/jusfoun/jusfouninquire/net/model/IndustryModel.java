package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author lee
 * @version create time:2015/11/1015:08
 * @Email email
 * @Description 行业职位 model
 */

public class IndustryModel extends BaseModel {
    private String type;
    private List<JobModel> rlist;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<JobModel> getRlist() {
        return rlist;
    }

    public void setRlist(List<JobModel> rlist) {
        this.rlist = rlist;
    }

    @Override
    public String toString() {
        return "IndustryModel{" +
                "joblist=" + rlist +
                '}';
    }
}
