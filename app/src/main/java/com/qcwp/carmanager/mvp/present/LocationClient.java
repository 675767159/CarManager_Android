package com.qcwp.carmanager.mvp.present;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.TimeUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.broadcast.LocationEvent;
import com.qcwp.carmanager.enumeration.OBDConnectStateEnum;
import com.qcwp.carmanager.enumeration.UploadStatusEnum;
import com.qcwp.carmanager.greendao.gen.DaoSession;
import com.qcwp.carmanager.implement.MyLocationListener;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.LocationModel;
import com.qcwp.carmanager.mvp.contact.LocationContract;
import com.qcwp.carmanager.obd.OBDClient;
import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.qcwp.carmanager.utils.CommonUtils.MAX;
import static com.qcwp.carmanager.utils.CommonUtils.MIN;

/**
 * Created by qyh on 2017/9/11.
 *
 * @email:675767159@qq.com
 */

public class LocationClient implements LocationContract {




    private static LocationClient INSTANCE;
    // 提供一个全局的静态方法
    public static LocationClient getDefaultClien() {
        if (INSTANCE == null) {
            synchronized (LocationClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocationClient();
                }
            }
        }
        return INSTANCE;
    }




    public com.baidu.location.LocationClient mLocationClient = null;
    private List<LatLng> mapPointArray;
    private String travelStartTime,userName,vinCode;
    private DaoSession mDaoSession;

    private    double minLat = 90.0,maxLat = -90.0,minLon = 180.0,maxLon = -180.0;
    private LocationClient(){
        mLocationClient = new com.baidu.location.LocationClient(APP.getInstance());

        BDLocationListener myListener = new MyLocationListener(this);
        //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        //注册监听函数

        LocationClient.this.initData();
        LocationClient.this.initLocation();
    }

    private void initData(){
        mapPointArray=new ArrayList();
        travelStartTime=OBDClient.getDefaultClien().getStartTime();
        userName= UserData.getInstance().getUserName();
        vinCode=OBDClient.getDefaultClien().getVinCode();
        mDaoSession=APP.getInstance().getDaoInstant();
    }

    private void initLocation(){

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=5000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

//        option.setIsNeedAddress(true);
//        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

//        option.setLocationNotify(false);
//        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//
//        option.setIsNeedLocationDescribe(true);
//        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//
//        option.setIsNeedLocationPoiList(true);
//        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死



        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        option.setWifiCacheTimeOut(5*60*1000);
        //可选，7.2版本新增能力，如果您设置了这个接口，首次启动定位时，会先判断当前WiFi是否超出有效期，超出有效期的话，会先重新扫描WiFi，然后再定位

        mLocationClient.setLocOption(option);
    }

    public void startLocation(){

        mLocationClient.start();
    }


    @Override
    public void didUpdateBMKUserLocation(final double latitude, final double longitude) {

        Print.d("didUpdateBMKUserLocation", "latitude==" + latitude + "longitude==" + longitude);

        if (OBDClient.getDefaultClien().getConnectStatus() == OBDConnectStateEnum.connectTypeHaveBinded) {

            LocationModel locationModel=new LocationModel();
            locationModel.setLatitude(latitude);
            locationModel.setLongitude(longitude);
            locationModel.setCurrentTime(TimeUtils.getNowString());
            locationModel.setCreateTime(travelStartTime);
            locationModel.setUploadFlag(UploadStatusEnum.NotUpload);
            locationModel.setVinCode(vinCode);
            locationModel.setUserName(userName);
            mDaoSession.insert(locationModel);





            LatLng location = new LatLng(latitude, longitude);
            mapPointArray.add(location);


            if (mapPointArray.size()>2000){
                mLocationClient.stop();

                List arr = new ArrayList();
                for(int i = 0 ; i <mapPointArray.size(); i++)
                {
                    if (i % 2 == 0)
                    {
                        arr.add(mapPointArray.get(i));
                    }
                }
                mapPointArray.clear();
                mapPointArray=arr;

                mLocationClient.start();
            }




            minLat = MIN(minLat, latitude);
            maxLat = MAX(maxLat, latitude);
            minLon = MIN(minLon, longitude);
            maxLon = MAX(maxLon, longitude);

            LocationEvent locationEvent=new LocationEvent();
            locationEvent.setMapPoints(mapPointArray);
            locationEvent.setCenterLatLng(new LatLng( (minLat + maxLat) * 0.5f, (minLon + maxLon) * 0.5f));
            EventBus.getDefault().post(locationEvent);

        }


    }


}
