package com.jusfoun.jusfouninquire.service.event;

import com.jusfoun.jusfouninquire.net.model.HomeVersionModel;

/**
 * Created by Albert on 2016/1/12.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:需要更新设置、个人中心显示时，发送此event，携带显示数据
 */
public class UpdateMainEvent implements IEvent {
    private HomeVersionModel model;

    public HomeVersionModel getModel() {
        return model;
    }

    public void setModel(HomeVersionModel model) {
        this.model = model;
    }
}
