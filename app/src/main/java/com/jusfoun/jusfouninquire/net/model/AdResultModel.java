package com.jusfoun.jusfouninquire.net.model;

import java.io.Serializable;

/**
 * @author zhaoyapeng
 * @version create time:16/3/31上午10:28
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class AdResultModel extends BaseModel implements Serializable {
    public AdModel getDataResult() {
        return dataResult;
    }

    public void setDataResult(AdModel dataResult) {
        this.dataResult = dataResult;
    }

    private AdModel dataResult;
}
