package com.example.studyandroid.No10_UnitTest.BmiCalculator;

import androidx.annotation.VisibleForTesting;

public class BmiCalculator {
    public BmiValue calculate(float heightInMeter, float weightInKg){
        if(heightInMeter <0 || weightInKg < 0){
            throw new RuntimeException("키와 몸무게는 양수로 입력해야합니다.");
        }

        float bmiValue = weightInKg / (heightInMeter * heightInMeter);
        return createValueObj(bmiValue);
    }

    @VisibleForTesting
    public BmiValue createValueObj(float bmiValue){
        return new BmiValue(bmiValue);
    }


}
