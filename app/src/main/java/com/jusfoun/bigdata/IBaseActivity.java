package com.jusfoun.bigdata;

/**
 * Activity接口
 */
public interface IBaseActivity {

    /**
     * 初始化数据 在{@link #initView()} 之前
     */
    void initData();

    /**
     * 初始化视图
     */
    void initView();

    /**
     * 设置事件监听 在{@link #initView()}之后
     */
    void setListener();

    /**
     * 业务处理操作 在{@link #setListener()}之后
     */
    void doBusiness();

}