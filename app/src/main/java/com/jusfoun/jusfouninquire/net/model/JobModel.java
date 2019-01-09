package com.jusfoun.jusfouninquire.net.model;

/**
 * @author lee
 * @version create time:2015/11/1015:09
 * @Email email
 * @Description 行业、职位 item model
 */

public class JobModel  extends BaseModel{
    private String childid,name,parentid,haschild,level;

    public String getChildid() {
        return childid;
    }

    public void setChildid(String childid) {
        this.childid = childid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getHaschild() {
        return haschild;
    }

    public void setHaschild(String haschild) {
        this.haschild = haschild;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "JobModel{" +
                ", childid='" + childid + '\'' +
                ", name='" + name + '\'' +
                ", parentid='" + parentid + '\'' +
                ", haschild='" + haschild + '\'' +
                '}';
    }


}
