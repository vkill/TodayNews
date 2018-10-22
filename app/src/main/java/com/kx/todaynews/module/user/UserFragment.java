package com.kx.todaynews.module.user;


import android.view.View;

import com.kx.todaynews.R;
import com.kx.todaynews.base.BaseFragment;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.base.IBasePresenter;
import com.kx.todaynews.widget.loadinglayout.LoadingFlashView;

import butterknife.BindView;


/**
 * Created by admin on 2018/10/14.
 */
public class UserFragment extends BaseFragment {

    private static UserFragment instance = null;
    @BindView(R.id.loadingLayout)
    LoadingFlashView loadingLayout;

    public static UserFragment getInstance() {
        if (instance == null) {
            instance = new UserFragment();
        }
        return instance;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_user;
    }

    @Override
    protected void initEventAndData() {
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
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }
    @Override
    protected void initView(View rootView) {

    }
}
