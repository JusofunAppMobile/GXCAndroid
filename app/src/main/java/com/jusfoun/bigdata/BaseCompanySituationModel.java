package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * @author lee
 * @version create time:2016/3/318:10
 * @Email email
 * @Description $公司概况
 */

public class BaseCompanySituationModel extends BaseModel {
    private CompanySituationModel data;
    private int tatol;

    public CompanySituationModel getData() {
        return data;
    }

    public void setData(CompanySituationModel data) {
        this.data = data;
    }

    public int getTatol() {
        return tatol;
    }

    public void setTatol(int tatol) {
        this.tatol = tatol;
    }

    @Override
    public String toString() {
        return "BaseCompanySituationModel{" +
                "data=" + data +
                ", tatol=" + tatol +
                '}';
    }
}
