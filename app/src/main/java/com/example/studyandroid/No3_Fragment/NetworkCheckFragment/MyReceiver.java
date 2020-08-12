package com.example.studyandroid.No3_Fragment.NetworkCheckFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(NetworkCheckFragment.ACTION_CHECK_INTERNET);
        i.putExtra(NetworkCheckFragment.KEY_CHECK_INTERNET, NetworkCheckFragment.isInternetConnected(context));

        // 연결 변경 알림
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }
}
