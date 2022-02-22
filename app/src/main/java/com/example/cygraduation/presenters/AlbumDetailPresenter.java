package com.example.cygraduation.presenters;

import com.example.cygraduation.data.XimalayApi;
import com.example.cygraduation.interfaces.IAlbumDetailPresenter;
import com.example.cygraduation.interfaces.IAlbumDetailViewCallback;
import com.example.cygraduation.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailPresenter implements IAlbumDetailPresenter {

    private static final String TAG="AlbumDetailPresenter";
    private List<IAlbumDetailViewCallback> mCallbacks = new ArrayList<>();
    private List<Track> mTracks = new ArrayList<>();


    private Album mTargetAlbum = null;
    //当前的专辑ID
    private int mCurrentAlbumId = -1;
    //当前页
    private int mCurrentPageIndex = 0;

    private AlbumDetailPresenter(){
    }

    private static AlbumDetailPresenter sInstance = null;

    public static AlbumDetailPresenter getInstance(){
        if (sInstance==null) {
            synchronized (AlbumDetailPresenter.class){
                if (sInstance==null) {
                    sInstance = new AlbumDetailPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {
        //去加载更多内容
        mCurrentPageIndex++;
        //传入true，表示结果会追加到列表的后方。
        doLoader(true);
    }

    private void doLoader(final boolean isLoaderMore){
        XimalayApi ximalayApi = XimalayApi.getXimalayApi();
        ximalayApi.getAlbumDetail(new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                if (trackList != null) {
                    List<Track> tracks = trackList.getTracks();
                    LogUtil.d(TAG,"tracks size -->  "+tracks.size());
                    if (isLoaderMore) {
                        //上拉加载，结果放到后面去
                        mTracks.addAll(tracks);
                        int size = tracks.size();
                        handlerLoaderMoreResult(size);
                    }else{
                        //这个是下拉加载，结果放到前面去
                        mTracks.addAll(0,tracks);
                    }
                    handlerAlbumDetailResult(mTracks);
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                if (isLoaderMore) {
                    mCurrentPageIndex--;
                }
                LogUtil.d(TAG,"errorCode -->"+errorCode);
                LogUtil.d(TAG,"errorMessage -->"+errorMessage);
                handlerError(errorCode,errorMessage);
            }
        },mCurrentAlbumId,mCurrentPageIndex);
    }

    /**
     * 处理加载更多的结果
     *
     * @param size
     */
    private void handlerLoaderMoreResult(int size) {
        for (IAlbumDetailViewCallback callback : mCallbacks) {
            callback.onLoaderMoreFinished(size);
        }
    }

    @Override
    public void getAlbumDetail(int albumId, int page) {
        mTracks.clear();
        this.mCurrentAlbumId = albumId;
        this.mCurrentPageIndex = page;
        //根据页码和专辑id获取列表
        doLoader(false);
    }

    /**
     * 如果是发生错误，那么通知UI
     *
     * @param errorCode
     * @param errorMessage
     */
    private void handlerError(int errorCode, String errorMessage) {
        for (IAlbumDetailViewCallback callback : mCallbacks) {
            callback.onNetWorkError(errorCode,errorMessage);
        }
    }

    private void handlerAlbumDetailResult(List<Track> tracks) {
        for (IAlbumDetailViewCallback mCallback : mCallbacks) {
            mCallback.onDetailListLoaded(tracks);
        }
    }

    @Override
    public void registerViewCallback(IAlbumDetailViewCallback detailViewCallback) {
        if (!mCallbacks.contains(detailViewCallback)) {
            mCallbacks.add(detailViewCallback);
            if (mTargetAlbum != null) {
                detailViewCallback.onAlbumLoaded(mTargetAlbum);
            }
        }
    }

    @Override
    public void unRegisterViewCallback(IAlbumDetailViewCallback detailViewCallback) {
        mCallbacks.remove(detailViewCallback);
    }

    public void setTargetAlbum(Album targetAlbum) {
        this.mTargetAlbum = targetAlbum;
    }
}
