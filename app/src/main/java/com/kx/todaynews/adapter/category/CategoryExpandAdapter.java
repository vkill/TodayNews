package com.kx.todaynews.adapter.category;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.ArticleCategory;
import com.kx.todaynews.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/10/27.
 */
public class CategoryExpandAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    // 我的频道 标题部分
    public static final int TYPE_MY_CHANNEL_HEADER = 0;
    // 我的频道
    public static final int TYPE_MY = 1;
    // 其他频道 标题部分
    public static final int TYPE_OTHER_CHANNEL_HEADER = 2;
    // 其他频道
    public static final int TYPE_OTHER = 3;

    private List<ArticleCategory.DataBeanX.DataBean> mMyChannelItems = new ArrayList<>();
    private List<ArticleCategory.DataBeanX.DataBean> mOtherChannelItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater ;
    private boolean isEditMode = false;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };


    public CategoryExpandAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
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
            baseViewHolder = new BaseViewHolder(view);
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
        }else if (viewType == TYPE_OTHER){
            view = mLayoutInflater.inflate(R.layout.item_other, parent, false);
            baseViewHolder = new BaseViewHolder(view);
            TextView tv = view.findViewById(R.id.tv);
            RecyclerView recyclerView = ((RecyclerView) parent);
            ViewGroup viewParent = (ViewGroup) recyclerView.getParent();
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            // 点击其他频道item.
            BaseViewHolder finalBaseViewHolder = baseViewHolder;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout parent = (RelativeLayout) tv.getParent();
                    final PathMeasure mPathMeasure;
                    final float[] mCurrentPosition = new float[2];
                    int parentLoc[] = new int[2];
                    parent.getLocationInWindow(parentLoc);
                    int startLoc[] = new int[2];
                    view.getLocationInWindow(startLoc);

                    ImageView imageView = addMirrorView(viewParent, recyclerView, view);

                    final View endView;
                    float toX, toY;
                    int endLoc[] = new int[2];
                    //进行判断
                    int i = mMyChannelItems.size();
                    //  我的频道暂无数据
                    if (i == 0) {
                        toX = view.getWidth();
                        toY = view.getHeight();
                    // 我的频道数据为4的整数，就移动到下一行。
                    } else if (i % 4 == 0) {
                        endView = manager.findViewByPosition(i-3);
                        endView.getLocationInWindow(endLoc);
                        toX = endLoc[0] + (startLoc[0] - parentLoc[0]);
                        toY = endLoc[1] + view.getHeight()+(startLoc[1] - parentLoc[1]) *3 /2 ;
                    } else {
                        // 添加在后面
                        endView = manager.findViewByPosition(i);
                        endView.getLocationInWindow(endLoc);
                        toX = endLoc[0] + endView.getWidth() + (startLoc[0] - parentLoc[0]);
                        toY = endLoc[1] -  (startLoc[1] - parentLoc[1]) / 2;
                    }
                    float startX = startLoc[0];
                    float startY = startLoc[1];

                    Path path = new Path();
                    path.moveTo(startX, startY);
                    path.lineTo(toX, toY);
                    mPathMeasure = new PathMeasure(path, false);
                    //属性动画实现
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
                    valueAnimator.setDuration(330);
                    // 匀速插值器
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (Float) animation.getAnimatedValue();
                            // 获取当前点坐标封装到mCurrentPosition
                            mPathMeasure.getPosTan(value, mCurrentPosition, null);
                            imageView.setTranslationX(mCurrentPosition[0]);
                            imageView.setTranslationY(mCurrentPosition[1]);
                        }
                    });
                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                         //   notifyItemRemoved(position);
                           // viewParent.removeView(startView);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    int position = finalBaseViewHolder.getAdapterPosition();

                    int startPosition = position - mMyChannelItems.size() - 2;

                    ArticleCategory.DataBeanX.DataBean item = mOtherChannelItems.get(startPosition);

                    mOtherChannelItems.remove(startPosition);
                    mMyChannelItems.add(item);
                    notifyItemMoved(position, mMyChannelItems.size() - 1 + 1);
                    valueAnimator.start();
                    //  或者在动画结束后移除
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewParent.removeView(imageView);
                        }
                    }, 400);
                }
            });

        }
        return baseViewHolder;
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
//             holder.setOnClickListener(R.id.tv, new View.OnClickListener() {
//                 @Override
//                 public void onClick(View v) {
//                     System.out.println(dataBean.getName());
//                 }
//             });
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
    private ImageView addMirrorView(ViewGroup parent, RecyclerView recyclerView, View view) {
        /**
         * 我们要获取cache首先要通过setDrawingCacheEnable方法开启cache，然后再调用getDrawingCache方法就可以获得view的cache图片了。
         buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，若果cache没有建立，系统会自动调用buildDrawingCache方法生成cache。
         若想更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         当调用setDrawingCacheEnabled方法设置为false, 系统也会自动把原来的cache销毁。
         */
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        final ImageView mirrorView = new ImageView(recyclerView.getContext());
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        view.setDrawingCacheEnabled(false);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(view.getWidth(), view.getHeight());
        parent.addView(mirrorView, params);

//        int[] locations = new int[2];
//        view.getLocationOnScreen(locations);
//        int[] parenLocations = new int[2];
//        recyclerView.getLocationOnScreen(parenLocations);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
//        params.setMargins(locations[0], locations[1] - parenLocations[1], 0, 0);
//        parent.addView(mirrorView, params);

        return mirrorView;
    }
}
