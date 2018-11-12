package com.kx.todaynews.module.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kx.todaynews.R;
import com.kx.todaynews.constants.Constant;
import com.kx.todaynews.module.news.activity.CategoryExpandActivity;
import com.kx.todaynews.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.weyye.library.colortrackview.ColorTrackTabLayout;


/**
 * Created by admin on 2018/10/14.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.tab_channel)
    ColorTrackTabLayout tabChannel;
    @BindView(R.id.iv_add_category)
    ImageView ivAddChannel;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    Unbinder unbinder;
    private List<NewsListFragment> mChannelFragments = new ArrayList<>();
    private String[] mChannelCodes;
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
        mChannelCodes = getResources().getStringArray(R.array.channel_code);
        String[] channels = getResources().getStringArray(R.array.channel);
        String[] channelCodes = getResources().getStringArray(R.array.channel_code);

        if (mChannelFragments.size()>0){
            mChannelFragments.clear();
        }
        for (String channel: channelCodes) {
            NewsListFragment newsListFragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE,channel);
            //是否是视频列表页面,根据判断频道号是否是视频
            bundle.putBoolean(Constant.IS_VIDEO_LIST, "video".equals(channel));
            bundle.putBoolean(Constant.IS_IMAGE_LIST, "组图".equals(channel));
            newsListFragment.setArguments(bundle);
            mChannelFragments.add(newsListFragment);//添加到集合中
        }
        tabChannel.setTabPaddingLeftAndRight(UiUtils.dp2px(getActivity(),10), UiUtils.dp2px(getActivity(),10));
        tabChannel.setupWithViewPager(vpContent);
        tabChannel.post(new Runnable() {
            @Override
            public void run() {
                //设置最小宽度，使其可以在滑动一部分距离
                ViewGroup slidingTabStrip = (ViewGroup) tabChannel.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + ivAddChannel.getMeasuredWidth());
            }
        });
        //隐藏指示器
        tabChannel.setSelectedTabIndicatorHeight(0);

        vpContent.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mChannelFragments.get(position);
            }

            @Override
            public int getCount() {
                return mChannelFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return channels[position];
            }
        });
        vpContent.setOffscreenPageLimit(mChannelFragments.size());
    }
    @OnClick(R.id.iv_add_category)
    public void addCategory(){
        startActivity(new Intent(getActivity(), CategoryExpandActivity.class));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
