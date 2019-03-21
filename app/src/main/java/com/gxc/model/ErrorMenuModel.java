package com.gxc.model;

import com.gxc.retrofit.NetModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailMenuModel;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/2110:04
 * @Email zyp@jusfoun.com
 * @Description ${纠错异议model}
 */
public class ErrorMenuModel extends NetModel {

    public List<ObjectionItem> objectionList;

    public static class ObjectionItem {
        public List<CompanyDetailMenuModel> menuList;
        public String typeName;
        public String id;
    }
}
