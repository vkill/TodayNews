package com.kx.todaynews.module.map;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.amap.api.services.core.PoiItem;
import com.kx.todaynews.R;
import com.kx.todaynews.base.BaseActivity;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.module.map.fragment.SearchPoiFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class RouteShowActivity extends BaseActivity implements SearchPoiFragment.onRouteSelectListener{

    @BindView(R.id.et_start)
    EditText etStart;
    @BindView(R.id.et_end)
    EditText etEnd;
    /**
     * 用于判断 当前是 哪个EditText获取焦点,
     */
    private boolean isStartEtHasFocus = true;
    private SearchPoiFragment mSearchPoiFragment;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_route_show;
    }

    @Override
    protected void initListener() {
        etStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isStartEtHasFocus = hasFocus;
            }
        });
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {
        mSearchPoiFragment = new SearchPoiFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_container, mSearchPoiFragment);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.iv_route_edit_exchange)
    public void onRouteEdit_ExchangeClick(){

        // 动画前先将Hint值改变
        etStart.setHint("输入终点");
        etEnd.setHint("输入起点");

        ImageView startMirrorView = addMirrorView(etStart);
        ImageView endMirrorView = addMirrorView(etEnd);

        // 动画前先清除获取焦点的EditText的光标
        if (isStartEtHasFocus){
            etStart.setCursorVisible(false);
        }else {
            etEnd.setCursorVisible(false);
        }

        int[] startPosition = new int[2];
        etStart.getLocationOnScreen(startPosition);

        int[] endPosition = new int[2];
        etEnd.getLocationOnScreen(endPosition);

        // 动画前记录两个文本框之前的文本值
        String start = etStart.getText().toString().trim();
        String end = etEnd.getText().toString().trim();

        etStart.setText("");
        etEnd.setText("");
        etStart.setHint("");
        etEnd.setHint("");

        ObjectAnimator upToDownAnimator = ObjectAnimator.ofFloat(startMirrorView,"translationY",0,endPosition[1]- startPosition[1]);
        ObjectAnimator downToUpAnimator = ObjectAnimator.ofFloat(endMirrorView,"translationY",0,startPosition[1]- endPosition[1]);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(upToDownAnimator, downToUpAnimator);
        animatorSet.setDuration(300);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
                decorView.removeView(startMirrorView);
                decorView.removeView(endMirrorView);

                etEnd.setHint("输入终点");
                etStart.setHint("输入起点");

                etStart.setText(end);
                if (!TextUtils.isEmpty(etStart.getText().toString().trim())){
                    etStart.setSelection(etStart.getText().length());
                }
                etEnd.setText(start);
                if (!TextUtils.isEmpty(etEnd.getText().toString().trim())){
                    etEnd.setSelection(etEnd.getText().length());
                }
                // 动画结束后显示动画前获取焦点的EditText的光标
                if (isStartEtHasFocus){
                    etStart.setCursorVisible(true);
                }else {
                    etEnd.setCursorVisible(true);
                }
                // 交换起点和终点坐标
                mSearchPoiFragment.exchangeStartAndEndPoint();
            }
        });
        animatorSet.start();

    }

    /**
     *  起点选择回调
     */
    @Override
    public void onStartSelect(PoiItem poiItem) {
        etStart.setText(poiItem.getTitle());
        etStart.setSelection(etStart.getText().toString().trim().length());
    }
    /**
     *  终点选择回调
     */
    @Override
    public void onEndSelect(PoiItem poiItem) {
        etEnd.setText(poiItem.getTitle());
        etEnd.setSelection(etEnd.getText().toString().trim().length());
    }
    /**
     * 添加需要移动的 镜像View
     */
    private ImageView addMirrorView(EditText currentView) {
        /**
         * 我们要获取cache首先要通过setDrawingCacheEnable方法开启cache，然后再调用getDrawingCache方法就可以获得view的cache图片了。
         buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，若果cache没有建立，系统会自动调用buildDrawingCache方法生成cache。
         若想更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         当调用setDrawingCacheEnabled方法设置为false, 系统也会自动把原来的cache销毁。
         */
        currentView.destroyDrawingCache();
        currentView.setDrawingCacheEnabled(true);
        final ImageView mirrorView = new ImageView(currentView.getContext());
        Bitmap bitmap = Bitmap.createBitmap(currentView.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        currentView.setDrawingCacheEnabled(false);
        //  获取View左上角点的坐标
        int[] position = new int[2];
        currentView.getLocationOnScreen(position);

        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(currentView.getWidth(), currentView.getHeight());
        params.setMargins(position[0],position[1],position[0]+currentView.getWidth(),position[1]+currentView.getHeight());
        decorView.addView(mirrorView, params);

        return mirrorView;
    }
}
