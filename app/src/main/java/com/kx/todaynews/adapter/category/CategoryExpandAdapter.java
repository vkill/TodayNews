package com.kx.todaynews.adapter.category;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.ArticleCategory;
import com.kx.todaynews.listener.OnDragVHListener;
import com.kx.todaynews.listener.OnItemMoveListener;
import com.kx.todaynews.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2018/10/27.
 */
public class CategoryExpandAdapter extends RecyclerView.Adapter<BaseViewHolder> implements OnItemMoveListener{
    // 我的频道 标题部分
    public static final int TYPE_MY_CHANNEL_HEADER = 0;
    // 我的频道
    public static final int TYPE_MY = 1;
    // 其他频道 标题部分
    public static final int TYPE_OTHER_CHANNEL_HEADER = 2;
    // 其他频道
    public static final int TYPE_OTHER = 3;
    // 我的频道之前的header数量  该demo中 即标题部分 为 1
    private static final int COUNT_PRE_MY_HEADER = 1;
    // 其他频道之前的header数量  该demo中 即标题部分 为 COUNT_PRE_MY_HEADER + 1
    private static final int COUNT_PRE_OTHER_HEADER = COUNT_PRE_MY_HEADER + 1;

    private List<ArticleCategory.DataBeanX.DataBean> mMyChannelItems = new ArrayList<>();
    private List<ArticleCategory.DataBeanX.DataBean> mOtherChannelItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater ;
    private boolean isEditMode = false;
    private ItemTouchHelper mItemTouchHelper ;
    private Handler mHandler = new Handler();
    private int clickPosition;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            notifyItemMoved(clickPosition, mMyChannelItems.size() - 1 + 1);
        }
    };
    // touch 点击开始时间
    private long startTime;
    // touch 间隔时间  用于分辨是否是 "点击"
    private static final long SPACE_TIME = 100;

    public CategoryExpandAdapter(Context context, ItemTouchHelper itemTouchHelper) {
        mLayoutInflater = LayoutInflater.from(context);
        mItemTouchHelper = itemTouchHelper;
    }

    public void setCategoryData(List<ArticleCategory.DataBeanX.DataBean> myData, List<ArticleCategory.DataBeanX.DataBean> otherData) {
        this.mMyChannelItems = myData;
        this.mOtherChannelItems = otherData;
    }

    public List<ArticleCategory.DataBeanX.DataBean> getMyChannelItems() {
        return mMyChannelItems;
    }

    @Override
    public int getItemCount() {
        // 我的频道  标题 + 我的频道.size + 其他频道 标题 + 其他频道.size
        return mMyChannelItems.size() + mOtherChannelItems.size() + 2;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {    // 我的频道 标题部分
            return TYPE_MY_CHANNEL_HEADER;
        } else if (position == mMyChannelItems.size() + 1) {    // 其他频道 标题部分
            return TYPE_OTHER_CHANNEL_HEADER;
        } else if (position > 0 && position < mMyChannelItems.size() + 1) {
            return TYPE_MY;
        } else {
            return TYPE_OTHER;
        }
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder =null;
        View view = null;
        if (viewType == TYPE_MY_CHANNEL_HEADER){
            view = mLayoutInflater.inflate(R.layout.item_my_channel_header, parent, false);
            TextView tv_btn_edit = view.findViewById(R.id.tv_btn_edit);
            view.findViewById(R.id.tv_btn_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEditMode) {
                        setEditMode((RecyclerView) parent);
                        tv_btn_edit.setText(R.string.finish);
                    } else {
                        setEditMode((RecyclerView) parent);
                        tv_btn_edit.setText(R.string.edit);
                    }
                }
            });
            baseViewHolder = new BaseViewHolder(view);
        }else if (viewType == TYPE_OTHER_CHANNEL_HEADER){
            view = mLayoutInflater.inflate(R.layout.item_other_channel_header, parent, false);
            baseViewHolder = new BaseViewHolder(view);
        }else if (viewType == TYPE_MY){
            view = mLayoutInflater.inflate(R.layout.item_my, parent, false);
            baseViewHolder = new MyViewHolder(view);
            TextView tv = view.findViewById(R.id.tv);
            BaseViewHolder finalBaseViewHolder1 = baseViewHolder;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEditMode){
                        // 获取点击item在adapter中的position
                        int position = finalBaseViewHolder1.getAdapterPosition();
                        // 获取点击item在我的频道中的position
                        int clickPosition = position - 1;
                        ArticleCategory.DataBeanX.DataBean item = mMyChannelItems.get(clickPosition);
                        mMyChannelItems.remove(clickPosition);
                        mOtherChannelItems.add(0,item);
                        notifyItemMoved(position, mMyChannelItems.size()+ 2);

                    }else {
                        ToastUtils.showToast(tv.getText().toString());
                    }
                }
            });
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    if (!isEditMode) {
                        RecyclerView recyclerView = ((RecyclerView) parent);
                        setEditMode(recyclerView);
                        // header 按钮文字 改成 "完成"
                        View view = recyclerView.getChildAt(0);
                        if (view == recyclerView.getLayoutManager().findViewByPosition(0)) {
                            TextView tvBtnEdit =  view.findViewById(R.id.tv_btn_edit);
                            tvBtnEdit.setText(R.string.finish);
                        }
                    }
                    mItemTouchHelper.startDrag(finalBaseViewHolder1);
                    return true;
                }
            });
