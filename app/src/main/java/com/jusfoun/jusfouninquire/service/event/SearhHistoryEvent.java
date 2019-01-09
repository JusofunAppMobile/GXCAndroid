package com.jusfoun.jusfouninquire.service.event;

/**
 * Created by Albert on 2015/12/3.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description：点击搜索记录发送此event，不再发送SearchEvent，避免由于设置编辑框文字导致死循环
 */
public class SearhHistoryEvent implements IEvent{
    private String mSearchKey,mSearchScopeID,mSearchScopeName;

    public String getmSearchKey() {
        return mSearchKey;
    }

    public void setmSearchKey(String mSearchKey) {
        this.mSearchKey = mSearchKey;
    }

    public String getmSearchScopeID() {
        return mSearchScopeID;
    }

    public void setmSearchScopeID(String mSearchScopeID) {
        this.mSearchScopeID = mSearchScopeID;
    }

    public String getmSearchScopeName() {
        return mSearchScopeName;
    }

    public void setmSearchScopeName(String mSearchScopeName) {
        this.mSearchScopeName = mSearchScopeName;
    }
}
