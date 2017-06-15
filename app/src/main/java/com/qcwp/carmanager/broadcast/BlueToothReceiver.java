package com.qcwp.carmanager.broadcast;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qcwp.carmanager.utils.Print;


/**
 * Created by qyh on 2016/12/26.
 */

public class BlueToothReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        Print.d("BlueToothReceiver",intent.getAction());

        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(intent.getAction())) {// 搜索到蓝牙
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Print.d("BlueToothReceiver",device.getName());
        }
    }
}
