package com.example.cygraduation.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cygraduation.R;
import com.example.cygraduation.utils.LogUtil;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class PlayerTrackPagerAdapter extends PagerAdapter {

    private static final String TAG = "PlayerTrackPagerAdapter";

    private List<Track> mData = new ArrayList<>();

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View  itemview = LayoutInflater.from(container.getContext()).inflate(R.layout.item_track_pager, (ViewGroup) container,false);
        container.addView(itemview);

        //设置数据

        //找到控件
        ImageView item = itemview.findViewById(R.id.track_pager_item);
        //设置图片
        Track track = mData.get(position);
        String coverUriLarger = track.getCoverUrlLarge();
        //LogUtil.d(TAG,"coverUriLarger  -->  " + coverUriLarger);
        if(!TextUtils.isEmpty(coverUriLarger)){
            Picasso.with(container.getContext()).load(coverUriLarger).into(item);
        }else{
            item.setImageResource(R.mipmap.logo);
        }
        return itemview;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    public void setData(List<Track> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }
}
