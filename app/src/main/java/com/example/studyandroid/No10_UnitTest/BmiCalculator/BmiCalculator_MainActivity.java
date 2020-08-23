package com.example.studyandroid.No10_UnitTest.BmiCalculator;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyandroid.R;

/**
 * [ Unit Test _ BMI 계산기 예제 ]
 * 1. 화면에 키와 몸무게 입력 EditText, BMI 계산 실행 Button 설치 (BmiCalculator_MainActivity.java)
 * 2. 버튼을 누르면 BMI 계산, 백그라운드에 저장처리 (저장처리 중 버튼 비활성화) (BmiCalculator.java - 계산, BmiSaveService - 저장)
 * 3. 저장처리 마친 후 LocalBroadcast 로 통지 후 버튼 재활성화
 */

public class BmiCalculator_MainActivity extends AppCompatActivity {

    private LocalBroadcastManager mLocalBroadcastManager;
    private Button mCalcButton;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator__main);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());

        initViews();
    }

    @VisibleForTesting
    public void initViews() {
        EditText weightText = findViewById(R.id.text_weight);
        EditText heightText = findViewById(R.id.text_height);
        TextView resultText = findViewById(R.id.text_result);

        mCalcButton = findViewById(R.id.button_calculate);

        View.OnClickListener buttonListener = createButtonListener(weightText, heightText, resultText);
        mCalcButton.setOnClickListener(buttonListener);
    }

    @VisibleForTesting
    public View.OnClickListener createButtonListener(final TextView weightText,
                                                     final TextView heightText,
                                                     final TextView resultText) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결과 취득과 표시
                BmiValue result = calculateBmiValue(weightText, heightText);
                showCalcResult(resultText, result);

                //Service를 사용해 보존 처리
                startResultSaveService(result);
                prepareReceiveResultSaveServiceAction();
            }
        };
    }

    @VisibleForTesting
    public BmiValue calculateBmiValue(final TextView weightText, final TextView heightText) {
        float weight = Float.valueOf(weightText.getText().toString());
        float height = Float.valueOf(heightText.getText().toString());
        BmiCalculator calculator = new BmiCalculator();
        return calculator.calculate(height, weight);
    }

    @VisibleForTesting
    public void showCalcResult(TextView resultText, BmiValue result) {
        String message = result.toFloat() + " : " + result.getMessage() + "체형입니다";
        resultText.setText(message);
    }

    @VisibleForTesting
    public void startResultSaveService(BmiValue result) {
        mCalcButton.setText("저장 중입니다...");
        mCalcButton.setEnabled(false);
        BmiSaveService.start(BmiCalculator_MainActivity.this, result);
    }

    @VisibleForTesting
    public void prepareReceiveResultSaveServiceAction() {
        IntentFilter filter = new IntentFilter(BmiSaveService.ACTION_RESULT);
        mReceiver = new BmiSaveResultReceiver(mCalcButton);
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            mLocalBroadcastManager.unregisterReceiver(mReceiver);
        }
    }

    @VisibleForTesting
    public static class BmiSaveResultReceiver extends BroadcastReceiver {

        private Button mCalcButton;

        public BmiSaveResultReceiver(Button calcButton) {
            mCalcButton = calcButton;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }

            if (!intent.hasExtra(BmiSaveService.PARAM_RESULT)) {
                return;
            }

            boolean result = intent.getBooleanExtra(BmiSaveService.PARAM_RESULT, false);
            if (!result) {
                Toast.makeText(context, "BMI 저장에 실패했습니다", Toast.LENGTH_SHORT).show();
            }

            mCalcButton.setText("계산");
            mCalcButton.setEnabled(true);
        }
    }
}
