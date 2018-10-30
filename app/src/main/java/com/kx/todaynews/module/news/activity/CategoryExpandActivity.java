package com.kx.todaynews.module.news.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import com.kx.todaynews.R;
import com.kx.todaynews.adapter.category.CategoryExpandAdapter;
import com.kx.todaynews.base.BaseActivity;
import com.kx.todaynews.bean.ArticleCategory;
import com.kx.todaynews.contract.IArticleCategoryContract;
import com.kx.todaynews.presenter.ArticleCategoryPresenter;
import com.kx.todaynews.widget.helper.ItemDragHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 频道管理界面
 */
public class CategoryExpandActivity extends BaseActivity<ArticleCategoryPresenter> implements IArticleCategoryContract.IArticleCategoryView {

    @BindView(R.id.categoryRecyclerView)
    RecyclerView categoryRecyclerView;
    private CategoryExpandAdapter mCategoryExpandAdapter;


    @Override
    protected ArticleCategoryPresenter createPresenter() {
        return new ArticleCategoryPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category_expand;
    }

    @Override
    protected void initEventAndData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        ItemDragHelperCallback touchHelper =  new ItemDragHelperCallback();
        //通过ItemTouchHelper将rv和callback关联起来
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelper);
        itemTouchHelper.attachToRecyclerView(categoryRecyclerView);
        mCategoryExpandAdapter = new CategoryExpandAdapter(this,itemTouchHelper);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = mCategoryExpandAdapter.getItemViewType(position);
                return viewType == CategoryExpandAdapter.TYPE_MY || viewType == CategoryExpandAdapter.TYPE_OTHER ? 1 : 4;
            }
        });

        mPresenter.getArticleCategory();
    }

    @Override
    public void showArticleCategory(ArticleCategory articleCategory) {
        List<ArticleCategory.DataBeanX.DataBean> otherData = articleCategory.getData().getData();
        List<ArticleCategory.DataBeanX.DataBean> myData = new ArrayList<>();
        myData.add(new ArticleCategory.DataBeanX.DataBean("热点"));
        mCategoryExpandAdapter.setCategoryData(myData,otherData);
        categoryRecyclerView.setAdapter(mCategoryExpandAdapter);
    }


    @Override
    protected void initToolbar() {

    }
    class MyItemTouchHelper extends ItemTouchHelper.Callback{
        private CategoryExpandAdapter mAdapter;

        public MyItemTouchHelper(CategoryExpandAdapter adapter) {
            mAdapter = adapter;
        }

        /**
         *   设置拖拽和滑动方向
         *  makeMovementFlags(dragFlags, swipeFlags);
         *   参数为0 时代表不可拖拽或侧滑
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                // 其他频道不能拖拽
                if (viewHolder.getItemViewType() == CategoryExpandAdapter.TYPE_OTHER){
                    return makeMovementFlags(0, swipeFlags);
                }else {
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        /**
         *  拖拽时回调的方法
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // 不同Type之间不可移动
            if (viewHolder.getItemViewType() != target.getItemViewType()) {
                return false;
            }
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            // 向后拖拽
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mAdapter.getMyChannelItems(), i -1, i);
                }
            } else {
                // 向前拖拽
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mAdapter.getMyChannelItems(), i-2, i-1);
                }
            }
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        /**
         *   侧滑时回调的方法。
         */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
        /**
         * item 被选中时
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            // 不在闲置状态
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                if (viewHolder.getItemViewType() == CategoryExpandAdapter.TYPE_MY) {
                    TextView tv = viewHolder.itemView.findViewById(R.id.tv);
                    tv.setBackgroundResource(R.drawable.bg_channel_p);
                }
            }
            super.onSelectedChanged(viewHolder, actionState);
        }
        /**
         * item 取消选中时
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == CategoryExpandAdapter.TYPE_MY) {
                TextView tv = viewHolder.itemView.findViewById(R.id.tv);
                tv.setBackgroundResource(R.drawable.bg_channel_n);
            }
            super.clearView(recyclerView, viewHolder);
        }
    }
}
