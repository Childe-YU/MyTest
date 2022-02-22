package com.example.cygraduation.data;

import com.example.cygraduation.utils.Constants;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.model.word.HotWordList;
import com.ximalaya.ting.android.opensdk.model.word.SuggestWords;

import java.util.HashMap;
import java.util.Map;

import static com.example.cygraduation.utils.Constants.COUNT_CATEGORY;

public class XimalayApi {

    private XimalayApi(){
    }

    private static XimalayApi sXimalayApi;

    public static XimalayApi getXimalayApi(){
        if (sXimalayApi==null) {
            synchronized (XimalayApi.class){
                if (sXimalayApi==null) {
                    sXimalayApi = new XimalayApi();
                }
            }
        }
        return sXimalayApi;
    }

    /**
     * 获取推荐内容
     *
     * @param callBack 请求结果的回调接口
     */
    public void getRecommendList(IDataCallBack<GussLikeAlbumList> callBack){
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.LIKE_COUNT, Constants.COUNT_RECOMMEND+"");
        CommonRequest.getGuessLikeAlbum(map, callBack);
    }

    /**
     * 根据专辑的ID获取到详情专辑内容
     *
     * @param callBack 获取专辑详情的回调接口
     * @param albumId   专辑ID
     * @param pageIndex  第几页
     */
    public void getAlbumDetail(IDataCallBack<TrackList> callBack, long albumId, int pageIndex){
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID, albumId+"");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, pageIndex+"");
        map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT+"");
        CommonRequest.getTracks(map,callBack);
    }

    /**
     * 根据关键字，进行搜索。
     *
     * @param keyword
     */
    public void searchByKeyword(String keyword , int page , IDataCallBack<SearchAlbumList> callBack) {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.SEARCH_KEY, keyword);
        map.put(DTransferConstants.PAGE, page+"");
        map.put(DTransferConstants.PAGE_SIZE,Constants.COUNT_DEFAULT+"");
        CommonRequest.getSearchedAlbums(map,callBack );
    }

    /**
     * 获取推荐的热词
     *
     * @param callback
     */
    public void getHotWords(IDataCallBack<HotWordList> callback){
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.TOP, String.valueOf(Constants.COUNT_HOT_WORD));
        CommonRequest.getHotWords(map, callback);
    }

    /**
     * 根据关键字，获取联想词
     *
     * @param keyword    关键字
     * @param callback   回调
     */
    public void getSuggestWord(String keyword , IDataCallBack<SuggestWords> callback){
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.SEARCH_KEY, keyword);
        CommonRequest.getSuggestWord(map, callback);
    }

    /**
     * 获取分类列表
     *
     * @param callBack
     */
    public void getCategory(IDataCallBack<CategoryList> callBack){
        Map<String, String> map = new HashMap<>();
        CommonRequest.getCategories(map,callBack);
    }

    /**
     * 根据分类获取音频
     *
     * @param id
     * @param callBack
     */
    public void getCategoryDetail(long id,IDataCallBack<AlbumList> callBack){
        Map<String ,String> map = new HashMap<>();
        map.put(DTransferConstants.CATEGORY_ID , id+"");
        map.put(DTransferConstants.CALC_DIMENSION ,"1");
        map.put(DTransferConstants.PAGE_SIZE,COUNT_CATEGORY+"");
        CommonRequest.getAlbumList(map, callBack);
    }
}
