package com.kx.todaynews.module.map.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.SupportMapFragment;
import com.kx.todaynews.R;
import com.kx.todaynews.module.map.SelectMapPointActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by admin on 2018/11/14.
 */
public class SearchPoiFragment extends SupportMapFragment {


    @BindView(R.id.tv_my_position)
    TextView tvMyPosition;
    @BindView(R.id.tv_choose_point)
    TextView tvChoosePoint;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_search_poi, viewGroup, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.tv_my_position)
    public void onTvMyPositionClicked() {
        startActivity(new Intent(getContext(), SelectMapPointActivity.class));
    }

    @OnClick(R.id.tv_choose_point)
    public void onTvChoosePointClicked() {
        startActivity(new Intent(getContext(), SelectMapPointActivity.class));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
