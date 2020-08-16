package com.example.studyandroid.No6_Service.IntentService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.studyandroid.R;

/*

[ IntentService ]
 : 액티비티, 프래그먼트의 생명주기에 의존하지 않고 백그라운드에서 처리하고 싶은 경우 사용
------------------------------------------------------------------------------

[ 피보나치 수열을 백그라운드에서 계산하고, 그 결과를 LocalBroadcastReceiver 를 이용해 액티비티에 전달하는 예쩨 ]

 1. IntentService 클래스를 상속받는 IntentService 작성 ( FibService.java )
 2. onHandlerIntent() 내에 백그라운드에서 작업할 내용을 작성 ( 이 예제에서는 피보내치 수열 계산 및 계산값, 계산시간 출력 )
 3. 작업내용의 전송은 LocalBroadcastManager 를 통해 수행 ( 앱 간의 연계가 필요없으므로 LocalBroadcastManager 이용 )
 4. 액티비티에서 FibService 를 호출 ( startService() 이용 )
 5. FibService 가 백그라운드에서 작업을 완료한 후 결과 값을 전송
 6. 액티비티에서 LocalBroadcastReceiver 를 통해 Intent 수신하여 데이터 처리

 */
public class IntentService_MainActivity extends AppCompatActivity {

    private TextView mTextView;

    private LocalBroadcastManager mLocalBroadcastManager;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // FibService 의 결과가 정상적으로 수행되었을 때 전송 값 추출
            if (FibService.ACTION_CALC_DONE.equals(action)) {
                int result = intent.getIntExtra(FibService.KEY_CALC_RESULT, -1);
                long msec = intent.getLongExtra(FibService.KEY_CALC_MILLISECONDS, -2);
                // 결과 표시
                mTextView.setText("fib(" + FibService.N  + ") = " + result + " (" + msec + ")밀리초" );
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service__main);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        mIntentFilter = new IntentFilter(FibService.ACTION_CALC_DONE);
        Intent serviceIntent = new Intent(FibService.ACTION_CALC);
        serviceIntent.setClass(getApplicationContext(), FibService.class);
        startService(serviceIntent);

        mTextView = findViewById(R.id.fibText);

        // 백그라운드에서 계산이 될 동안 임시로 띄울 텍스트
        mTextView.setText("fib(" + FibService.N + ") 계산 중...");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // LocalBroadcastReceiver 를 받음
        mLocalBroadcastManager.registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // LocalBroadcastReceiver 를 해제
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }
}
