package com.kx.todaynews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.bean.ImageList;
import com.kx.todaynews.bean.MiddleImage;
import com.kx.todaynews.bean.VideoDetailInfo;

import java.util.ArrayList;
import java.util.List;

public class HotDataAdapter  extends RecyclerView.Adapter<HotDataAdapter.AbstractHolder>{
    //  纯文本,没图片的 item
    public static final int TYPE_TEXT =  0 ;
    //  一张图片，位于右边
    public static final int TYPE_ONE_IMAGE =  1 ;
    //  显示三张图片，位于底边
    public static final int TYPE_THREE_IMAGE =  2 ;
    //  多张图片                    count >=10
    public static final int TYPE_MANY_IMAGE =  3 ;
    //  视频 Item
    public static final int TYPE_VIDEO=  4 ;
    // 为编写布局的Item类型
    public static final int TYPE_UNKNOW=  5 ;

    private LayoutInflater mLayoutInflater ;
    private List<HotContent> mContents  = new ArrayList<>();
    private  Context mContext ;
    public HotDataAdapter(Context  context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setHotDatas(List<HotContent> contents) {
        if (contents !=null && contents .size()>0){
            mContents.addAll(0,contents);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        HotContent hotContent = mContents.get(position);
        int gallary_image_count = hotContent.getGallary_image_count();
        boolean has_video = hotContent.isHas_video();
        ArrayList<ImageList> image_list = hotContent.getImage_list();
        if (has_video){
            return TYPE_VIDEO;
        }else if (gallary_image_count==0){
            return TYPE_TEXT;
        }else if (image_list!=null && image_list.size()>=3 ){
            return TYPE_THREE_IMAGE ;
        }else if (gallary_image_count < 6  && gallary_image_count >=1 ){
            return TYPE_ONE_IMAGE;
        }else if (gallary_image_count >= 6){
           return TYPE_MANY_IMAGE ;
        }else {
            return TYPE_UNKNOW;
        }
    }

    @Override
    public AbstractHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT){
            return new TexTHolder(mLayoutInflater.inflate(R.layout.item_hot_data_text,null,false));
        }else if (viewType == TYPE_ONE_IMAGE){
            return new OneImageHolder(mLayoutInflater.inflate(R.layout.item_hot_data_one_image,null,false));
        }else if (viewType == TYPE_THREE_IMAGE){
            return new ThreeImageHolder(mLayoutInflater.inflate(R.layout.item_hot_data_three_image,null,false));
        }else if (viewType == TYPE_MANY_IMAGE){
            return new ManyImageHolder(mLayoutInflater.inflate(R.layout.item_hot_data_many_image,null,false));
        } else if (viewType == TYPE_VIDEO){
            return new VideoHolder(mLayoutInflater.inflate(R.layout.item_hot_data_video,null,false));
        }else {
            return new AbstractHolder(mLayoutInflater.inflate(R.layout.unknow,null,false));
        }
    }
    @Override
    public void onBindViewHolder(AbstractHolder holder, int position) {
        HotContent hotContent = mContents.get(position);
        holder.title.setText(hotContent.getTitle());
        holder.media_name.setText(hotContent.getMedia_name());
        holder.comment_count.setText(String.format("%s评论",hotContent.getComment_count()));
        if (holder instanceof  ThreeImageHolder){
            ArrayList<ImageList> image_list = hotContent.getImage_list();
                Glide.with(mContext).load(image_list.get(0).getUrl()).into(((ThreeImageHolder)holder).iv_1);
                Glide.with(mContext).load(image_list.get(1).getUrl()).into(((ThreeImageHolder)holder).iv_2);
                Glide.with(mContext).load(image_list.get(2).getUrl()).into(((ThreeImageHolder)holder).iv_3);
        }else if (holder instanceof  VideoHolder){
            //  视频时长  单位 秒   需要自行转换成  HH：mm : ss  格式
            int video_duration = hotContent.getVideo_duration();
            VideoDetailInfo video_detail_info = hotContent.getVideo_detail_info();
            if (video_detail_info!=null){
                Glide.with(mContext).load(video_detail_info.getDetail_video_large_image().getUrl()).into(((VideoHolder)holder).iv_1);
            }
        }else if (holder instanceof  OneImageHolder){
            MiddleImage middle_image = hotContent.getMiddle_image();
            if (middle_image!=null){
                Glide.with(mContext).load(middle_image.getUrl()).into(((OneImageHolder)holder).iv_1);
            }
            int gallary_image_count = hotContent.getGallary_image_count();
            if (gallary_image_count >1){
                ((OneImageHolder)holder).tv_image_count.setVisibility(View.VISIBLE);
                ((OneImageHolder)holder).tv_image_count.setText(String.format("%s图",gallary_image_count));
            }
        }else if (holder instanceof  ManyImageHolder){
            MiddleImage middle_image = hotContent.getMiddle_image();
            ((ManyImageHolder)holder).tv_image_count.setText(String.format("%s图",middle_image));
            ArrayList<ImageList> image_list = hotContent.getImage_list();
            if (image_list !=null && image_list .size()>0 ){
               // ((ManyImageHolder)holder).iv.setText(String.format("%s图",image_list.get(0).getUrl()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    /**
     *  只显示Title的 Holder
     */
    private static class  TexTHolder extends AbstractHolder{
        private TexTHolder(View itemView) {
            super(itemView);
        }
    }
    /**
     *  显示一张图片的 Holder
     */
    static class  OneImageHolder extends AbstractHolder{
        ImageView iv_1;
        TextView tv_image_count;
        public OneImageHolder(View itemView) {
            super(itemView);
            iv_1 = itemView.findViewById(R.id.iv_1);
            tv_image_count = itemView.findViewById(R.id.tv_image_count);
        }
    }
    /**
     *  显示三张图片的 Holder
     */
    private static class  ThreeImageHolder extends AbstractHolder{
        ImageView iv_1,iv_2,iv_3;
        private ThreeImageHolder(View itemView) {
            super(itemView);
            iv_1 = itemView.findViewById(R.id.iv_1);
            iv_2 = itemView.findViewById(R.id.iv_2);
            iv_3 = itemView.findViewById(R.id.iv_3);
        }
    }

    /**
     *  显示多张图片的 Holder
     */
    private static class  ManyImageHolder extends AbstractHolder{
        TextView tv_image_count;
        ImageView iv_1;
        private ManyImageHolder(View itemView) {
            super(itemView);
            tv_image_count = itemView.findViewById(R.id.tv_image_count);
            iv_1 = itemView.findViewById(R.id.iv_1);
        }
    }
    static class  VideoHolder extends AbstractHolder{
        ImageView iv_1;
        private VideoHolder(View itemView) {
            super(itemView);
            iv_1 = itemView.findViewById(R.id.iv_1);
        }
    }

     static  class AbstractHolder extends RecyclerView.ViewHolder{
        /**
         * 每个item布局公有的控件
         */
        TextView title ,media_name,comment_count;
        private AbstractHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            media_name = itemView.findViewById(R.id.media_name);
            comment_count = itemView.findViewById(R.id.comment_count);
        }
    }
}
