package com.example.studyandroid.No3_Fragment.NetworkCheckFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studyandroid.R;
/*

[ Fragment - UI를 갖지 않는 프래그먼트 ]

 UI를 갖지 않는 프래그먼트를 다른 말로 Headless Fragment 라고도 한다.
 네트워크 연결 확인 및 네트워크 연결 변경 감지를 프래그먼트로 구현하는 예제를 작성한다.
 ---------------------------------------------------------------------------

 Headless Fragment 는 UI와 연결되어 있지 않으므로,
 화면 회전 등의 디바이스 설정이 변경되어도 프래그먼트를 재생성하지 않아도 된다.
 따라서 setRetainInstance(true) 를 호출하여 재생성되지 않도록 설정한다.

 하지만 이 때, 액티비티는 여전히 재생성되므로 액티비티의 onCreate() 에서
 프래그먼트를 생성하지 않도록 설정한다.
 */
public class NetworkCheck_MainActivity extends AppCompatActivity {

    private NetworkCheckFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_check__main);

        // NetworkCheckFragment 에서 정의한 TAG로 프래그먼트가 추가됐는지 체크
        mFragment = (NetworkCheckFragment) getSupportFragmentManager().findFragmentByTag(NetworkCheckFragment.TAG);

        // 추가되지 않았다면 TAG를 지정하여 추가함
        if(mFragment==null){
            mFragment = mFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(mFragment, NetworkCheckFragment.TAG).commit();
        }
    }
}
