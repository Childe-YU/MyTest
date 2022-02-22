package com.example.cygraduation.interfaces;

import com.ximalaya.ting.android.opensdk.model.category.Category;

import java.util.List;

public interface ICategoryViewCallBack {
    /**
     * 获取分类结果
     *
     * @param category
     */
    void onCategoryLoaded(List<Category> category);
}
