package com.example.cygraduation.adapters;

import com.example.cygraduation.R;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.InnerHolder> {

    private static final String TAG="AlbumListAdapter";
    private List<Album> mData = new ArrayList<>();
    private OnAlbumItemClickListener mItemClickListen = null;
    private OnAlbumItemLongClickListener mLongClickListener = null;

    @NonNull
    @Override
    public AlbumListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //这里是载view
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);
        return new InnerHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListAdapter.InnerHolder holder, int position) {
        //这里是设置数据
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListen!=null) {
                    int clickPosition = (int) v.getTag();
                    mItemClickListen.onItemClick(clickPosition,mData.get(clickPosition));
                }
                Log.d(TAG,"holder.itemView was click -->"+v.getTag());
            }
        });
        holder.setData(mData.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongClickListener != null) {
                    int clickPosition = (int) v.getTag();
                    mLongClickListener.onItemLongClick(mData.get(clickPosition));
                }
                //true 表示消费掉该事件
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //返回要显示的个数
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if (mData != null) {
            mData.clear();
            mData.addAll(albumList);
        }
        //更新一下UI
        notifyDataSetChanged();
    }


    public class InnerHolder extends RecyclerView.ViewHolder{
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album){
            //找到各个控件，设置数据
            //专辑封面
            ImageView albumCoverIv=itemView.findViewById(R.id.album_cover);
            //标题
            TextView albumTitleTv=itemView.findViewById(R.id.album_title_tv);
            //描述
            TextView albumDesTv=itemView.findViewById(R.id.album_description_tv);
            //播放数量
            TextView albumPlayCountTv=itemView.findViewById(R.id.album_play_count);
            //专辑数量
            TextView albumContentCountTv=itemView.findViewById(R.id.album_content_size);

            albumTitleTv.setText(album.getAlbumTitle());
            albumDesTv.setText(album.getAlbumIntro());
            albumPlayCountTv.setText(album.getPlayCount()+"");
            albumContentCountTv.setText(album.getIncludeTrackCount()+"");

            String coverUrlLarge = album.getCoverUrlLarge();
            if (!TextUtils.isEmpty(coverUrlLarge)) {
                Picasso.with(itemView.getContext()).load(coverUrlLarge).into(albumCoverIv);
            }else{
                albumCoverIv.setImageResource(R.mipmap.ximalay_logo);
            }
        }
    }

    public void setAlbumItemClickListener(OnAlbumItemClickListener listener){
        this.mItemClickListen = listener;
    }

    public interface OnAlbumItemClickListener {
        void onItemClick(int position,Album album);
    }

    public void setOnAlbumItemLongClickListener(OnAlbumItemLongClickListener listener){
        this.mLongClickListener = listener;
    }

    /**
     * item长按的接口
     */
    public interface OnAlbumItemLongClickListener {
        void onItemLongClick(Album album);
    }
}
