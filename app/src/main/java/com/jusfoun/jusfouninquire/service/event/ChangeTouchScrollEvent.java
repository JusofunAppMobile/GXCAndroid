package com.jusfoun.jusfouninquire.service.event;

/**
 * Created by Albert on 2015/12/3.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description：搜索页面，当区域列表显示时，禁止viewpager滑动
 */
public class ChangeTouchScrollEvent implements IEvent{
    private boolean mScrollable;

    public boolean ismScrollable() {
        return mScrollable;
    }

    public void setmScrollable(boolean mScrollable) {
        this.mScrollable = mScrollable;
    }
}
