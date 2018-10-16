package com.kx.todaynews.module.user;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kx.todaynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2018/10/14.
 */
public class UserFragment extends Fragment {

    private static UserFragment instance = null;
    @BindView(R.id.changeNightMode)
    TextView changeNightMode;
    Unbinder unbinder;

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
