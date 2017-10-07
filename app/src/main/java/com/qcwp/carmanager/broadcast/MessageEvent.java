package com.qcwp.carmanager.broadcast;

import java.util.Map;

/**
 * Created by qyh on 2017/6/23.
 */

public class MessageEvent {

    private MessageEventType type;
    private String message;
    private Map data;

    public MessageEvent(){

    }

    public MessageEvent(MessageEventType type, String message) {
        this.type = type;
        this.message = message;
    }

    public MessageEventType getType() {
        return type;
    }

    public void setType(MessageEventType type) {
        this.type = type;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   public enum MessageEventType{
       OBDConnectSuccess,
       CarBlindSuccess,
       CarCrash,
       CarDataUpdate,
       BlueToothScaned,
       WIFIScaned,
       OBDLostDisconnection,
       BlueRequestResult,
       WIFILocationRequestResult,
       BluetoothLocationRequestResult,
       CarSelected,
       Driving,
       CarCheck_some,
       CarCheck_faultCode,
       CarCheck_engineCondition,
       CarCheck_driveData,
       CarCheck_end,
       CarTest,
       OverSpeed,
       NormalSpeed


   }
}
