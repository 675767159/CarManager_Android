package com.qcwp.carmanager.implement;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.qcwp.carmanager.mvp.contact.LocationContract;
import com.qcwp.carmanager.utils.Print;

/**
 * Created by qyh on 2017/9/10.
 *
 * @email:675767159@qq.com
 */

public class MyLocationListener extends BDAbstractLocationListener {

    private LocationContract.Presenter delegate;
    public MyLocationListener(LocationContract.Presenter delegate){
        this.delegate=delegate;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {

//        //获取定位结果
//        location.getTime();    //获取定位时间
//        location.getLocationID();    //获取定位唯一ID，v7.2版本新增，用于排查定位问题
//        location.getLocType();    //获取定位类型
        double latitude=location.getLatitude();    //获取纬度信息
        double longitude= location.getLongitude();    //获取经度信息
//        location.getRadius();    //获取定位精准度
//        location.getAddrStr();    //获取地址信息
//        location.getCountry();    //获取国家信息
//        location.getCountryCode();    //获取国家码
//        location.getCity();    //获取城市信息
//        location.getCityCode();    //获取城市码
//        location.getDistrict();    //获取区县信息
//        location.getStreet();    //获取街道信息
//        location.getStreetNumber();    //获取街道码
//        location.getLocationDescribe();    //获取当前位置描述信息
//        location.getPoiList();    //获取当前位置周边POI信息
//
//        location.getBuildingID();    //室内精准定位下，获取楼宇ID
//        location.getBuildingName();    //室内精准定位下，获取楼宇名称
//        location.getFloor();    //室内精准定位下，获取当前位置所处的楼层信息

        delegate.didUpdateBMKUserLocation(latitude,longitude);

        if (location.getLocType() == BDLocation.TypeGpsLocation){

            //当前为GPS定位结果，可获取以下信息
            location.getSpeed();    //获取当前速度，单位：公里每小时
            location.getSatelliteNumber();    //获取当前卫星数
            location.getAltitude();    //获取海拔高度信息，单位米
            location.getDirection();    //获取方向信息，单位度

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

            //当前为网络定位结果，可获取以下信息
            location.getOperators();    //获取运营商信息

        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

            //当前为网络定位结果

        } else if (location.getLocType() == BDLocation.TypeServerError) {

            //当前网络定位失败
            //可将定位唯一ID、IMEI、定位失败时间反馈至loc-bugs@baidu.com

        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

            //当前网络不通

        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

            //当前缺少定位依据，可能是用户没有授权，建议弹出提示框让用户开启权限
            //可进一步参考onLocDiagnosticMessage中的错误返回码

        }
    }

    /**
     * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
     * 自动回调，相同的diagnosticType只会回调一次
     *
     * @param locType           当前定位类型
     * @param diagnosticType    诊断类型（1~9）
     * @param diagnosticMessage 具体的诊断信息释义
     */
    public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {

        Print.d("onLocDiagnosticMessage",diagnosticType+"--"+diagnosticMessage);
        if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_GPS) {

            //建议打开GPS

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_WIFI) {

            //建议打开wifi，不必连接，这样有助于提高网络定位精度！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_LOC_PERMISSION) {

            //定位权限受限，建议提示用户授予APP定位权限！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_NET) {

            //网络异常造成定位失败，建议用户确认网络状态是否异常！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CLOSE_FLYMODE) {

            //手机飞行模式造成定位失败，建议用户关闭飞行模式后再重试定位！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_INSERT_SIMCARD_OR_OPEN_WIFI) {

            //无法获取任何定位依据，建议用户打开wifi或者插入sim卡重试！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_OPEN_PHONE_LOC_SWITCH) {

            //无法获取有效定位依据，建议用户打开手机设置里的定位开关后重试！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_SERVER_FAIL) {

            //百度定位服务端定位失败
            //建议反馈location.getLocationID()和大体定位时间到loc-bugs@baidu.com

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_FAIL_UNKNOWN) {

            //无法获取有效定位依据，但无法确定具体原因
            //建议检查是否有安全软件屏蔽相关定位权限
            //或调用LocationClient.restart()重新启动后重试！

        }
    }
}
