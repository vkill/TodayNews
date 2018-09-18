package com.kx.todaynews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class HotDataAdapter  {
    //  纯文本,没图片的 item
    public static final int TYPE_TEXT =  0 ;
    //  一张图片，位于右边
    public static final int TYPE_ONE_IMAGE =  1 ;
    //  显示三张图片，位于底边
    public static final int TYPE_THREE_IMAGE =  2 ;
    //  多张图片                    count >=10
    public static final int TYPE_MANY_IMAGE =  3 ;

    /**
     *  只显示Title的 Holder
     */
    static class  TexTHolder extends RecyclerView.ViewHolder{

        public TexTHolder(View itemView) {
            super(itemView);
        }
    }
    /**
     *  显示一张图片的 Holder
     */
    static class  OneImageHolder extends RecyclerView.ViewHolder{

        public OneImageHolder(View itemView) {
            super(itemView);
        }
    }
    /**
     *  显示三张图片的 Holder
     */
    static class  ThreeImageHolder extends RecyclerView.ViewHolder{

        public ThreeImageHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     *  显示多张图片的 Holder
     */
    static class  ThreeManyHolder extends RecyclerView.ViewHolder{

        public ThreeManyHolder(View itemView) {
            super(itemView);
        }
    }
}
