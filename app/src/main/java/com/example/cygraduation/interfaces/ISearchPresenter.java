package com.example.cygraduation.interfaces;

import com.example.cygraduation.base.IBasePresenter;

public interface ISearchPresenter extends IBasePresenter<ISearchCallback> {

    /**
     * 进行搜索
     *
     * @param keyword
     */
    void doSearch(String keyword);

    /**
     * 重新搜索
     */
    void reSearch();

    /**
     * 加载更多搜索结果
     */
    void loaderMore();

    /**
     * 获取热词
     */
    void getHotWord();

    /**
     * 获取推荐的关键字（相关关键字）
     *
     * @param keyword
     */
    void getRecommendWord(String keyword);
}
