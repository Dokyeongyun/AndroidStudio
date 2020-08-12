package com.example.studyandroid.No3_Fragment.StaticFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.studyandroid.R;

/*
[ Fragment ]

프래그먼트는 액티비티와 마찬가지로 수명주기를 가지며, 프래그먼트 각각의 수명주기에 따라 처리를 구현할 수 있다는 특징이 있다.
UI를 가지는 프래그먼트의 활용방법과, UI를 가지지 않는 프래그먼트인 Headless, 그리고 중첩 프래그먼트에 대해 공부한다.
-----------------------------------------------------------------------------------------------------------------------

[ Fragment 사용방법 ]

 1. Fragment 클래스를 생성한다. (MyFragment.java)
 2. Fragment의 UI 및 View 구축에 이용할 XML을 생성한다. (fragment_static.xml)
 3. 액티비티에서 작성한 Fragment를 사용한다. (FragmentTestActivity.java)
    - 이 때, 액티비티와 프래그먼트 사이의 연결을 위하여 하나의 인터페이스를 정의한다
    - 프래그먼트의 생명주기 중 onAttach()에서 연결이 수행되고
    - 연결을 해제하기 전 onDetach()에서 리스너에 대한 참조를 해제한다.
    - 이 과정이 정상적으로 동작하게 하기 위해서 onAttach()에서 OnFragmentInteractionListener가 구현되지 않았다면 예외를 발생시킨다.
-----------------------------------------------------------------------------------------------------------------------

[ Fragment - Static Fragment ]
 : 정적 프래그먼트의 사용방법 => 액티비티의 xml 파일에 직접 프래그먼트 태그를 추가

  * 이렇게 액티비티와 프래그먼트간의 연결을 인터페이스(리스너)를 구현함으로써 수행하게 되면,
    액티비티에 대한 참조를 직접 가지지 않기 때문에 프래그먼트가 특정 액티비티에 의존하지 않게되고고
   결과적으로 느슨한 결합으로 액티비티와 프래그먼트를 연결할 수 있게 된다.
 */
// MyFragment와의 연결을 위한 인터페이스를 구현한다.
public class Fragment_StaticUsing extends AppCompatActivity implements StaticFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_static_using);
    }

    @Override
    public void onFragmentInteraction() {
        Toast.makeText(this, "버튼이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
    }
}
