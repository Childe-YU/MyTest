package com.example.cygraduation.interfaces;

public interface ICategoryPresenter {
    /**
     * 获取分类
     */
    void getCategory();

    /**
     * 这个方法用于注册UI的回调
     *
     * @param callBack
     */
    void registerViewCallback(ICategoryViewCallBack callBack);

    /**
     * 取消UI的回调注册
     *
     * @param callBack
     */
    void unRegisterViewCallback(ICategoryViewCallBack callBack);
}
