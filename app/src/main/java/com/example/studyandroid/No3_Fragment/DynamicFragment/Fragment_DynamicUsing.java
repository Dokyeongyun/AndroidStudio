package com.example.studyandroid.No3_Fragment.DynamicFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.studyandroid.R;

/*
[ Fragment - Dynamic Fragment ]

동적 프래그먼트의 사용방법에 대하여 공부한다.

 : 추가버튼과 삭제버튼 클릭하면 각각 프래그먼트가 동적으로 생성되고 삭제되는 간단한 예제!
-----------------------------------------------------------------------------------------------------------------------

 프래그먼트를 다룰 때에는 다음의 규칙을 이해하여야 한다.
  1. 프래그먼트의 추가/삭제는 트랜잭션 단위로 수행된다. -> beginTransaction()
  2. 프래그먼트 추가는 특정 ViewGroup 에 할 수 있다. -> add() 메소드의 매개변수로 프래그먼트가 들어갈 Container 전달
  3. 액티비티와 마찬가지로 백스택이 존재한다.
-----------------------------------------------------------------------------------------------------------------------

 프래그먼트의 추가와 삭제는 간단하다.
  - 추가 : FragmentManager 를 이용, 트랜잭션 생성 -> add() -> 스택위치결정 -> commit()
  - 삭제 : 백스택에서 제거

 각 프래그먼트의 TextView 에 해당 프래그먼트가 몇 번째 프래그먼트인지를 알려주기 위하여,
 액티비티의 변수 mNumber 를 각 프래그먼트로 전달을 해주어야 한다.

 이 때, 액티비티와 프래그먼트 사이의 데이터교환 통로는 Bundle 객체이다.
 프래그먼트의 getInstance() 의 매개변수로 현재 프래그먼트의 수 + 1를 전달하게 되는데,
 이 메소드에서는 전달 받은 값을 Bundle 객체에 담아 setArguments()를 이용해 프래그먼트로 전달한다.

 그러면 각 프래그먼트의 View 가 모두 생성된 후 콜백되는 메소드 onViewCreated() 에서
 해당 Bundle 객체에 담긴 정보를 getArguments() 를 이용하여 데이터를 가져와 다룰 수 있게 된다.

 */
public class Fragment_DynamicUsing extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    private static final String KEY_NUMBER = "KEY_NUMBER";
    private int mNumber = 0;

    // 프래그먼트의 백스택에 변화가 발생을 감지하는 리스너
    //  : 현재 액티비티 내의 프래그먼트의 개수를 세어 mNumber 를 결정함
    private FragmentManager.OnBackStackChangedListener mListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            FragmentManager fm = getSupportFragmentManager();
            int count = 0;
            for (Fragment f : fm.getFragments()) {
                if (f != null) {
                    count++;
                }
            }
            mNumber = count;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dynamic_using);


        // 동적 프래그먼트 추가
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();

                // 1. 프래그먼트는 트랜잭션 단위로 사용 -> beginTransaction() 을 이용해 트랜잭션 생성
                // 2. 프래그먼트의 생성자 getInstance() 를 이용해 프래그먼트 생성
                // 3. addToBackStack() 을 이용해 백스택에 추가
                fm.beginTransaction().add(R.id.fragment_container, DynamicFragment.getInstance(mNumber))
                        .addToBackStack(null).commit();
            }
        });

        // 동적 프래그먼트 삭제
        findViewById(R.id.removeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mNumber == 0) {
                    return;
                }
                FragmentManager fm = getSupportFragmentManager();

                // 1. 백스택에서 pop 하여 프래그먼트를 스택에서 제거함
                fm.popBackStack();

            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(mListener);

        Fragment fragment = fm.findFragmentByTag(FRAGMENT_TAG);

        if (savedInstanceState == null) {
            fm.beginTransaction().add(R.id.fragment_container, DynamicFragment.getInstance(mNumber), FRAGMENT_TAG)
                    .addToBackStack(null).commit();
        }

    }


    // 프래그먼트가 폐기될 때마다 백스택의 변화가 발생하기 때문에
    // 이를 감지하는 리스너를 통해 mNumber 값을 변경한다.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        FragmentManager fm = getSupportFragmentManager();
        fm.removeOnBackStackChangedListener(mListener);
    }

    // 화면 전환 등 기기설정이 변경될 때, 데이터를 저장/호출 하는 콜백 메소드
    // onSaveInstanceState, onRestoreInstanceState 정의
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_NUMBER, mNumber);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mNumber = savedInstanceState.getInt(KEY_NUMBER);
    }
}
