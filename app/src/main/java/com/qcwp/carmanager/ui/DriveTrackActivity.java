package com.qcwp.carmanager.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.R;
import com.qcwp.carmanager.broadcast.LocationEvent;
import com.qcwp.carmanager.broadcast.MessageEvent;
import com.qcwp.carmanager.control.TouchHighlightImageView;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.greendao.gen.LocationModelDao;
import com.qcwp.carmanager.implement.MyLocationListener;
import com.qcwp.carmanager.model.LoginModel;
import com.qcwp.carmanager.model.sqLiteModel.LocationModel;
import com.qcwp.carmanager.model.sqLiteModel.TravelSummaryModel;
import com.qcwp.carmanager.mvp.contact.LocationContract;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

import static com.qcwp.carmanager.utils.CommonUtils.MAX;
import static com.qcwp.carmanager.utils.CommonUtils.MIN;

public class DriveTrackActivity extends BaseActivity implements LocationContract{


    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.totalMileage)
    TextView totalMileage;
    @BindView(R.id.distance)
    TextView distance;
    @BindView(R.id.driveTime)
    TextView driveTime;
    @BindView(R.id.averageSpeed)
    TextView averageSpeed;
    @BindView(R.id.avgOilConsume)
    TextView avgOilConsume;

    @BindView(R.id.record)
    TouchHighlightImageView btnRecord;

    private OBDClient obdClient;
    private Locale locale;
    private LocationClient mLocationClient;
    private BaiduMap mBaiduMap;
    private Boolean firstAppear=true;
    @Override
    protected int getContentViewLayoutID() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        return R.layout.activity_drive_track;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        obdClient=OBDClient.getDefaultClien();
        locale = Locale.getDefault();
        mBaiduMap=mapView.getMap();


        if (obdClient.getConnectStatus()!=OBDConnectStateEnum.connectTypeHaveBinded) {
            startGetCurrentLocation();
        }


        Intent intent=getIntent();
        if (intent.getStringExtra("travelSummaryModel")!=null){
            String travelSummaryModelStr=intent.getStringExtra("travelSummaryModel");
            TravelSummaryModel travelSummaryModel=new Gson().fromJson(travelSummaryModelStr, TravelSummaryModel.class);
            initDisplay(travelSummaryModel);
            initLocationData(travelSummaryModel.getStartDate());
            btnRecord.setEnabled(false);

        }


    }

    private void startGetCurrentLocation(){

        mLocationClient =new  LocationClient(this);

        BDLocationListener myListener = new MyLocationListener(this);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        if (obdClient.getConnectStatus()!= OBDConnectStateEnum.connectTypeHaveBinded) {
            mLocationClient.start();
        }

    }

    private void initPosition(LatLng cenpt){

        //设定中心点坐标

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(12)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);



    }

    @Override
    protected void onReceiveMessageEvent(MessageEvent messageEvent) {

        switch (messageEvent.getType()) {
            case Driving:
                this.updateDisplay();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocationEvent event) {

        this.updateMapTrack(event.getMapPoints(),event.getCenterLatLng());
    }


    private void updateDisplay(){

        totalMileage.setText(String.format(locale, "%.1f KM", obdClient.getTotalMileage()));
        distance.setText(String.valueOf(String.format(locale, "%.1f KM", obdClient.getDist())));
        driveTime.setText(CommonUtils.longTimeToStr((int)obdClient.getTotalTime()));
        averageSpeed.setText(String.format(locale, "%.0f KM/H", obdClient.getAvgVehicleSpeed()));
        avgOilConsume.setText(String.format(locale, "%.2f L/100KM", obdClient.getAvgOilConsume()));
    }

    private void initDisplay(TravelSummaryModel travelSummaryModel){

        totalMileage.setText("-");
        distance.setText(String.valueOf(String.format(locale, "%.1f KM", travelSummaryModel.getMileage())));
        driveTime.setText(CommonUtils.longTimeToStr(travelSummaryModel.getDriveTime()));
        averageSpeed.setText(String.format(locale, "%.0f KM/H", travelSummaryModel.getAverageSpeed()));
        avgOilConsume.setText(String.format(locale, "%.2f L/100KM", travelSummaryModel.getAverageOilConsume()));
    }


    private void updateMapTrack(List<LatLng> mapPoints,LatLng latLng){
        mBaiduMap.clear();

        if (mapPoints.size()<2){
            return;
        }
        LatLng startLocation=mapPoints.get(0);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.drive_track_icon_nav_start);
      //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(startLocation)
                .icon(bitmap);
      //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);




        LatLng endLocation=mapPoints.get(mapPoints.size()-1);
        //构建Marker图标
        bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.drive_track_icon_nav_end);
        //构建MarkerOption，用于在地图上添加Marker
         option = new MarkerOptions()
                .position(endLocation)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);


        if (firstAppear) {
            initPosition(latLng);
            firstAppear=false;
        }



        OverlayOptions ooPolyline = new PolylineOptions().width(8)
                .points(mapPoints).color(R.color.red);
        Polyline mPolyline = (Polyline) mapView.getMap().addOverlay(ooPolyline);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
        firstAppear=true;
    }


    @Override
    public void didUpdateBMKUserLocation(double latitude, double longitude) {
        mLocationClient.stop();
        LatLng latLng=new LatLng(latitude,longitude);
        initPosition(latLng);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.record:
                readyGoForResult(TrackRecordActivity.class,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==1){
            String travelSummaryModelStr=data.getStringExtra("travelSummaryModel");
            TravelSummaryModel travelSummaryModel=new Gson().fromJson(travelSummaryModelStr, TravelSummaryModel.class);
            initDisplay(travelSummaryModel);
            initLocationData(travelSummaryModel.getStartDate());


        }

    }


    private void initLocationData(String startDate){
      List<LocationModel> locationModels=mDaoSession.queryBuilder(LocationModel.class).where(LocationModelDao.Properties.CreateTime.eq(startDate)).orderAsc(LocationModelDao.Properties.CurrentTime).list();
        double minLat = 90.0,maxLat = -90.0,minLon = 180.0,maxLon = -180.0;

        List<LatLng>  mapPointArray=new ArrayList<>();
        for (LocationModel locationModel:locationModels){
            double latitude=locationModel.getLatitude();
            double longitude=locationModel.getLongitude();
            minLat = MIN(minLat, latitude);
            maxLat = MAX(maxLat, latitude);
            minLon = MIN(minLon, longitude);
            maxLon = MAX(maxLon, longitude);
            LatLng location = new LatLng(latitude, longitude);
            mapPointArray.add(location);
        }
        LatLng center=new LatLng((minLat + maxLat) * 0.5f, (minLon + maxLon) * 0.5f);

        updateMapTrack(mapPointArray,center);

    }
}
