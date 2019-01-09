package com.jusfoun.jusfouninquire.net.model;

/**
 * Created by Albert on 2015/11/18.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:高级搜索选择注册时间model
 */
public class ChooseRegisterTimeItemModel {
    private String value,interval;
    private boolean choosed;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }
}
