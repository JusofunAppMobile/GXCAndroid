package com.jusfoun.jusfouninquire.net.model;

/**
 * @author lee
 * @version create time:2015/11/1211:25
 * @Email email
 * @Description $检查更新
 */

public class CheckVersionModel extends BaseModel {

    private VersionModel versionnumber;

    public VersionModel getVersionnumber() {
        return versionnumber;
    }

    public void setVersionnumber(VersionModel versionnumber) {
        this.versionnumber = versionnumber;
    }

    @Override
    public String toString() {
        return "CheckVersionModel{" +
                "versionnumber=" + versionnumber +
                '}';
    }
}
