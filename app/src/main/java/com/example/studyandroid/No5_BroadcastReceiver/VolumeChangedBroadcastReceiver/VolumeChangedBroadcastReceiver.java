package com.example.studyandroid.No5_BroadcastReceiver.VolumeChangedBroadcastReceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.studyandroid.R;

/*

[ BroadcastReceiver ]
 : 어떤 이벤트가 발생한 사실을 앱에 알릴 때 도와주는 컴포넌트

 - 예) 단말기 전원 On, 디스크 용량 부족, 음량변화, 네트워크상태변화 등
 - 위처럼 시스템 이벤트 뿐만 아니라 앱 간의 연계를 위해 이벤트를 알릴 때에도 사용됨
--------------------------------------------------------------------------

[ BroadcastReceiver _ Basic Concept ]

 - BroadcastReceiver 는 브로드캐스트 Intent 를 받았을 때, onReceive() 에서 이를 처리한다.
 - 이 때, 어느 브로드캐스트에서 보낸 Intent 를 받을지는 IntentFilter 로 정의한다.

 1. 브로드캐스트 Intent 를 받는다.
 2. Intent.getAction() 을 호출하여 액션의 이름을 가져온다.
 3. 주어진 데이터가 있다면, Intent.getExtras() 를 호출하여 Bundle 을 가져오고 이를 처리한다.
 4. 주의할 점 : onReceive() 는 메인 스레드에서 이루어짐 -> 10초 이상의 시간 소요 불가 -> ANR발생 -> 강제 종료됨
--------------------------------------------------------------------------

[ BroadcastReceiver _ 등록방법 ]

 1. Manifest 에 receiver 태그를 이용해 등록
 2. Context.registerReceiver() 를 이용해 실행 시 등록 : onResume() 에서 등록
    Context.unregisterReceiver() 를 이용해 해제 : onPause() 에서 해제
--------------------------------------------------------------------------

 */
public class VolumeChangedBroadcastReceiver extends AppCompatActivity {

    private static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            intent.getExtras();

            if (TextUtils.equals(action, VOLUME_CHANGED_ACTION)) {
                Toast.makeText(context, "음량이 변경되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_changed_broadcast_receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 실행 시 Receiver 직접 등록
        IntentFilter filterOn = new IntentFilter(VOLUME_CHANGED_ACTION);
        registerReceiver(mReceiver, filterOn);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Reveiver 이용완료 후 직접 해제
        unregisterReceiver(mReceiver);
    }
}
