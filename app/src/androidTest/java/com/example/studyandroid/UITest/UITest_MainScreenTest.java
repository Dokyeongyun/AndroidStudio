package com.example.studyandroid.UITest;

import androidx.test.rule.ActivityTestRule;

import com.example.studyandroid.No11_UITest.UITest_MainActivity;
import com.example.studyandroid.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class UITest_MainScreenTest {

    final String MESSAGE = UITest_MainActivity.DONE_MESSAGE;

    @Rule
    public ActivityTestRule<UITest_MainActivity> mMainActivityTestRule = new ActivityTestRule<>(UITest_MainActivity.class);

    @Test
    public void 화면에FAB관련문자열이표시되지않는다() {
        onView(withText(MESSAGE)).check(doesNotExist());
    }

    @Test
    public void FAB를클릭하면FAB관련메시지가표시된다(){
        onView(withId(R.id.fab2)).perform(click());
        onView(withText(MESSAGE)).check(matches(isDisplayed()));
    }
}
