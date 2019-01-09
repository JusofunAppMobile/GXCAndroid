package com.jusfoun.jusfouninquire.service.event;

import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;
import com.jusfoun.jusfouninquire.net.model.HomeHotBusinessModel;

import java.util.List;

/**
 * Created by Albert on 2015/12/8.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:热门企业列表关注数发生变化，首页重新获取数据成功后发送该event
 */
public class HomeHotChangedEvent implements IEvent {
    List<HomeDataItemModel> list;

    public List<HomeDataItemModel> getList() {
        return list;
    }

    public void setList(List<HomeDataItemModel> list) {
        this.list = list;
    }
}
