package com.example.cygraduation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cygraduation.adapters.AlbumListAdapter;
import com.example.cygraduation.base.BaseActivity;
import com.example.cygraduation.interfaces.ICategoryDetailViewCallBack;
import com.example.cygraduation.presenters.AlbumDetailPresenter;
import com.example.cygraduation.presenters.CategoryDetailPresenter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class CategoryDetailActivity extends BaseActivity implements ICategoryDetailViewCallBack, AlbumListAdapter.OnAlbumItemClickListener {

    private CategoryDetailPresenter mCategoryDetailPresenter;
    private TextView mCategoryDetailTv;
    private RecyclerView mCategoryDetailRv;
    private AlbumListAdapter mAlbumListAdapter;
    private View mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        initView();
        initEvent();
        initPresenter();
    }

    private void initEvent() {
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAlbumListAdapter.setAlbumItemClickListener(this);
    }

    private void initPresenter() {
        mCategoryDetailPresenter = CategoryDetailPresenter.getInstance();
        mCategoryDetailPresenter.registerViewCallback(this);
        mCategoryDetailPresenter.getCategoryDetail();
    }

    private void initView() {
        mCategoryDetailTv = this.findViewById(R.id.category_detail_tv);
        mCategoryDetailRv = this.findViewById(R.id.category_detail_list);
        TwinklingRefreshLayout twinklingRefreshLayout = this.findViewById(R.id.over_scroll_view);
        twinklingRefreshLayout.setPureScrollModeOn();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCategoryDetailRv.setLayoutManager(linearLayoutManager);
        mCategoryDetailRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top= UIUtil.dip2px(view.getContext(),5);
                outRect.bottom=UIUtil.dip2px(view.getContext(),5);
                outRect.left=UIUtil.dip2px(view.getContext(),5);
                outRect.right=UIUtil.dip2px(view.getContext(),5);
            }
        });
        mAlbumListAdapter = new AlbumListAdapter();
        mCategoryDetailRv.setAdapter(mAlbumListAdapter);

        mBackBtn = this.findViewById(R.id.category_detail_back_img);
    }

    @Override
    public void onCategoryDetailLoaded(List<Album> albums,String name) {
        mCategoryDetailTv.setText(name);
        mAlbumListAdapter.setData(albums);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCategoryDetailPresenter != null){
            mCategoryDetailPresenter.unRegisterViewCallback(this);
        }
    }

    @Override
    public void onItemClick(int position, Album album) {
        AlbumDetailPresenter.getInstance().setTargetAlbum(album);
        //item被点击,跳转到详情界面
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }
}
