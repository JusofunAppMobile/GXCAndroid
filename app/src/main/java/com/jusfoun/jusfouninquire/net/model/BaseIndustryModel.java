package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author lee
 * @version create time:2015/11/1816:39
 * @Email email
 * @Description $职位 行业 Model
 *
 */

public class BaseIndustryModel extends BaseModel {
    List<IndustryModel> datalist;

    @Override
    public String toString() {
        return "BaseIndustryModel{" +
                "datalist=" + datalist +
                '}';
    }

    public List<IndustryModel> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<IndustryModel> datalist) {
        this.datalist = datalist;
    }
}
