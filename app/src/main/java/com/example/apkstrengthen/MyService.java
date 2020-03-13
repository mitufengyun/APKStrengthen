package com.example.apkstrengthen;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


public class MyService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("xpf", "service:" + getApplication());
        Log.i("xpf", "service:" + getApplicationContext());
        Log.i("xpf", "service:" + getApplicationInfo().className);
    }
}
