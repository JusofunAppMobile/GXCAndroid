package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * @author lee
 * @version create time:2015/11/1210:15
 * @Email email
 * @Description $推荐应用
 */

public class AppRecommendModel extends BaseModel{
    private List<AppModel> apprecommenlist;

    @Override
    public String toString() {
        return "AppRecommendModel{" +
                "apprecommenlist=" + apprecommenlist +
                '}';
    }

    public List<AppModel> getApprecommenlist() {
        return apprecommenlist;
    }

    public void setApprecommenlist(List<AppModel> apprecommenlist) {
        this.apprecommenlist = apprecommenlist;
    }
}
