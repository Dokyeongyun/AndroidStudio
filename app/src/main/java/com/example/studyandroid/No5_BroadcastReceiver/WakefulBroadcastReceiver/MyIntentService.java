package com.example.studyandroid.No5_BroadcastReceiver.WakefulBroadcastReceiver;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.legacy.content.WakefulBroadcastReceiver;

/*
 IntentService 는 처리를 백그라운드 스레드에서 실행해주는 Service
 처리가 끝나면 자동으로 종료됨
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 처리 내용 기술

        try{
            Log.d("MyIntentService","onHandleIntent START");

            // 30초 슬립 설정
            Thread.currentThread();
            Thread.sleep(30*1000);

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("test", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("KEY", "SAVED");
            editor.apply();
            Log.d("MyIntentService", "onHandleIntent SAVED");

        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("MyIntentService", "Error");

        }finally{
            Log.d("MyIntentService","onHandleIntent FINALLY");

            // Service 의 처리가 종료된 후 WakefulBroadcastReceiver.completeWakefulIntent() 를 이용하여 WakeLock 을 해제
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }
}
