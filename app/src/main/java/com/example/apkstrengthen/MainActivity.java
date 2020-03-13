package com.example.apkstrengthen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("xpf","activity:"+getApplication());
        Log.i("xpf","activity:"+getApplicationContext());
        Log.i("xpf","activity:"+getApplicationInfo().className);

        startService(new Intent(this, MyService.class));

        Intent intent = new Intent("com.example.apkstrengthen.MyBroadCastReceiver.test");
        intent.setComponent(new ComponentName(getPackageName(), MyBroadCastReceiver.class.getName
                ()));
        sendBroadcast(intent);

        getContentResolver().delete(Uri.parse("content://com.example.apkstrengthen.MyProvider"), null,
                null);
    }
}
