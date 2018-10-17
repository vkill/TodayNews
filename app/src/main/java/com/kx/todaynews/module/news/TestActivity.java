package com.kx.todaynews.module.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kx.todaynews.R;
import com.kx.todaynews.widget.loadinglayout.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
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
    @BindView(R.id.loadingView)
    View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
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

    public void xx(View view) {
        loadingView.setVisibility(loadingView.getVisibility() == View.VISIBLE ? View.GONE :View.VISIBLE);
    }
}
