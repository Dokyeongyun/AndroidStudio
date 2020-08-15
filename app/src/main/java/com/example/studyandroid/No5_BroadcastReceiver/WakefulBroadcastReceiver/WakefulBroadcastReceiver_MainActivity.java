package com.example.studyandroid.No5_BroadcastReceiver.WakefulBroadcastReceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyandroid.R;
/*

[ BroadcastReceiver _ WakeLock ]

 - 브로드캐스트를 수신하여 처리할 때 주의할 점으로, 단말기가 슬립상태에 들어가는 점을 고려해야한다는 것이 있다.
     안드로이드는 절전을 위해 일정 시간이 지나거나 사용자가 화면을 끄면 슬립상태로 들어간다.
     이 때, 브로드캐스트를 수신 후 처리하던 작업이 종료되면 안되는 경우가 있을 수도 있는데,
     이를 위해 WakeLock 을 사용한다.

 - 단말기 상태와 그에 따른 WakeLock 의 종류는 다음과 같다.
    [ 1. 화면 ON (밝음), CPU ON :    FULL_WAKE_LOCK ]
    [ 2. 화면 ON (어두움), CPU ON :  SCREEN_DIM_WAKE_LOCK ]
    [ 3. 화면 OFF, CPU ON :         PARTIAL_WAKE_LOCK ]
    [ 4. 화면 OFF, CPU OFF :        없음 ]

    * FULL_WAKE_LOCK, SCREEN_DIM_WAKE_LOCK 은 deprecated
        -> WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON 을 이용

 - WakeLock 을 사용하기 위해 Manifest 에 퍼미션 이용을 선언해야 함
 - WakeLock 을 얻고 이용 후 해제하지 않으면 CPU 가 슬립하지 않아 전력이 많이 소비됨
    -> 지원 라이브러리의 WakefulBroadcastReceiver 클래스를 이용! (처리 끝난 후 자동 해제)
 - 단, WakefulBroadcastReceiver 로 얻은 WakeLock 은 60초의 타임아웃이 있어 60초 후 슬립상태로 전환됨

 */
public class WakefulBroadcastReceiver_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeful_broadcast_receiver__main);


/*
        // 이전에 설정했던 것을 모두 clear, apply
        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
*/

        (new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.currentThread().sleep(30*1000);
                    sendBroadcast(new Intent("com.example.studyandroid.No5_BroadcastReceiver.WakefulBroadcastReceiver.TEST_ACTION"));
                } catch (InterruptedException e) {
                    Toast.makeText(WakefulBroadcastReceiver_MainActivity.this, "오류", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        })).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        String result = sharedPreferences.getString("KEY",null);
        TextView textView = findViewById(R.id.BRText);

        if(result ==null){
            textView.setText("휴대폰이 슬립상태가 되도록 기다려주세요.\n (단, 30초이내에 슬립상태가 되도록 설정해야 함)");
        }else{
            textView.setText("MyIntentService 의 처리가 완료되었습니다.");
        }
    }
}
