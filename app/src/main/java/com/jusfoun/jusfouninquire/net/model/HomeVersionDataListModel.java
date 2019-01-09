package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by Albert on 2016/1/14.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:设置、个人中心数据list model
 */
public class HomeVersionDataListModel extends BaseModel {
    private List<HomeVersionModel> content;

    public List<HomeVersionModel> getContent() {
        return content;
    }

    public void setContent(List<HomeVersionModel> content) {
        this.content = content;
    }
}
