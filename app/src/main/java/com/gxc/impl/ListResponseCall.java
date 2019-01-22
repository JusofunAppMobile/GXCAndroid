package com.gxc.impl;

import com.gxc.inter.OnCompleteListener;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author liuguangdan
 * @version create at 2019/1/22/022 17:00
 * @Description ${}
 */
public abstract class ListResponseCall extends ResponseCall {

    private OnCompleteListener listener;

    public abstract List getList(NetModel model);

    public ListResponseCall(OnCompleteListener listener) {
        this.listener = listener;
        if (listener == null)
            throw new RuntimeException("call listener can not be null.");
    }

    @Override
    public void success(NetModel model) {
        if (model.success())
            listener.completeLoadData(getList(model), getTotalCount(model));
        else
            listener.completeLoadDataError();
    }

    private int getTotalCount(NetModel model) {
        JSONObject obj = model.getDataJSONObject();
        if (obj.has(getTotalCountKey())) {
            try {
                return obj.getInt(getTotalCountKey());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 如果获取key不同，需重写该方法
     *
     * @return
     */
    public String getTotalCountKey() {
        return "totalCount";
    }

    @Override
    public void error() {
        listener.completeLoadDataError();
    }
}
