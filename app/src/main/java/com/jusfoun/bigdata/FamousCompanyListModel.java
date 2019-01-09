package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

import java.util.List;

/**
 * @author lee
 * @version create time:2016/4/1314:23
 * @Email email
 * @Description $名企model
 */

public class FamousCompanyListModel extends BaseModel {

    private List<FamousCompanyModel> data;
    private String total;

    public List<FamousCompanyModel> getData() {
        return data;
    }

    public void setData(List<FamousCompanyModel> data) {
        this.data = data;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "FamousCompanyListModel{" +
                "data=" + data +
                ", total='" + total + '\'' +
                '}';
    }
}