//            tv.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (isEditMode) {
//                        switch (MotionEventCompat.getActionMasked(event)) {
//                            case MotionEvent.ACTION_DOWN:
//                                startTime = System.currentTimeMillis();
//                                break;
//                            case MotionEvent.ACTION_MOVE:
//                                if (System.currentTimeMillis() - startTime > SPACE_TIME) {
//                                    mItemTouchHelper.startDrag(finalBaseViewHolder1);
//                                }
//                                break;
//                            case MotionEvent.ACTION_CANCEL:
//                            case MotionEvent.ACTION_UP:
//                                startTime = 0;
//                                break;
//                        }
//                    }
//                    return false;
//                }
//            });
        }else if (viewType == TYPE_OTHER){
            view = mLayoutInflater.inflate(R.layout.item_other, parent, false);
            baseViewHolder = new BaseViewHolder(view);
            TextView tv = view.findViewById(R.id.tv);
            // 点击其他频道item.
            BaseViewHolder finalBaseViewHolder = baseViewHolder;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RecyclerView recyclerView = ((RecyclerView) parent);
                    ViewGroup rootParent = (ViewGroup) recyclerView.getParent();
                    GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int currentPosition = finalBaseViewHolder.getAdapterPosition();
                    // 如果RecyclerView滑动到底部,移动的目标位置的y轴 - height
                    View currentView = manager.findViewByPosition(currentPosition);

                    float startX = currentView.getLeft();
                    float startY = currentView.getTop();

                    final View endView;
                    float toX, toY;
                   // int endLoc[] = new int[2];
                    //进行判断
                    int i = mMyChannelItems.size();
                    //  我的频道暂无数据
                    if (i == 0) {
                        toX = view.getWidth();
                        toY = view.getHeight();
                    // 我的频道数据为4的整数，就移动到下一行。
                    } else if (i % 4 == 0) {
                        endView = manager.findViewByPosition(i-3);
                       // endView.getLocationInWindow(endLoc);
                        toX = endView.getLeft() ;
                        toY = endView.getTop() + endView.getHeight();
                    } else {
                        // 添加在后面
                        endView = manager.findViewByPosition(i);
                       // endView.getLocationInWindow(endLoc);
                        toX = endView.getLeft()+ endView.getWidth();
                        toY = endView.getTop();
                        // 最后一个item可见   recyclerView 滑动到底部了,
                        if (manager.findLastVisibleItemPosition() == getItemCount() - 1) {
                            int spanCount= manager.getSpanCount();
                            // 最后的item在最后一行第一个位置
                            if ((getItemCount() - 1 - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % spanCount == 0) {
                                // RecyclerView实际高度 > 屏幕高度 && RecyclerView实际高度 < 屏幕高度 + item.height
                                int firstVisiblePostion = manager.findFirstVisibleItemPosition();
                                if (firstVisiblePostion == 0) {
                                    // FirstCompletelyVisibleItemPosition == 0 即 内容不满一屏幕 , targetY值不需要变化
                                    // // FirstCompletelyVisibleItemPosition != 0 即 内容满一屏幕 并且 可滑动 , targetY值 + firstItem.getTop
                                    if (manager.findFirstCompletelyVisibleItemPosition() != 0) {
                                        int offset = (-recyclerView.getChildAt(0).getTop()) - recyclerView.getPaddingTop();
                                        toY += offset;
                                    }
                                } else { // 在这种情况下 并且 RecyclerView高度变化时(即可见第一个item的 position != 0),
                                    // 移动后, targetY值  + 一个item的高度
                                    //  此时点击item刷新数据recyclerView会向下滑动一定的距离,targetY要重新计算
                                    toY += currentView.getHeight();
                                }
                            }
                        }
                    }
                  //  startAnimation(rootParent,currentView, startX, startY, toX, toY);
                    int targetPosition = mMyChannelItems.size() - 1 + COUNT_PRE_OTHER_HEADER;
                    // 则 需要延迟250秒 notifyItemMove , 这是因为这种情况 , 并不触发ItemAnimator , 会直接刷新界面
                    // 导致我们的位移动画刚开始,就已经notify完毕,引起不同步问题
                    if (currentPosition == manager.findLastVisibleItemPosition()
                            && (currentPosition - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % manager.getSpanCount() != 0
                            && (targetPosition - COUNT_PRE_MY_HEADER) % manager.getSpanCount() != 0) {
                        moveOtherToMyWithDelay(finalBaseViewHolder);
                    } else {
                        moveOtherToMy(finalBaseViewHolder);
                    }
                }
            });

        }
        return baseViewHolder;
    }

