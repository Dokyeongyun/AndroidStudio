package com.example.studyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studyandroid.No1_Activity.ConfigChanged;
import com.example.studyandroid.No2_ViewAndLayout.CompositeCustomView;
import com.example.studyandroid.No3_Fragment.DynamicFragment.Fragment_DynamicUsing;
import com.example.studyandroid.No3_Fragment.NestedFragment.NestedFragment_MainActivity;
import com.example.studyandroid.No3_Fragment.NetworkCheckFragment.NetworkCheck_MainActivity;
import com.example.studyandroid.No3_Fragment.StaticFragment.Fragment_StaticUsing;
import com.example.studyandroid.No4_ContentProvider.MediaStoreContentProvider.ContentProvider_MainActivity;
import com.example.studyandroid.No4_ContentProvider.WordOfToday2.WordOfToday_MainActivity;
import com.example.studyandroid.No5_BroadcastReceiver.VolumeChangedBroadcastReceiver.VolumeChangedBroadcastReceiver;
import com.example.studyandroid.No5_BroadcastReceiver.WakefulBroadcastReceiver.WakefulBroadcastReceiver_MainActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void startOnClick(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.no1_configChanged:
                intent = new Intent(this, ConfigChanged.class);
                startActivity(intent);
                break;
            case R.id.no2_customView:
                intent = new Intent(this, CompositeCustomView.class);
                startActivity(intent);
                break;
            case R.id.no3_staticFragment:
                intent = new Intent(this, Fragment_StaticUsing.class);
                startActivity(intent);
                break;
            case R.id.no3_dynamicFragment:
                intent = new Intent(this, Fragment_DynamicUsing.class);
                startActivity(intent);
                break;
            case R.id.no3_networkCheck:
                intent = new Intent(this, NetworkCheck_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no3_nestedFragment:
                intent = new Intent(this, NestedFragment_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no4_mediaStore:
                intent = new Intent(this, ContentProvider_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no4_wordOfToday:
                intent = new Intent(this, WordOfToday_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no5_volumeChangedBR:
                intent = new Intent(this, VolumeChangedBroadcastReceiver.class);
                startActivity(intent);
                break;
            case R.id.no5_wakefulBR:
                intent = new Intent(this, WakefulBroadcastReceiver_MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
