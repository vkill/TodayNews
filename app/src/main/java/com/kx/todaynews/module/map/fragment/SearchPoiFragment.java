package com.kx.todaynews.module.map.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.kx.todaynews.R;
import com.kx.todaynews.module.map.SelectMapPointActivity;
import com.kx.todaynews.module.map.walkroute.WalkRouteDetailActivity;
import com.kx.todaynews.module.map.walkroute.overlay.WalkRouteOverlay;
import com.kx.todaynews.module.map.walkroute.util.AMapUtil;
import com.kx.todaynews.module.map.walkroute.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.kx.todaynews.module.map.SelectMapPointActivity.CHOOSEPOINTLATLNG;
import static com.kx.todaynews.module.map.SelectMapPointActivity.MYPOSITIONLATLNG;

/**
 * Created by admin on 2018/11/14.
 */
public class SearchPoiFragment extends SupportMapFragment implements RouteSearch.OnRouteSearchListener, AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, AMap.OnMapLoadedListener {
    public static final int MY_POSITION_REQUESTCODE = 1000 ;
    public static final int CHOOSE_POINT_REQUESTCODE = 1001 ;
    @BindView(R.id.tv_my_position)
    TextView tvMyPosition;
    @BindView(R.id.tv_choose_point)
    TextView tvChoosePoint;
    Unbinder unbinder;
    LatLng myPosition ;
    LatLng choosePoint ;
    onRouteSelectListener mOnRouteSelectListener;
    private String[] items = {"公交", "步行", "驾车", "骑行"};
    private String searchType = items[0];
    private AMap aMap;
    private MapView mapView;
    private RouteSearch mRouteSearch;
    private WalkRouteResult mWalkRouteResult;
    private LatLonPoint mStartPoint = new LatLonPoint(39.996678,116.479271);//起点，39.996678,116.479271
    private LatLonPoint mEndPoint = new LatLonPoint(39.997796,116.468939);//终点，39.997796,116.468939
    private final int ROUTE_TYPE_WALK = 3;

