package com.kx.todaynews.widget.helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.kx.todaynews.adapter.category.CategoryExpandAdapter;
import com.kx.todaynews.listener.OnDragVHListener;
import com.kx.todaynews.listener.OnItemMoveListener;

/**
 * ItemDragHelperCallback
 */
public class ItemDragHelperCallback extends ItemTouchHelper.Callback {

    /**
     *   设置拖拽和滑动方向
     *  makeMovementFlags(dragFlags, swipeFlags);
     *   参数为0 时代表不可拖拽或侧滑
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 如果想支持滑动(删除)操作, swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END
        final int swipeFlags = 0;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            // 其他频道不能拖拽
            if (viewHolder.getItemViewType() == CategoryExpandAdapter.TYPE_OTHER){
                return makeMovementFlags(0, swipeFlags);
            }else {
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
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

        if (recyclerView.getAdapter() instanceof OnItemMoveListener) {
            OnItemMoveListener listener = ((OnItemMoveListener) recyclerView.getAdapter());
            listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // 不在闲置状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof OnDragVHListener) {
                OnDragVHListener itemViewHolder = (OnDragVHListener) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof OnDragVHListener) {
            OnDragVHListener itemViewHolder = (OnDragVHListener) viewHolder;
            itemViewHolder.onItemFinish();
        }
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 不支持长按拖拽功能 手动控制
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        // 不支持滑动功能
        return false;
    }
}
