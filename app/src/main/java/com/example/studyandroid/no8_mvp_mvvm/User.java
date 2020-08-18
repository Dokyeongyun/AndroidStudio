package com.example.studyandroid.no8_mvp_mvvm;

import android.view.View;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class User {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableInt age = new ObservableInt();
    public ObservableInt likes = new ObservableInt();

    public User(String nameString, int ageInt) {
        name.set(nameString);
        age.set(ageInt);
        likes.set(0);
    }

    public void onClickLike(View view) {
        likes.set(likes.get() + 1);
    }
}
