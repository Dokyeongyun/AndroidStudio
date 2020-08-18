package com.example.studyandroid.no8_mvp_mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.example.studyandroid.R;
import com.example.studyandroid.databinding.ActivityDataBindingMainBinding;

import java.util.Calendar;

/*

[ Data Binding ]

 : MVVM 모델의 기반이 된 이론으로, Model 에서 가져온 Data 를 View 에 반영하는 로직을 자동으로 수행해주는 처리기법이다.
   즉, 안드로이드의 XML 파일에서 정의한 레이아웃과 데이터를 자동으로 연결해주는 기법이다.

   - 사용방법 :
     1. app/build.gradle 파일에 내용 추가
        -> dataBinding { enabled = true }
     2. 레이아웃 파일 (XML) 작성
        -> 루트 태그를 layout 으로 작성
        -> layout 태그 안에 data 태그 작성
        -> data 태그 내에 바인딩할 클래스를 작성
        -> 바인딩할 클래스의 데이터를 레이아웃 안에서 이용가능
     3. 바인딩할 클래스 작성
        -> layout 과 관련된 멤버 변수 또는 메소드를 작성
-----------------------------------------------------------------------------------------------------

[ Data Binding 을 활용한 간단한 앱 ]
 : Like 버튼을 누르면 Like 텍스트의 값이 변경되는 간단한 앱

   1. User.java : Data Binding 을 수행할 클래스 (유저 프로필과 Like 변수, Like 버튼 클릭 시 count 증가하는 함수 작성)
   2. DataBinding_MainActivity.java : 앱의 기본 액티비티
        -> Binding 객체를 얻은 후 데이터바인딩 할 클래스를 설정
        -> 객체를 이용하여 View 에 대한 참조를 얻어 데이터 조작
   3. activity_data_binding__main.xml : 앱의 기본 화면 구성 - Data Binding 적용
-----------------------------------------------------------------------------------------------------

   * Data Binding 을 사용함으로써 findViewById() 등을 통한 View 에 대한 참조를 얻을 필요가 없으며
     setText(), setOnClickListener 등의 조작 또한 작성하지 않아도 된다.
   * 주의할 점: Data Binding 을 이용할 때, 클래스가 포함된 경로의 Package 명에 대문자가 포함되면 오류가 발생한다!


 */
public class DataBinding_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_data_binding__main);

        // Binding 객체 얻기
        ActivityDataBindingMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding__main);
        // Binding 객체에 User 를 설정
        binding.setUser(new User("Do", 25));

        String date = (String) DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance());
        binding.textTime.setText(date);

        // View 에 id 가 지정되어 있으면 View 에 대한 참조를 얻을 수 있음
/*        String likeCount = binding.likeText.getText().toString();
        Toast.makeText(this,  likeCount+"", Toast.LENGTH_SHORT).show();*/
    }
}
