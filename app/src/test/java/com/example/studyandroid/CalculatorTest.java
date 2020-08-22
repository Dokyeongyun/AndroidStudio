package com.example.studyandroid;

import com.example.studyandroid.No10_UnitTest.Calculator;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class CalculatorTest {
    @Test
    public void evaluatesExpression(){
        Calculator cal = new Calculator();
        int sum = cal.evaluate("1+2+3");
        assertEquals(6,sum);
    }
}

