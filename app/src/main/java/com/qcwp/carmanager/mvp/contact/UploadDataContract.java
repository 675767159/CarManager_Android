package com.qcwp.carmanager.mvp.contact;



/**
 * Created by qyh on 2017/7/10.
 *
 * @email:675767159@qq.com
 */

public interface UploadDataContract {
    interface View{

        void uploadDriveDataComplete();
        void uploadMapPointOfDriveDataComplete();
        void uploadDrivingCustomComplete();
        void uploadPhysicalExaminationComplete();
    }


    interface Presenter  {

         void uploadDriveData();
         void uploadMapPointOfDriveData();
         void uploadDrivingCustom();
         void uploadPhysicalExamination();

    }
}
