package com.example.cygraduation.presenters;

import android.widget.Toast;

import com.example.cygraduation.data.XimalayApi;
import com.example.cygraduation.interfaces.ICategoryDetailPresenter;
import com.example.cygraduation.interfaces.ICategoryDetailViewCallBack;
import com.example.cygraduation.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.category.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailPresenter implements ICategoryDetailPresenter {

    private static final String TAG = "CategoryDetailPresenter";

    private Category mTargetCategory = null;

    private List<ICategoryDetailViewCallBack> mCallbacks = new ArrayList<>();

    private CategoryDetailPresenter(){};

    private static CategoryDetailPresenter sInstance = null;

    /**
     *获取单例对象
     *
     * @return
     */
    public static CategoryDetailPresenter getInstance(){
        if (sInstance==null) {
            synchronized (CategoryDetailPresenter.class){
                if(sInstance==null){
                    sInstance = new CategoryDetailPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void getCategoryDetail() {
        LogUtil.d(TAG,"NAME -->  "+mTargetCategory.getId());
        XimalayApi ximalayApi = XimalayApi.getXimalayApi();
        ximalayApi.getCategoryDetail(mTargetCategory.getId(), new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(AlbumList albumList) {
                if(albumList!=null){
                    List<Album> albums = albumList.getAlbums();
                    if (mCallbacks != null) {
                        for (ICategoryDetailViewCallBack callBack : mCallbacks) {
                            callBack.onCategoryDetailLoaded(albums,mTargetCategory.getCategoryName());
                        }
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void registerViewCallback(ICategoryDetailViewCallBack callBack) {
        if(mCallbacks != null&& !mCallbacks.contains(callBack)){
            mCallbacks.add(callBack);
        }
    }

    @Override
    public void unRegisterViewCallback(ICategoryDetailViewCallBack callBack) {
        if (mCallbacks != null) {
            mCallbacks.remove(mCallbacks);
        }
    }

    public void setTargetCategory(Category category){
        this.mTargetCategory = category;
    }
}
