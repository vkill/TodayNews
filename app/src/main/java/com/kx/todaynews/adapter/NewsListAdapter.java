package com.kx.todaynews.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.provider.LiveItemProvider;
import com.kx.todaynews.adapter.provider.ManyPicItemProvider;
import com.kx.todaynews.adapter.provider.OnePicItemProvider;
import com.kx.todaynews.adapter.provider.TextNewsItemProvider;
import com.kx.todaynews.adapter.provider.ThreePicItemProvider;
import com.kx.todaynews.adapter.provider.VideoItemProvider;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.bean.ImageList;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends MultipleItemRvAdapter<HotContent,BaseViewHolder> {
    //  纯文本,没图片的 item
    public static final int TYPE_TEXT =  0 ;
    //  一张图片，位于右边
    public static final int TYPE_ONE_IMAGE =  1 ;
    //  显示三张图片，位于底边
    public static final int TYPE_THREE_IMAGE =  2 ;
    //  多张图片                    count >=6
    public static final int TYPE_MANY_IMAGE =  3 ;
    //  视频 Item
    public static final int TYPE_VIDEO=  4 ;
    //  广告
    public static final int TYPE_ADVERTISEMENT=  5 ;
    //  直播
    public static final int TYPE_LIVE=  6 ;
    // 未编写布局的Item类型
    public static final int TYPE_UNKNOW=  7 ;

    public NewsListAdapter(@Nullable List<HotContent> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(HotContent hotContent) {
        int gallary_image_count = hotContent.getGallary_image_count();
        boolean has_video = hotContent.isHas_video();
        ArrayList<ImageList> image_list = hotContent.getImage_list();
        if (has_video){
            return TYPE_VIDEO;
        }else if (hotContent.getBackground()!=null){
            return TYPE_LIVE;
        } else if (gallary_image_count==0){
            return TYPE_TEXT;
        }else if (image_list!=null && image_list.size()>=3 ){
            return TYPE_THREE_IMAGE ;
        }else if (gallary_image_count <= 6  && gallary_image_count >=1 ){
            return TYPE_ONE_IMAGE;
        }else if (gallary_image_count > 6){
            return TYPE_MANY_IMAGE ;
        }else {
            return TYPE_UNKNOW;
        }
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider(new TextNewsItemProvider());
        mProviderDelegate.registerProvider(new OnePicItemProvider());
        mProviderDelegate.registerProvider(new ThreePicItemProvider());
        mProviderDelegate.registerProvider(new ManyPicItemProvider());
        mProviderDelegate.registerProvider(new VideoItemProvider());
        mProviderDelegate.registerProvider(new LiveItemProvider());
    }

     static  class AbstractHolder extends RecyclerView.ViewHolder{
        /**
         * 每个item布局公有的控件
         */
        TextView title ,media_name,comment_count,tv_time;
        private AbstractHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            media_name = itemView.findViewById(R.id.media_name);
            comment_count = itemView.findViewById(R.id.comment_count);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
    public interface onItemClickListener {
        void onItemClick(String groupId);
    }
    onItemClickListener mListener;

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }
}
