package com.example.studyandroid.ViewAndLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.studyandroid.R;
/*
[ CustomView 만들기 ]
 : 세 개의 별 아이콘이 있고, 버튼을 클릭할 때마다 색칠된 별의 위치를 변경한다.
----------------------------------------------------------------------------
방법
 1. 커스텀 뷰의 레이아웃 결정
    - 세 개의 별 그림이 있는 xml파일을 작성한다 (three_stars_indicator.xml)
 2. 레이아웃 XML로 설정할 수 있는 항목을 attrs.xml에 기재
    - 커스텀 뷰의 상태를 파악할 수 있는 속성인 selected를 추가 ( 자료형은 integer )
 3. 커스텀 뷰 클래스 만들기 ( MyCustomView.java )
    - 레이아웃 XML 전개
    - attrs.xml에 정의해 두었던 스타일을 반영
    - 외부 클래스에서 커스텀 뷰의 상태를 변경하는 메소드를 작성하여 이용함
 4. 메인 앱의 레이아웃에 삽입하여 확인 ( CompositeCustomView.java )
 */
public class CompositeCustomView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.composite_custom_view);

        final MyCustomView indicator = findViewById(R.id.indicator);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = indicator.getSelected();
                if (selected == 2) {
                    selected = 0;
                } else {
                    selected++;
                }
                indicator.setSelected(selected);
            }
        });
    }
}
