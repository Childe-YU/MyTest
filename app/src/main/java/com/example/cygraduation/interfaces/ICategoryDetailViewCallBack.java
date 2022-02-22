package com.example.cygraduation.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.category.Category;

import java.util.List;

public interface ICategoryDetailViewCallBack {
    /**
     * 搜索结果
     *
     * @param albums
     * @param name
     */
    void onCategoryDetailLoaded(List<Album> albums,String name);
}
