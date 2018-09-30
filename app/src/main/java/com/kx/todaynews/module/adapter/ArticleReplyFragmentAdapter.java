package com.kx.todaynews.module.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Administrator
 */
public class ArticleReplyFragmentAdapter extends RecyclerView.Adapter<ArticleReplyFragmentAdapter.ArticleReplyFragmentHolder> {



    @Override
    public ArticleReplyFragmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ArticleReplyFragmentHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ArticleReplyFragmentHolder extends RecyclerView.ViewHolder{

        public ArticleReplyFragmentHolder(View itemView) {
            super(itemView);
        }
    }
}
