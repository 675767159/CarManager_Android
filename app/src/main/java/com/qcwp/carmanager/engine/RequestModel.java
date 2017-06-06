package com.qcwp.carmanager.engine;


import com.blankj.utilcode.util.EmptyUtils;

/**
 * Created by qyh on 2016/11/18.
 */

public class RequestModel {
   public String data;
   public String msg;
   public int status;
   public boolean isSuccess=false;

   public static RequestModel  HandlerData(retrofit2.Response<RequestModel> response) {

      RequestModel  model=response.body();
      if (response.isSuccessful()&&model!=null){

            if (model.status==1) {
               model.isSuccess = true;
            } else {
               model.isSuccess = false;
            }
      }
      else {
         model=new  RequestModel();
         model.isSuccess=false;
         if (EmptyUtils.isEmpty(response.message())){
            model.msg="检查地址是否正确(有些地址不能自动消除多余斜杠)";
         }else {
            model.msg = response.message();
         }

      }
      return model;
   }


}
