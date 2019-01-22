package com.gxc.inter;

import java.util.List;

/**
 * @author liuguangdan
 * @version create at 2019/1/22/022 17:02
 * @Description ${}
 */
public interface OnCompleteListener {

    void completeLoadData(List list, int totalCount);

    void completeLoadDataError();

}