    private RelativeLayout mBottomLayout, mHeadLayout;
    private TextView mRotueTimeDes, mRouteDetailDes;
    private ProgressDialog progDialog = null;// 搜索时进度条
    private WalkRouteOverlay walkRouteOverlay;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof onRouteSelectListener){
            mOnRouteSelectListener = (onRouteSelectListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_search_poi, viewGroup, false);
        unbinder = ButterKnife.bind(this, rootView);


        mapView =rootView.findViewById(R.id.route_map);
        mapView.onCreate(bundle);// 此方法必须重写
        init(rootView);
        return rootView;
    }
    /**
     * 初始化AMap对象
     */
    private void init(View rootView) {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        registerListener();
        mRouteSearch = new RouteSearch(getContext());
        mRouteSearch.setRouteSearchListener(this);
        mBottomLayout = (RelativeLayout)rootView.findViewById(R.id.bottom_layout);
        mHeadLayout = (RelativeLayout) rootView.findViewById(R.id.routemap_header);
        mRotueTimeDes = (TextView) rootView.findViewById(R.id.firstline);
        mRouteDetailDes = (TextView) rootView.findViewById(R.id.secondline);
        MyLocationStyle myLocationStyle = new MyLocationStyle();

        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //设置定位蓝点精度圆圈的填充颜色的方法。
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        //设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        aMap.getUiSettings().setZoomControlsEnabled(true);
        // 修改蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        // aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示，非必需设置。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16.5f));
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (followMove) {
                    aMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
                }
            }
        });
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (followMove) {
                    //用户拖动地图后，不再跟随移动，直到用户点击定位按钮
                    followMove = false ;
                }
            }
        });
    }
    private boolean followMove=true;
    /**
     * 注册监听
     */
    private void registerListener() {
        aMap.setOnMapClickListener(this);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnInfoWindowClickListener(this);
        aMap.setInfoWindowAdapter(this);
        aMap.setOnMapLoadedListener(this);

    }
    @OnClick(R.id.tv_my_position)
    public void onTvMyPositionClicked() {
        Intent intent = new Intent(getContext(), SelectMapPointActivity.class);
        intent.putExtra(SelectMapPointActivity.TAG,MY_POSITION_REQUESTCODE);
        intent.putExtra(MYPOSITIONLATLNG,myPosition);
        intent.putExtra(CHOOSEPOINTLATLNG,choosePoint);
        startActivityForResult(intent,MY_POSITION_REQUESTCODE);
    }

    @OnClick(R.id.tv_choose_point)
    public void onTvChoosePointClicked() {
        Intent intent = new Intent(getContext(), SelectMapPointActivity.class);
        intent.putExtra(SelectMapPointActivity.TAG,CHOOSE_POINT_REQUESTCODE);
        intent.putExtra(MYPOSITIONLATLNG,myPosition);
        intent.putExtra(CHOOSEPOINTLATLNG,choosePoint);
        startActivityForResult(intent,CHOOSE_POINT_REQUESTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==MY_POSITION_REQUESTCODE && resultCode == Activity.RESULT_OK){
            PoiItem poiItem = data.getParcelableExtra(MYPOSITIONLATLNG);
            myPosition = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
            if (mOnRouteSelectListener!=null){
                mOnRouteSelectListener.onStartSelect(poiItem);
            }
        }
        if (requestCode==CHOOSE_POINT_REQUESTCODE && resultCode == Activity.RESULT_OK){
            PoiItem poiItem = data.getParcelableExtra(CHOOSEPOINTLATLNG);
            choosePoint = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
            if (mOnRouteSelectListener!=null){
                mOnRouteSelectListener.onEndSelect(poiItem);
            }
        }
    }
    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType) {
        if (myPosition == null) {
            ToastUtil.show(getActivity(), "起点未设置");
            return;
        }
        if (choosePoint == null) {
            ToastUtil.show(getActivity(), "终点未设置");
        }
        showProgressDialog();
        mStartPoint = new LatLonPoint(myPosition.latitude,myPosition.longitude);
        mEndPoint =  new LatLonPoint(choosePoint.latitude,choosePoint.longitude);
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }
    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(getContext());
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dismissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        unbinder.unbind();
    }
    /**
     * 公交路线数据
     */
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        dismissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    if (walkRouteOverlay != null){
                        walkRouteOverlay.removeFromMap();
                    }
                    walkRouteOverlay = new WalkRouteOverlay(
                            getContext(), aMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) walkPath.getDistance();
                    int dur = (int) walkPath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
                    mRotueTimeDes.setText(des);
                    mRouteDetailDes.setVisibility(View.GONE);
                    mBottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),
                                    WalkRouteDetailActivity.class);
                            intent.putExtra("walk_path", walkPath);
                            intent.putExtra("walk_result",
                                    mWalkRouteResult);
                            startActivity(intent);
                        }
                    });
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(getActivity(), R.string.no_result);
                }
            } else {
                ToastUtil.show(getActivity(), R.string.no_result);
            }
        } else {
            ToastUtil.showerror(getActivity(), errorCode);
        }
    }
    /**
     * 驾车路线数据
     */
    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    /**
     * 骑行路线数据
     */
    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onMapLoaded() {
        searchRouteResult(ROUTE_TYPE_WALK);
    }
    @OnClick(R.id.rl_on_walk_click)
    public void  onWalkClick(View view){
        searchRouteResult(ROUTE_TYPE_WALK);
    }
    @OnClick(R.id.rl_on_bus_click)
    public void  onBusClick(View view){
        searchRouteResult(ROUTE_TYPE_WALK);
    }

    public  interface  onRouteSelectListener{
        void onStartSelect(PoiItem poiItem);
        void onEndSelect(PoiItem poiItem);
    }
}
