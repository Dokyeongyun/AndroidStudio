package com.example.studyandroid.No11_UITest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.studyandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * [ UI Test ]
 * : Unit Test 보다 한 단계 큰 범위의 테스트. Unit Test 에서 테스트를 수행한 모듈들을 통합한 후 테스트하는 것
 * 실제 앱을 수동으로 조작하는 것을 자동화하여 실행할 수 있는 형태로 만드는 테스트방법
 * <p>
 * * Unit Test 와 UI Test 의 특징
 * 1. 실행환경 : 로컬 PC의 JVM vs 안드로이드 디바이스
 * 2. 실행속도 : 빠름 vs 느림
 * 3. 안드로이드 플랫폼에 의존한 테스트 : 불가 vs 가능
 * ---------------------------------------------------------------------------------------------
 */
/**
 * [ UI Test _ 테스트방법 ]
 *
 *   1. Espresso Framework 사용환경 준비
 *      * Espresso (Android 2.2 (API 8) 이상부터 사용가능)
 *     - build.gradle(app) 파일 내 의존관계 설정
 *   2. 클랙스 및 메소드 작성
 *   3. androidTest 폴더 내에 테스트 클래스 및 메소드 작성
 *   4. 테스트내용 포함된 패키지 우클릭 후 테스트 수행
 */

/**
 * [ UI Test _ Espresso ]
 *
 *   - 에스프레소에 대한 자세한 내용은 구글이 제공하는 Cheat Sheet 를 확인
 *   - 에스프레소에서 주로 사용되는 4가지 메소드가 소개되어 있음
 *      1. Espresso.onView
 *      2. Espresso.onData
 *      3. Intents.intended
 *      4. Intents.intending
 */

public class UITest_MainActivity extends AppCompatActivity {

    public static final String DONE_MESSAGE = "FAB를 클릭했습니다";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_test__main);
        final TextView textView = findViewById(R.id.text2);

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(DONE_MESSAGE);
            }
        });

    }
}
