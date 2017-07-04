package com.qcwp.carmanager.model.retrofitModel;

import com.qcwp.carmanager.model.sqLiteModel.CarBrandModel;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarSeriesModel;
import com.qcwp.carmanager.model.sqLiteModel.CarTypeModel;
import com.qcwp.carmanager.model.sqLiteModel.CommonBrandModel;

import java.util.List;

/**
 * Created by qyh on 2017/5/31.
 */

public class TokenModel {


    private String token;
    private int status;

    public String getToken() {
        return token;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    private String msg;



}
