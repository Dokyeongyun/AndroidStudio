package com.example.studyandroid.No10_UnitTest.LocalUnitTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.studyandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * [ Software Test ]
 *  : 소프트웨어 개발 과정 전반에 걸쳐 소프트웨어의 개발이 잘 이루어지고 있는 지 확인하기 위한 테스트를 수행한다.
 *    종류로는 단위테스트, 통합테스트, 인수테스트, 시스템테스트 등이 있다.
 *  --------------------------------------------------------------------------------------------
*/
/**
 *  [ Unit Test ]
 *  : 단위테스트는 가장 작은 단위의 테스트로 클래스나 메소드 등의 작은 단위를 테스트하기 위한 목적으로 수행된다.
 *    소스코드 자체의 타당성, 품질을 확인하기 위한 테스트이다.
 *    구현 로직과 관련이 깊기 때문에 소스코드를 작성하는 프로그래머가 직접 테스트한다.
 *  --------------------------------------------------------------------------------------------
 */
/**
 *  [ Unit Test _ 테스트방법 ( JUnit, Mockito ) ]
 *
 *   1. 단위 테스트환경 설정
 *      - JUnit 프레임워크
 *        -> 자바의 사실상 표준이 되는 단위 테스트 프레임워크
 *        -> build.gradle(app) 파일 내에 dependencies 블록 -> mockito, junit 추가
 *   2. 클래스 및 메소드 작성
 *   3. 테스크 클래스 및 테스트 메소드 작성
 *   4-1. 사용예제 1 ( JUnit 사용 )
 *      - 계산기 클래스 Calculator, 덧셈연산을 수행하는 메소드 evaluate()
 *      - 계산기 클래스의 테스트 클래스 CalculatorTest ( 일반적으로 테스트하기 위한 클래스이름 + Test 로 선언함 )
 *      - evaluate() 메소드를 테스트하기 위한 메소드 evaluatesExpression() ( @Test 어노테이션으로 선언 )
 *      - 테스트 클래스가 담긴 패키지를 우클릭 후 테스트 수행 -> Run 윈도우에서 결과 확인
 *   4-2. 사용예제 2 ( Mockito 사용 )
 *      - 사용예제 1은 다른 클래스와의 의존관계가 아예 없기 때문에 테스트하기 쉽다.
 *      - 하지만 실제로 이런 경우는 매우 드물고, IO 등과 같은 외부의 영향을 받지 않도록 테스트를 작성해야 한다.
 *      - 이 때, 다른 클래스와의 의존관계, HTTP 등 외부 환경과의 의존관계를 대체하기 위해 '위장' 객체를 사용한다.
 *       * 위장객체 : 실제 처리는 하지 않지만 원하는 값을 반환하거나 처리를 실행하는 객체
 *      - 안드로이드에서 이 위장객체로써 Mock 객체를 사용하고 라이브러리 Mockito 로 적용가능하다.
 *        -> Mock 객체 생성 방법
 *           LinkedList mockedList = mock(LinkedList.class);
 *        -> mockedList.get(0) 이 'first' 를 반환하도록 설정
 *           When(mockedList.get(0)).thenReturn("first");
 *      - 목 객체는 메소드가 호출되었는가를 검증하는 기능을 지원한다.
 *        -> add 메소드와 clear 메소드의 호출여부를 검증하는 방법
 *        -> verify(mockedList, times(1)).add("one");
 *        -> verify(mockedList, times(1)).clear();
 *
 *
 */


public class LocalUnitTest_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_unit_test__main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


