package com.jusfoun.jusfouninquire.net.model;

/**
 * @author zhaoyapeng
 * @version create time:17/10/2609:46
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class ReporData extends BaseModel {
    private String vipType;
    private String reportUrl;

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }
}
