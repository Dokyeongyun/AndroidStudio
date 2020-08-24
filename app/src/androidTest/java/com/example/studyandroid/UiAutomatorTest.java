package com.example.studyandroid;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UiAutomatorTest {

    private static final String GOOGLE_PLAY_PACKAGE = "com.android.vending";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Ui Device 를 초기화하고 홈 버튼을 누름
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();

        // 런처 앱의 시작을 기다림
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void GooglePlay를시작하고텍스트입력후검색화면으로이동할수있다() throws UiObjectNotFoundException {

        // GooglePlay 시작
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(GOOGLE_PLAY_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // GooglePlay 시작 기다림
        mDevice.wait(Until.hasObject(By.pkg(GOOGLE_PLAY_PACKAGE).depth(0)), LAUNCH_TIMEOUT);

        // 검색 버튼 클릭
        UiObject searchBoxImage = mDevice.findObject(new UiSelector()
                .resourceId("com.android.vending:id/search_box_idle_text")
                .className("android.widget.ImageView"));
        searchBoxImage.click();

        // 검색을 위한 EditText 가 표시됨
        UiObject searchBox = mDevice.findObject(new UiSelector().resourceId("com.android.vending:id/search_box_text_input")
                .className("android.widget.EditText"));
        assertTrue(searchBox.exists());
    }
}
