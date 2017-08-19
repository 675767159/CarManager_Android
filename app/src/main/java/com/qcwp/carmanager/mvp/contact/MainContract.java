package com.qcwp.carmanager.mvp.contact;


import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.model.sqLiteModel.CarVinStatisticModel;
import com.qcwp.carmanager.model.sqLiteModel.SingleCarVinStatisticModel;

/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void onSuccessGetMyAllCarInfo(CarInfoModel carInfoModel);
    }

    interface Presenter  {

        void getMyAllCarInfo();
        CarInfoModel getLatestCarInfo();
        CarInfoModel getCarInfoByVinCode(String vinCode);
        CarVinStatisticModel getCarVinStatistic(String vinCode);
        SingleCarVinStatisticModel getSingleCarVinStatistic(String vinCode);
    }
}
