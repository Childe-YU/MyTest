package com.example.cygraduation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cygraduation.adapters.CategoryListAdapter;
import com.example.cygraduation.base.BaseActivity;
import com.example.cygraduation.interfaces.ICategoryViewCallBack;
import com.example.cygraduation.presenters.CategoryDetailPresenter;
import com.example.cygraduation.presenters.CategoryPresenter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class CategoryActivity extends BaseActivity implements ICategoryViewCallBack, CategoryListAdapter.OnCategoryItemClickListener {

    private CategoryPresenter mCategoryPresenter;
    private RecyclerView mCategoryRv;
    private CategoryListAdapter mCategoryListAdapter;
    private View mCategoryBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initView();
        initEvent();
        initPresent();
    }

    private void initPresent() {
        mCategoryPresenter = CategoryPresenter.getInstance();
        mCategoryPresenter.registerViewCallback(this);
        mCategoryPresenter.getCategory();
    }

    private void initEvent() {
        mCategoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCategoryListAdapter.setOnCategoryItemClickListener(this);
    }

    private void initView() {
        mCategoryRv = this.findViewById(R.id.category_list);
        TwinklingRefreshLayout twinklingRefreshLayout = this.findViewById(R.id.over_scroll_view);
        twinklingRefreshLayout.setPureScrollModeOn();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mCategoryRv.setLayoutManager(gridLayoutManager);
        mCategoryRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top= UIUtil.dip2px(view.getContext(),5);
                outRect.bottom=UIUtil.dip2px(view.getContext(),5);
                outRect.left=UIUtil.dip2px(view.getContext(),5);
                outRect.right=UIUtil.dip2px(view.getContext(),5);
            }
        });
        mCategoryListAdapter = new CategoryListAdapter();
        mCategoryRv.setAdapter(mCategoryListAdapter);

        mCategoryBack = this.findViewById(R.id.category_back_img);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCategoryPresenter != null){
            mCategoryPresenter.unRegisterViewCallback(this);
        }
    }

    @Override
    public void onCategoryLoaded(List<Category> categoryLists) {
        mCategoryListAdapter.setData(categoryLists);
    }

    @Override
    public void onItemClick(int position, Category category) {
        CategoryDetailPresenter.getInstance().setTargetCategory(category);
        Intent intent = new Intent(this,CategoryDetailActivity.class);
        startActivity(intent);
    }
}