//    private void startAnimation(ViewGroup viewParent, float startX, float startY, float toX, float toY) {
//        PathMeasure mPathMeasure;
//        ImageView mirrorImageView = addMirrorView(rootParent, recyclerView, currentView);
//        final float[] mCurrentPosition = new float[2];
//        Path path = new Path();
//        path.moveTo(startX, startY);
//        path.lineTo(toX, toY);
//        mPathMeasure = new PathMeasure(path, false);
//        //属性动画实现
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
//        valueAnimator.setDuration(330);
//        // 匀速插值器
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (Float) animation.getAnimatedValue();
//                // 获取当前点坐标封装到mCurrentPosition
//                mPathMeasure.getPosTan(value, mCurrentPosition, null);
//                mirrorImageView.setTranslationX(mCurrentPosition[0]);
//                mirrorImageView.setTranslationY(mCurrentPosition[1]);
//            }
//        });
//        valueAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                viewParent.removeView(mirrorImageView);
//            }
//        });
//        valueAnimator.start();
//    }

    private void moveOtherToMyWithDelay(BaseViewHolder finalBaseViewHolder) {

        clickPosition = finalBaseViewHolder.getAdapterPosition();

        int startPosition = clickPosition - mMyChannelItems.size() - 2;

        ArticleCategory.DataBeanX.DataBean item = mOtherChannelItems.get(startPosition);

        mOtherChannelItems.remove(startPosition);
        mMyChannelItems.add(item);
        mHandler.postDelayed(mRunnable,350);

    }

    private void moveOtherToMy(BaseViewHolder finalBaseViewHolder) {

        clickPosition = finalBaseViewHolder.getAdapterPosition();

        int startPosition = clickPosition - mMyChannelItems.size() - 2;

        ArticleCategory.DataBeanX.DataBean item = mOtherChannelItems.get(startPosition);

        mOtherChannelItems.remove(startPosition);
        mMyChannelItems.add(item);
        notifyItemMoved(clickPosition, mMyChannelItems.size() - 1 + 1);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder.getItemViewType()==TYPE_MY_CHANNEL_HEADER){
        }else if (holder.getItemViewType()== TYPE_MY  ) {
             ArticleCategory.DataBeanX.DataBean dataBean = mMyChannelItems.get(position-1);
             holder.setText(R.id.tv,dataBean.getName());
            if (isEditMode) {
                holder.setVisible(R.id.img_edit,true);
            } else {
                holder.setVisible(R.id.img_edit,false);
            }
         }else if (holder.getItemViewType()== TYPE_OTHER  ) {
             ArticleCategory.DataBeanX.DataBean dataBean = mOtherChannelItems.get(position-(mMyChannelItems.size()+2));
             holder.setText(R.id.tv,dataBean.getName());
         }
    }

    /**
     * 开启编辑模式
     *
     * @param parent
     */
    private void setEditMode(RecyclerView parent) {
        isEditMode = !isEditMode;
        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (imgEdit != null) {
                imgEdit.setVisibility(isEditMode ? View.VISIBLE:View.INVISIBLE);
            }
        }
    }
    /**
     * 添加需要移动的 镜像View
     */
    private ImageView addMirrorView(ViewGroup parent, View view) {
        /**
         * 我们要获取cache首先要通过setDrawingCacheEnable方法开启cache，然后再调用getDrawingCache方法就可以获得view的cache图片了。
         buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，若果cache没有建立，系统会自动调用buildDrawingCache方法生成cache。
         若想更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         当调用setDrawingCacheEnabled方法设置为false, 系统也会自动把原来的cache销毁。
         */
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        final ImageView mirrorView = new ImageView(view.getContext());
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        view.setDrawingCacheEnabled(false);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(view.getWidth(), view.getHeight());
        parent.addView(mirrorView, params);
        return mirrorView;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        // 向后拖拽
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mMyChannelItems, i -1, i);
            }
        } else {
            // 向前拖拽
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mMyChannelItems, i-2, i-1);
            }
        }
//        ArticleCategory.DataBeanX.DataBean item = mMyChannelItems.get(fromPosition - COUNT_PRE_MY_HEADER);
//        mMyChannelItems.remove(fromPosition - COUNT_PRE_MY_HEADER);
//        mMyChannelItems.add(toPosition - COUNT_PRE_MY_HEADER, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 我的频道
     */
    class MyViewHolder extends BaseViewHolder implements OnDragVHListener {
        private TextView textView;
        private ImageView imgEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.tv);
            imgEdit = itemView.findViewById(R.id.img_edit);
        }

        /**
         * item 被选中时
         */
        @Override
        public void onItemSelected() {
            textView.setBackgroundResource(R.drawable.bg_channel_p);
        }

        /**
         * item 取消选中时
         */
        @Override
        public void onItemFinish() {
            textView.setBackgroundResource(R.drawable.bg_channel_n);
        }
    }
}
