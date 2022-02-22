package com.example.cygraduation.presenters;

import com.example.cygraduation.data.XimalayApi;
import com.example.cygraduation.interfaces.ICategoryPresenter;
import com.example.cygraduation.interfaces.ICategoryViewCallBack;
import com.example.cygraduation.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryPresenter implements ICategoryPresenter {

    private static final String TAG = "CategoryPresenter";

    private List<ICategoryViewCallBack> mCallbacks = new ArrayList<>();

    private CategoryPresenter(){}

    private static CategoryPresenter sInstance = null;

    public static CategoryPresenter getInstance(){
        if(sInstance == null){
            synchronized (CategoryPresenter.class){
                if(sInstance == null){
                    sInstance = new CategoryPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void getCategory() {
        XimalayApi ximalayApi = XimalayApi.getXimalayApi();
        ximalayApi.getCategory(new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(CategoryList categoryList) {
                LogUtil.d(TAG,"categoryList --> size: "+categoryList.getCategories().size());
                if(categoryList != null){
                    List<Category> categories = categoryList.getCategories();
                    if (mCallbacks != null) {
                        for (ICategoryViewCallBack callBack : mCallbacks) {
                            callBack.onCategoryLoaded(categories);
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
    public void registerViewCallback(ICategoryViewCallBack callBack) {
        if(mCallbacks != null&& !mCallbacks.contains(callBack)){
            mCallbacks.add(callBack);
        }
    }

    @Override
    public void unRegisterViewCallback(ICategoryViewCallBack callBack) {
        if (mCallbacks != null) {
            mCallbacks.remove(mCallbacks);
        }
    }
}
