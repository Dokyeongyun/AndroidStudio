package com.example.studyandroid.No6_Service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.studyandroid.R;
/*

[ Service ]
 : 액티비티, 프래그먼트와 달리 화면에 표시되지 않아도 백그라운드 처리를 할 수 있게 하는 컴포넌트
------------------------------------------------------------------------------------

[ Service _ 종류 ]
 1. 백그라운드에서 동작하는 Service -> Context.startService() 로 시작
 2. Binder 를 통해 바인드하는 Service -> Context.bindService() 로 바인드
 3. 서로다른 앱 간의 연계 Service -> AIDL (Android Interface Definition Language) 이용
    이 방법은 각 앱의 Service 별로 실행되는 쓰레드가 다르며 구현 난도가 높아진다.
------------------------------------------------------------------------------------

[ Service _ 생명주기 ]
                시작              |                      표시(일시정지)                      |     폐기
 startService()     onCreate()   |   onStartCommand()                                    |    onDestroy()
 bindService()      onBind()     |   ServiceConnection.onServiceConnected()  onUnbind() |    onDestroy()
------------------------------------------------------------------------------------

[ 액티비티가 켜져있지 않아도 백그라운드에서 음악이 실행되는 서비스 만들기 ]

 1. 앱이 시작되면 서비스에 바인드하기
 2. 현재 음악이 재생중이면 -> 다른 액티비티로 넘어가도 계속 재생하게 하기 -> startService()
 3. 현재 음악이 정지중이면 -> 정지
 4. 서비스와 연결되면 콜백되는 메소드 ServiceConnection.onServiceConnected() 를 재정의하고 bindService() 를 이용해 연결한다.

 */
public class MusicPlayerService_MainActivity extends AppCompatActivity {

    private Boolean mIsPlaying;
    private View mBtnPlay;
    private View mBtnStop;
    private BackgroundMusicService mServiceBinder;
    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceBinder = ((BackgroundMusicService.MyBinder) service).getService();
            updateButtonEnabled();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBinder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_service__main);

        mBtnPlay = findViewById(R.id.btn_play);
        mBtnStop = findViewById(R.id.btn_stop);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(mServiceBinder ==null){
            // 서비스 바인드
            doBindService();
            // 백그라운드에서 음악 플레이어가 실행될 수 있도록 startService() 로 서비스 시작
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mServiceBinder !=null){
            mIsPlaying = mServiceBinder.isPlaying();

            if(!mIsPlaying){
                mServiceBinder.stopSelf();
            }

            unbindService(myConnection);
            mServiceBinder = null;
        }
    }

    // 서비스에 바인드
    public void doBindService(){
        Intent intent = null;
        intent = new Intent(this, BackgroundMusicService.class);
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    private void updateButtonEnabled(){
        if(mServiceBinder!=null){
            if(mServiceBinder.isPlaying()){
                mBtnPlay.setEnabled(false);
                mBtnStop.setEnabled(true);
            }else{
                mBtnPlay.setEnabled(true);
                mBtnStop.setEnabled(false);
            }
        }else{
            mBtnPlay.setEnabled(false);
            mBtnStop.setEnabled(false);
        }
    }

    public void musicPlayOnClick(View view) {
        switch(view.getId()){
            case R.id.btn_play:
                if(mServiceBinder !=null){
                    mServiceBinder.play();
                }
                updateButtonEnabled();
                break;
            case R.id.btn_stop:
                if(mServiceBinder!=null){
                    mServiceBinder.stop();
                }
                updateButtonEnabled();
        }
    }
}
