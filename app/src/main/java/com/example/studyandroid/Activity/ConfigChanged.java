package com.example.studyandroid.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studyandroid.R;
/*
액티비티 수명주기 [ onCreate - onStart / onRestart - onResume - onPause - onStop - onDestroy ] 에서,
시스템 메모리가 모자랄 경우, 시스템 자체적으로 onStop, onDestroy를 콜백하지 않고 액티비티를 강제로 종료시켜 메모리를 확보할 때가 있다.
이 경우, 다루고 있던 데이터를 보존하기 위해서는 액티비티의 수명주기 상태 중 onPause에서 이를 처리하여야 한다.

액티비티는 (onCreate - onDestory) / (onStart - onStop) / (onResume - onPause) 의 단계를 쌍으로 하여
어떠한 작업을 준비 - 뒷정리(종료) 하는 과정을 처리하게 된다.

----------------------------------------------------------------------------------------------------------------------------

만약 디바이스 설정이 갱신된 것을 탐지하게 되면,
기본적으로 시스템은 현재 액티비티를 폐기한 후 새로 생성한다.

 * 여기서 디바이스 설정의 변경은 => 화면 가로-세로 전환 / 언어 설정 변경 / 단말기 SIM교체에 따른 전화번호 변경 등

문제는 여기서 발생한다.
어떠한 정보를 입력하는 등 어떤 데이터를 다루고 있는 상황에서 액티비티가 재생성된다면
다루고 있던 데이터는 모두 초기화되고 새로운 액티비티가 onCreate 되게 될 것이다.

이를 위하여 onSaveInstanceState / onRestoreInstanceState 라는 콜백 메소드가 존재한다.
이 메소드는 일시적으로 데이터를 저장하고 복귀 시 저장한 데이터를 가져올 수 있게 하는 역할을 한다.

----------------------------------------------------------------------------------------------------------------------------

디바이스 설정이 변경될 때 액티비티의 상태는 onPause를 호출하게 된다.
onPause가 호출되면 콜백 메소드인 onSaveInstanceState가 호출되고 여기서 다루고 있던 데이터를 저장하는 코드를 작성하면 된다.

데이터를 저장할 때는 Bundle 객체를 사용하여 (키, 값)의 형태로 데이터를 put하게 된다.
이 때 사용할 수 있는 자료형으로는 int, float 등의 자바 기본형과 문자열, List와 Parcelable 형을 구현한 인스턴스가 있다.

그리고 onPause 상태가 종료되면 onResume가 호출되고
이에 따라 콜백 메소드인 onRestoreInstanceState가 호출되게 된다.
onSavaInstanceState에서 Bundle 객체에 담아두었던 데이터를 각 키값을 사용하여 꺼내온 후 원하는대로 데이터를 처리하면 되는 것이다.

----------------------------------------------------------------------------------------------------------------------------

단, 주의할 점으로 BACK 키처럼 사용자가 액티비티를 명시적으로 onDestroy 한 경우에는
위 두 가지 메소드는 콜백되지 않고 따라서 데이터 또한 사라지게 된다는 것.
 */


public class ConfigChanged extends AppCompatActivity {

    private String mText;
    private EditText mEditText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_changed);

        mEditText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(mEditText.getText().toString());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mText!=null){
            textView.setText(mText);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        mText = mEditText.getText().toString();
        outState.putString("EDIT_TEXT", mText);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mText = savedInstanceState.getString("EDIT_TEXT");
    }
}
