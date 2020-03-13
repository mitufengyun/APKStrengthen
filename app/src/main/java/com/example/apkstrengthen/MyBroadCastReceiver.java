package com.example.apkstrengthen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MyBroadCastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("xpf", "receiver:" + context);
        Log.i("xpf","receiver:" + context.getApplicationContext());
        Log.i("xpf","receiver:" + context.getApplicationInfo().className);

    }
}
