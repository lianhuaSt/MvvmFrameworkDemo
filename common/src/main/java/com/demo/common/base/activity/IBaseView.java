package com.demo.common.base.activity;

public interface IBaseView {

    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化view之前执行
     */
    void initBefore();
    /**
     * 初始化界面
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();


}
