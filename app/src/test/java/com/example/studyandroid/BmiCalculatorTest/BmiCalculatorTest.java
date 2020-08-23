package com.example.studyandroid.BmiCalculatorTest;

import com.example.studyandroid.No10_UnitTest.BmiCalculator.BmiCalculator;
import com.example.studyandroid.No10_UnitTest.BmiCalculator.BmiValue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BmiCalculatorTest {


    // @Spy 어노테이션 : 기본적으로 일반 객체지만 일부 메소드만 교체 또는 호출하여 확인할 수 있는 Mock 객체라는 의미
    // @Spy 어노테이션으로 정의된 객체는 MockitoAnnotations.initMocks(this); 가 호출된 후에 행동할 수 있게 됨
    @Spy
    private BmiCalculator cal = new BmiCalculator();

    // @Before 어노테이션 : @Test 어노테이션이 붙은 메소드를 실행하기 전(Before)에 항상 실행되는 메소드 정의
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUsingSimpleInput(){
        BmiValue result = cal.calculate(1,1);
        assertNotNull(result);
        verify(cal, times(1)).createValueObj(1f);
    }

    @Test
    public void testUsingRealInput(){
        BmiValue result = cal.calculate(1.70f,60f);
        assertNotNull(result);
        verify(cal, times(1)).createValueObj(20.761246f);
    }
}
