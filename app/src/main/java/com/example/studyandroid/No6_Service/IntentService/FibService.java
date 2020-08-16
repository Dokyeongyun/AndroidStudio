package com.example.studyandroid.No6_Service.IntentService;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/*
  백그라운드에서 피보나치 수열을 계산하는 IntentService

  onHandleIntent() 의 내용이 워커 스레드에서 실행됨
    * 워커 스레드 != 메인 스레드
 */
public class FibService extends IntentService {

    // 서비스 액션
    static final String ACTION_CALC = "ACTION_CALC";
    // 브로드캐스트 액션
    static final String ACTION_CALC_DONE = "ACTION_CALC_DONE";
    // 브로드캐스트로 계산 결과를 주고받기 위한 키
    static final String KEY_CALC_RESULT = "KEY_CALC_RESULT";
    // 브로드캐스트로 계산에 걸린 시간(초)을 주고받기 위한 키
    static final String KEY_CALC_MILLISECONDS = "KEY_CALC_MILLISECONDS";

    // 피보나치 수열 계산
    static final int N = 40;

    public FibService() {
        super("FibService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

            // MainActivity 에서 Intent 에 ACTION_CALC 담아 FibService 시작함
            if (ACTION_CALC.equals(action)) {
                long start = System.nanoTime();
                int result = fib(N);
                long end = System.nanoTime();

                // Intent 에 결과 넣어 전송
                Intent resultIntent = new Intent(ACTION_CALC_DONE);
                resultIntent.putExtra(KEY_CALC_RESULT, result);
                resultIntent.putExtra(KEY_CALC_MILLISECONDS, (end - start) / 1000 / 1000);

                // LocalBroadcastManager 를 이용해 액티비티로 Intent 전송
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(resultIntent);
            }
        }
    }

    // 재귀호출을 이용한 피보나치 수열 계산
    private static int fib(int n){
        return n<=1 ? n: fib(n-1) + fib(n-2);
    }
}

