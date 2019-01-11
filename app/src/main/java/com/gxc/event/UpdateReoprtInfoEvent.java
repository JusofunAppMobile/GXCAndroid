package com.gxc.event;

import com.gxc.model.EditReportInfoImgModel;
import com.gxc.model.EditReportInfoTextModel;
import com.jusfoun.jusfouninquire.service.event.IEvent;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1016:41
 * @Email zyp@jusfoun.com
 * @Description ${自主填报信息 更新event}
 */
public class UpdateReoprtInfoEvent implements IEvent {
    public int type;

    public EditReportInfoTextModel editReportInfoTextModel;
    public EditReportInfoImgModel editReportInfoImgModel;
}
