package com.kx.todaynews.module.user;


import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kx.todaynews.R;
import com.kx.todaynews.base.BaseFragment;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.utils.GlideCircleTransform;

import butterknife.BindView;


/**
 * Created by admin on 2018/10/14.
 */
public class UserFragment extends BaseFragment {

    private static UserFragment instance = null;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;

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
        String url  = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541353845829&di=a64d7fedf9a6b8c50834ce73600d2cc0&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-09-22%2F102336704.jpg";
        Glide.with(mActivity).load(url).transform(new GlideCircleTransform(mActivity)).into(mIvAvatar);
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
