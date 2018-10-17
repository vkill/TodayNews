package com.kx.todaynews.module.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kx.todaynews.R;
import com.kx.todaynews.widget.loadinglayout.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2018/10/14.
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private static UserFragment instance = null;
    Unbinder unbinder;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;

    public static UserFragment getInstance() {
        if (instance == null) {
            instance = new UserFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        //  changeNightMode.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //     public void onClick(View v) {
//                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//                if (mode == Configuration.UI_MODE_NIGHT_YES) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                }
        //  getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        // getActivity().recreate();
        //        }
        //  });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                loadingLayout.showLoading();
                break;
            case R.id.tv2:
                loadingLayout.showDataError();
                break;
            case R.id.tv3:
                loadingLayout.showNetError();
                break;
            case R.id.tv4:
                loadingLayout.showEmpty();
                break;
            case R.id.tv5:
                loadingLayout.showContentView();
                break;
        }
    }
}
