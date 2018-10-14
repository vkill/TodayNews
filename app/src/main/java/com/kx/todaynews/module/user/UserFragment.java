package com.kx.todaynews.module.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kx.todaynews.R;

/**
 * Created by admin on 2018/10/14.
 */
public class UserFragment extends Fragment {

    private static UserFragment instance = null;

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
        return view;
    }
}
