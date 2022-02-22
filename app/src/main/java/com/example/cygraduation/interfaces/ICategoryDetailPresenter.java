package com.example.cygraduation.interfaces;

public interface ICategoryDetailPresenter {
    /**
     * 获取分类详情
     */
    void getCategoryDetail();

    /**
     * 这个方法用于注册UI的回调
     *
     * @param callBack
     */
    void registerViewCallback(ICategoryDetailViewCallBack callBack);

    /**
     * 取消UI的回调注册
     *
     * @param callBack
     */
    void unRegisterViewCallback(ICategoryDetailViewCallBack callBack);
}
