package com.jusfoun.jusfouninquire.ui.widget.shareholder;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/8/1410:42
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class ShareHolderDataModel  extends BaseModel{

    public String companyname;
    public String companyid;
    public List<ShareHolderItemModel> list;


    public static class ShareHolderItemModel extends BaseModel {
        public String name;
        public String id;
        public List<ShareHolderItemModel> list;
        public boolean isPlaceholder= false;
    }

}


