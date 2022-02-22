package com.example.cygraduation.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cygraduation.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.category.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.InnerHolder> {

    private List<Category> mData = new ArrayList<>();
    private OnCategoryItemClickListener mItemClickListen = null;

    @NonNull
    @Override
    public CategoryListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListen!=null){
                    int clickPosition = (int)v.getTag();
                    mItemClickListen.onItemClick(clickPosition,mData.get(clickPosition));
                }
            }
        });
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Category> categoryList){
        if(mData !=null){
            mData.clear();
            mData.addAll(categoryList);
        }
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Category category) {
            //分类图片
            ImageView categoryCover = itemView.findViewById(R.id.category_cover);
            //分类名
            TextView categoryName = itemView.findViewById(R.id.category_name);

            categoryName.setText(category.getCategoryName());

            String coverUrlLarge = category.getCoverUrlLarge();
            if (!TextUtils.isEmpty(coverUrlLarge)) {
                Picasso.with(itemView.getContext()).load(coverUrlLarge).into(categoryCover);
            }else{
                //categoryCover.setImageResource(R.mipmap.ximalay_logo);
            }
        }
    }

    public void setOnCategoryItemClickListener(OnCategoryItemClickListener listener){
        this.mItemClickListen = listener;
    }

    /**
     * 分类点击事件
     */
    public interface OnCategoryItemClickListener{
        void onItemClick(int position,Category category);
    }
}
