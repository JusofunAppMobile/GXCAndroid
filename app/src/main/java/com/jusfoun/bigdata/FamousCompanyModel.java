package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * @author lee
 * @version create time:2016/4/1314:25
 * @Email email
 * @Description $ 名企model
 */

public class FamousCompanyModel extends BaseModel{

    private String entname;
    private String url;


    public String getEntname() {
        return entname;
    }

    public void setEntname(String entname) {
        this.entname = entname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FamousCompanyModel{" +
                "entname='" + entname + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
