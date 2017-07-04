package com.qcwp.carmanager.model.retrofitModel;

import com.qcwp.carmanager.model.sqLiteModel.CarBrandModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarSeriesModel;
import com.qcwp.carmanager.model.sqLiteModel.CarTypeModel;
import com.qcwp.carmanager.model.sqLiteModel.CommonBrandModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qyh on 2017/5/31.
 */

public class AllCarModel {

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<CarBrandModel> getBrands() {
        return brands;
    }

    /**
     * username : quyongheng
     * status : 1
     * memberId : 53696

     */
    private int status;

    public List<CommonBrandModel> getCommonBrands() {
        return commonBrands;
    }

    private String msg;

    public List<CarInfoModel> getVins() {
        return vins;
    }

    private List<CarBrandModel> brands;
    private List<CarInfoModel> vins;
    private List<CommonBrandModel> commonBrands;
    private List<CarSeriesModel>carSerieses;
    private List<CarTypeModel>carTypes;

    public List<CarTypeModel> getCarTypes() {
        return carTypes;
    }

    public List<CarSeriesModel> getCarSerieses() {
        return carSerieses;
    }
}
