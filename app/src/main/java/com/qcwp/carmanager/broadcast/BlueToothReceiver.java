package com.qcwp.carmanager.broadcast;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qcwp.carmanager.utils.CommonUtils;
import com.qcwp.carmanager.utils.Print;

import org.greenrobot.eventbus.EventBus;

import static com.qcwp.carmanager.broadcast.MessageEvent.MessageEventType.BlueToothScaned;


/**
 * Created by qyh on 2016/12/26.
 */

public class BlueToothReceiver extends BroadcastReceiver {
    public static Boolean gotOBD=false;
    @Override
    public void onReceive(Context context, Intent intent) {

        Print.d("BlueToothReceiver", intent.getAction());

        switch (intent.getAction()){
            case BluetoothDevice.ACTION_FOUND :
            case BluetoothDevice.ACTION_NAME_CHANGED:
                {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                this.isOBDBluetoothDevice(device);
            }
                break;
        }


    }

    private synchronized void  isOBDBluetoothDevice(BluetoothDevice device){
        String name=device.getName();
        Print.d("BlueToothReceiver",name+"---");
        if (name!=null&&name.contains("OBD")) {
            if (!gotOBD) {
                gotOBD=true;
                EventBus.getDefault().post(new MessageEvent(BlueToothScaned, device.getAddress()));
            }

        }
    }
}
