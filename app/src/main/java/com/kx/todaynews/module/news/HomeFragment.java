package com.kx.todaynews.module.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kx.todaynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.weyye.library.colortrackview.ColorTrackTabLayout;


/**
 * Created by admin on 2018/10/14.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.tab_channel)
    ColorTrackTabLayout tabChannel;
    @BindView(R.id.iv_operation)
    ImageView ivOperation;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    Unbinder unbinder;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initChannelFragments();
    }

    private void initChannelFragments() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
