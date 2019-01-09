package com.jusfoun.jusfouninquire.service.event;

/**
 * @author zhaoyapeng
 * @version create time:15/12/10下午3:59
 * @Email zyp@jusfoun.com
 * @Description ${更新 关注数event}
 */
public class UpdateAttentionEvent implements IEvent {

    public String attention;

    private String companyId;
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }



    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }


}
