package com.example.studyandroid.BmiCalculatorTest;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.studyandroid.No10_UnitTest.BmiCalculator.BmiSaveService;
import com.example.studyandroid.No10_UnitTest.BmiCalculator.BmiValue;

import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BmiSaveServiceTest {

    @Test
    public void static인start메소드를호출하면startService된다() {
        Context context = mock(Context.class);
        BmiSaveService.start(context, mock(BmiValue.class));
        verify(context, times(1)).startService((Intent) any());
    }

    @Test
    public void onHandleIntent에null을전달하면아무것도하지않는다() {
        BmiSaveService service = spy(new BmiSaveService());
        service.onHandleIntent(null);
        verify(service, never()).sendLocalBroadcast(anyBoolean());
        verify(service, never()).saveToRemoteServer((BmiValue) any());
    }

    @Test
    public void onHandleIntent에파라미터없는Intent를전달하면아무것도하지않는다() {
        BmiSaveService service = spy(new BmiSaveService());
        service.onHandleIntent(mock(Intent.class));
        verify(service, never()).sendLocalBroadcast(anyBoolean());
        verify(service, never()).saveToRemoteServer((BmiValue)any());
    }

    @Test
    public void onHandleIntent에BmiValue형이외의데이터가들어간Intent를전달하면아무것도하지않는다() {
        Intent intent = mock(Intent.class);
        when(intent.getSerializableExtra(BmiSaveService.PARAM_KEY_BMI_VALUE)).thenReturn("hoge");

        BmiSaveService service = spy(new BmiSaveService());
        service.onHandleIntent(intent);
        verify(service, never()).sendLocalBroadcast(anyBoolean());
        verify(service, never()).saveToRemoteServer((BmiValue)any());
    }

    // onHandleIntent() 로직 테스트
    @Test
    public void onHandleIntent에올바른데이터가들어간Intent를전달하면데이터저장과Broadcast가이루어진다() {
        // 1. BmiSaveService 에 전달할 Intent 준비
        BmiValue bmiValue = mock(BmiValue.class);
        Intent intent = mock(Intent.class);
        when(intent.getSerializableExtra(BmiSaveService.PARAM_KEY_BMI_VALUE)).thenReturn(bmiValue);

        // 2. BmiSaveService 의 메소드는 아무 기능도 하지 않도록 설정
        BmiSaveService service = spy(new BmiSaveService());
        doReturn(false).when(service).saveToRemoteServer((BmiValue) any());
        doNothing().when(service).sendLocalBroadcast(anyBoolean());

        // 3. 테스트, 메소드 호출 확인
        service.onHandleIntent(intent);
        verify(service, times(1)).sendLocalBroadcast(anyBoolean());
        verify(service, times(1)).saveToRemoteServer((BmiValue) any());
    }

    @Test
    public void Broadcast를통보한다(){

        // 테스트 위해 LocalBroadcastManager 를 mock 객체로 선언
        LocalBroadcastManager manager = mock(LocalBroadcastManager.class);
        BmiSaveService service = new BmiSaveService();
        service.setLocalBroadcastManager(manager);

        // LocalBroadcast 송신
        service.sendLocalBroadcast(true);

        // LocalBroadcast 로 Broadcast 실행되고 있는 지 확인
        verify(manager,times(1)).sendBroadcast((Intent)any());
    }
}
