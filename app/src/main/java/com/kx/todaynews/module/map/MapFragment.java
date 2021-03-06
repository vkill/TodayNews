package com.kx.todaynews.module.map;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.maps.model.Poi;
import com.kx.todaynews.R;
import com.kx.todaynews.utils.LogUtils;
import com.kx.todaynews.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MapFragment extends SupportMapFragment implements AMap.OnPOIClickListener, AMap.OnMarkerClickListener {

    @BindView(R.id.map)
    MapView mMapView;
    Unbinder unbinder;
    private AMap mAMap;

    public static MapFragment getInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mMapView.onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private boolean followMove=true;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化地图控制器对象
        mAMap = null;
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        // 显示实时交通状况
        mAMap.setTrafficEnabled(true);
        /**
         * MyLocationStyle
         * https://lbs.amap.com/api/android-sdk/guide/create-map/mylocation
         */
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        MyLocationStyle myLocationStyle = new MyLocationStyle();

        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //设置定位蓝点精度圆圈的填充颜色的方法。
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        //设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        //  显示比例尺
        mAMap.getUiSettings().setScaleControlsEnabled(true);
        // 修改蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked));
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示，非必需设置。
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        mAMap.setOnPOIClickListener(this);
        mAMap.setOnMarkerClickListener(this);
        /**
         *  解决拖动地图就会自动回到中心点的问题
         */
        mAMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (followMove) {
                    mAMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
                }
            }
        });
        mAMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (followMove) {
                    //用户拖动地图后，不再跟随移动，直到用户点击定位按钮
                    followMove = false ;
                }
            }
        });

        mAMap.setMyLocationEnabled(true);
        //初始化AMapLocationClientOption对象
//        mLocationOption = new AMapLocationClientOption();
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getContext());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(this);
//        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
//        mLocationOption.setInterval(2000);
//        //获取一次定位结果：
//        //该方法默认为false。
//        mLocationOption.setOnceLocation(true);
//        //设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        /**
//         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
//         */
//       // mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();
        /**
         * 用于重新设置场景模式，
         * 设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
         */
//        if(null != mLocationClient){
//            mLocationClient.setLocationOption(mLocationOption);
//            mLocationClient.stopLocation();
//            mLocationClient.startLocation();
//        }
    }
    /**
     *  底图poi点击回调
     */
    @Override
    public void onPOIClick(Poi poi) {
        mAMap.clear();
        MarkerOptions markOptiopns = new MarkerOptions();
        markOptiopns.position(poi.getCoordinate());
        TextView textView = new TextView(getActivity());
        textView.setText(poi.getName());
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.custom_info_bubble);
        markOptiopns.icon(BitmapDescriptorFactory.fromView(textView));
        mAMap.addMarker(markOptiopns);
    }

    /**
     * Marker 点击回调
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        // 构造导航参数
        NaviPara naviPara = new NaviPara();
        // 设置终点位置
        naviPara.setTargetPoint(marker.getPosition());
        // 设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(AMapUtils.DRIVING_AVOID_CONGESTION);
        try {
            // 调起高德地图导航
            AMapUtils.openAMapNavi(naviPara, getActivity());
        } catch (com.amap.api.maps.AMapException e) {
            // 如果没安装会进入异常，调起下载页面
            AMapUtils.getLatestAMapApp(getActivity());
        }
        mAMap.clear();
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView!=null)
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView!=null)
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView!=null)
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView!=null)
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * AMapLocation核心方法解析
     * @param  @link{https://lbs.amap.com/api/android-location-sdk/guide/android-location/getlocation}
     */
//    @Override
//    public void onLocationChanged(AMapLocation aMapLocation) {
//        if (aMapLocation != null) {
//            if (aMapLocation.getErrorCode() == 0) {
//                //可在其中解析amapLocation获取相应内容。
//                LogUtils.e("地址：" +aMapLocation.getAddress() +"   经度："+ aMapLocation.getLongitude() +"  纬度："+aMapLocation.getLatitude());
//                LatLng latLng = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
//               // final Marker marker = mAMap.addMarker(new MarkerOptions().position(latLng).title("家").snippet("DefaultMarker").draggable(true));
//            }else {
//                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                ToastUtils.showToast("ErrCode:"
//                        + aMapLocation.getErrorCode() + ", errInfo:"
//                        + aMapLocation.getErrorInfo());
//            }
//        }
//    }
    @OnClick(R.id.bt_location)
    public void ReLocate(){
        mAMap.setMyLocationEnabled(true);
        followMove = true;
    }
    @OnClick(R.id.bt_route)
    public void onShowRoute(){
        startActivity(new Intent(getContext(),RouteShowActivity.class));
    }
    private boolean showTraffic = true;
    @OnClick(R.id.iv_setTraffic)
    public void SetTraffic(){
        showTraffic = !showTraffic;
        mAMap.setTrafficEnabled(showTraffic);
        if (showTraffic){
            ToastUtils.showToast("开始并更新实时路况");
        }else {
            ToastUtils.showToast("关闭实时路况");
        }
    }

}
