package com.example.studyandroid.No6_Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.studyandroid.R;

public class BackgroundMusicService extends Service {
    private static final String TAG =  BackgroundMusicService.class.getSimpleName();

    private final IBinder mBinder = new MyBinder();
    private MediaPlayer mPlayer;

    public BackgroundMusicService(){}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        BackgroundMusicService getService() {
            return BackgroundMusicService.this;
        }
    }

    public boolean isPlaying(){
        boolean isPlaying = false;

        if(mPlayer !=null){
            isPlaying = mPlayer.isPlaying();
        }
        return isPlaying;
    }

    public void play(){
        mPlayer = MediaPlayer.create(this, R.raw.bensound_clearday);
        mPlayer.setLooping(true);
        mPlayer.setVolume(100,100);
        mPlayer.start();
    }

    public void stop(){
        if(mPlayer.isPlaying()){
            mPlayer.stop();     // 노래 중지
            mPlayer.release();  // MediaPlayer 해제
            mPlayer = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
}
