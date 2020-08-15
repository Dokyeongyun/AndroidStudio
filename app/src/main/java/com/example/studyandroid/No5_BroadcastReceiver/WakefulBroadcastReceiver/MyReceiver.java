package com.example.studyandroid.No5_BroadcastReceiver.WakefulBroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class MyReceiver extends WakefulBroadcastReceiver {
    public MyReceiver(){ }


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, MyIntentService.class);
        Toast.makeText(context, "AA", Toast.LENGTH_SHORT).show();
        // WakeLock 을 가진 상태로 서비스를 시작하게 하는 메소드 startWakefulService()
        startWakefulService(context, serviceIntent);
    }
}
