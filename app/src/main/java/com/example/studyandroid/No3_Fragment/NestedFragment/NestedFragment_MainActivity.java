package com.example.studyandroid.No3_Fragment.NestedFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.studyandroid.R;
/*
[ Fragment - Nested Fragment ]

 중첩프래그먼트의 특징
  1. 레이아웃 XML 로 추가할 수 없다.
  2. 동적으로 추가해야 한다.
  3. 코드가 복잡해지고 구현 난도가 높다.
  4. 부모 프래그먼트에서 UI를 가지지 않고, 자식 프래그먼트에서 관리하여 역할을 명확히 한다.
  5. getSupportFragmentManager 가 아닌 getChildFragmentManager 를 사용한다.
  6. [뒤로가기] 키 클릭 시 중첩 프래그먼트의 백스택이 아닌 부모 프래그먼트의 백스택을 확인한다.
 */
public class NestedFragment_MainActivity extends AppCompatActivity {

    public static final String TAG_PARENT = "TAG_PARENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_fragment__main);

        // 액티비티 생성 시 프래그먼트에 TAG 붙여줌
        FragmentManager fm = getSupportFragmentManager();
        Fragment parentFragment = fm.findFragmentByTag(TAG_PARENT);

        if(parentFragment==null){
            fm.beginTransaction().add(R.id.container, ParentFragment.getInstance(), TAG_PARENT).commit();
        }
    }

    // 중첩프래그먼트에서는 뒤로가기 클릭 시 부모프래그먼트의 백스택을 확인하기 때문에 이에 대한 처리를 한다.
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment parentFragment = fm.findFragmentByTag(TAG_PARENT);

        // 부모프래그먼트의 백스택 확인
        if(parentFragment != null && parentFragment.getChildFragmentManager().getBackStackEntryCount() >0) {
            parentFragment.getChildFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }
}
