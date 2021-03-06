package com.example.studyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studyandroid.No10_UnitTest.BmiCalculator.BmiCalculator_MainActivity;
import com.example.studyandroid.No10_UnitTest.LocalUnitTest.LocalUnitTest_MainActivity;
import com.example.studyandroid.No11_UITest.UITest_MainActivity;
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
import com.example.studyandroid.No6_Service.IntentService.IntentService_MainActivity;
import com.example.studyandroid.No6_Service.MusicPlayerService.MusicPlayerService_MainActivity;
import com.example.studyandroid.No7_SupportLibrary.NotificationCompat.NotificationCompat_MainActivity;
import com.example.studyandroid.No7_SupportLibrary.RecyclerView.RecyclerView_MainActivity;
import com.example.studyandroid.No9_Gradle.Gradle_MainActivity;
import com.example.studyandroid.no8_mvp_mvvm.DataBinding_MainActivity;

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
            case R.id.no6_musicPlayService:
                intent = new Intent(this, MusicPlayerService_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no6_fibService:
                intent = new Intent(this, IntentService_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no7_notificationCompat:
                intent = new Intent(this, NotificationCompat_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no7_recyclerView:
                intent = new Intent(this, RecyclerView_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no8_MVP_MVVP:
                ComponentName compName = new ComponentName("com.github.advanced_android.newgithubrepo", "com.github.advanced_android.newgithubrepo.RepositoryListActivity");
                Intent intent2 = new Intent(Intent.ACTION_MAIN);
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                intent2.setComponent(compName);
                startActivity(intent2);
                break;
            case R.id.no8_dataBinding:
                intent = new Intent(this, DataBinding_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no9_gradle:
                intent = new Intent(this, Gradle_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no10_localUnitTest:
                intent = new Intent(this, LocalUnitTest_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no10_bmiCalculator:
                intent = new Intent(this, BmiCalculator_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no11_UiTest:
                intent = new Intent(this, UITest_MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
