package com.kx.todaynews.widget.emoji;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kx.todaynews.ArticleDetailActivity;
import com.kx.todaynews.R;


public class EmoJiFragment extends Fragment {
    private int type = 0;
    private EditText et_input_container;
    private CircleIndicator circleIndicator;

    public EmoJiFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.fragment_emoji, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArticleDetailActivity mainActivity = (ArticleDetailActivity) getActivity();
        initView();
    }

    private void initView() {
       // type = getArguments().getInt("EmoJiType");
        ViewPager viewPager = (ViewPager) getView().findViewById(R.id.viewPager);
        CircleIndicator circleIndicator = (CircleIndicator) getView().findViewById(R.id.circleIndicator);
        EmoJiHelper emojiHelper = new EmoJiHelper(type, getContext(), et_input_container);
        EmoJiContainerAdapter mAdapter = new EmoJiContainerAdapter(emojiHelper.getPagers());
        viewPager.setAdapter(mAdapter);
        circleIndicator.setViewPager(viewPager);
    }
}