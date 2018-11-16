package com.kx.todaynews;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kx.todaynews.module.map.MapFragment;
import com.kx.todaynews.module.news.HomeFragment;
import com.kx.todaynews.module.user.UserFragment;
import com.kx.todaynews.widget.helper.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {
    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "bottomNavigationSelectItem";
    private static final int FRAGMENT_NEWS = 0;
    private static final int FRAGMENT_MAPS = 1;
    private static final int FRAGMENT_USER = 2;

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    private int mLastFgIndex = 0;
    HomeFragment mNewsFragment;
    MapFragment  mMapFragment;
    UserFragment mUserFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv.setText(getString(R.string.main_news));
        //StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.main_status_bar_blue), 1f);
       // mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        //  查询   emoji 表情图顺序的接口
        //  http://ic.snssdk.com/user/expression/config/?iid=44267707161&device_id=57548705831&ac=wifi&channel=tengxun2&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&ab_version=425530%2C511489%2C512678%2C486952%2C442428%2C500130%2C504794%2C494121%2C499728%2C478963%2C496464%2C239097%2C500092%2C170988%2C493249%2C480607%2C374117%2C495946%2C478532%2C489312%2C501963%2C509852%2C513538%2C276206%2C453560%2C435216%2C459650%2C459993%2C511225%2C500386%2C416055%2C510641%2C392484%2C495897%2C378451%2C471406%2C510754%2C513728%2C508932%2C509307%2C512915%2C468954%2C271178%2C424178%2C326524%2C326532%2C476036%2C514892%2C496389%2C345191%2C504889%2C512336%2C512047%2C504723%2C513201%2C514035%2C455643%2C424177%2C214069%2C513805%2C507002%2C442255%2C514737%2C512958%2C489509%2C280449%2C281299%2C513401%2C511104%2C325618%2C508560%2C514708%2C514821%2C512807%2C498551%2C509887%2C508594%2C386889%2C498375%2C397995%2C467514%2C513891%2C512007%2C444464%2C506751%2C514991%2C261578%2C403270%2C491728%2C491265%2C293032%2C457481%2C502679%2C510535%2C491255%2C507368&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=Redmi+Note+4X&device_brand=xiaomi&language=zh&os_api=24&os_version=7.0&openudid=30f2074ddcee24da&manifest_version_code=691&resolution=1080*1920&dpi=480&update_version_code=69111&_rticket=1537718641955&plugin=0&fp=irT_JzPqFlKtFlD_PlU1F2mIFSF1&rom_version=miui_v9_v9.6.2.0.ncfcnfd&ts=1537718641&as=a2459b8aa1c7fb09b76341&mas=00f6e15477087c8bb3ab2150c2bdb0d427548780aa02e846ea
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main_news:
                  //  mToolbar.setVisibility(View.VISIBLE);
                  //  mTitleTv.setText(getString(R.string.main_news));
                    showFragment(FRAGMENT_NEWS);
                    break;
                    case R.id.tab_main_map:
                   // mToolbar.setVisibility(View.VISIBLE);
                  //  mTitleTv.setText(getString(R.string.main_news));
                    showFragment(FRAGMENT_MAPS);
                    break;
                case R.id.tab_main_user:
                  //  mToolbar.setVisibility(View.GONE);
                  //  mTitleTv.setText(getString(R.string.main_user));
                    showFragment(FRAGMENT_USER);
                    break;
            }
            return true;
        });
        if (savedInstanceState != null) {
            mNewsFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            mMapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(MapFragment.class.getName());
            mUserFragment= (UserFragment) getSupportFragmentManager().findFragmentByTag(UserFragment.class.getName());
           // showFragment(0);
           // mUserFragment = (UserFragment) getSupportFragmentManager().findFragmentByTag(UserFragment.class.getName());
            // 恢复 recreate 前的位置
            mLastFgIndex =savedInstanceState.getInt(POSITION);
            showFragment(savedInstanceState.getInt(POSITION));
            bottomNavigation.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
        } else {
            showFragment(FRAGMENT_NEWS);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // recreate 时记录当前位置 (在 Manifest 已禁止 Activity 旋转,所以旋转屏幕并不会执行以下代码)
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, mLastFgIndex);
        outState.putInt(SELECT_ITEM, bottomNavigation.getSelectedItemId());
    }
    private void showFragment(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = getTabFragmentByPosition(position);
        Fragment lastFg = getTabFragmentByPosition(mLastFgIndex);
        ft.hide(lastFg);
        mLastFgIndex = position;
        if (!targetFg.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(targetFg).commitAllowingStateLoss();
            ft.add(R.id.fl_container, targetFg,targetFg.getClass().getSimpleName());
        }
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
    }

    private Fragment getTabFragmentByPosition(int tabPosition){
        Fragment fragment = null ;
        switch (tabPosition) {
            case FRAGMENT_NEWS:
                /**
                 * 如果Fragment为空，就新建一个实例
                 */
                if (mNewsFragment == null) {
                    mNewsFragment = HomeFragment.getInstance();
                }
                fragment = mNewsFragment;
                break;
            case FRAGMENT_MAPS:
                if (mMapFragment == null) {
                    mMapFragment = MapFragment.getInstance();
                }
                fragment = mMapFragment;
                break;
            case FRAGMENT_USER:
                if (mUserFragment == null) {
                    mUserFragment = UserFragment.getInstance();
                }
                fragment = mUserFragment;
                break;
        }
        return fragment;
    }

}
