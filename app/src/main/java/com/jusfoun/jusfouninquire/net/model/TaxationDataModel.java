package com.jusfoun.jusfouninquire.net.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/1/916:39
 * @Email zyp@jusfoun.com
 * @Description ${税号 data}
 */
public class TaxationDataModel extends BaseModel implements Serializable{
    public int totalCount;
    public List<TaxationItemModel>dataResult;


}
