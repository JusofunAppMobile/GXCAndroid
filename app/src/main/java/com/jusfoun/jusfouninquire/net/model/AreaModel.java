package com.jusfoun.jusfouninquire.net.model;

import java.util.List;

/**
 * Created by Albert on 2015/11/17.
 * Mail : lbh@jusfoun.com
 * TODO :区域model
 */
public class AreaModel {
    private String id,pid,name;
    private List<AreaModel> children;
    private boolean choosed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaModel> getChildren() {
        return children;
    }

    public void setChildren(List<AreaModel> children) {
        this.children = children;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }
}
