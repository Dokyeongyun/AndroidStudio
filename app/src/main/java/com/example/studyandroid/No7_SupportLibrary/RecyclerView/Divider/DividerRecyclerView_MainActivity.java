package com.example.studyandroid.No7_SupportLibrary.RecyclerView.Divider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.studyandroid.No7_SupportLibrary.RecyclerView.DummyDataGenerator;
import com.example.studyandroid.No7_SupportLibrary.RecyclerView.SimpleRecyclerView.SimpleStringAdapter;
import com.example.studyandroid.R;


/*

[ RecyclerView _ Customizing _ 구분선 (Divider) 넣기 ]

  1. RecyclerView.ItemDecoration 클래스를 상속하는 클래스를 정의한다.
  2. 원하는 모양에 맞추어 onDrawOver, onDraw, getItemOffsets 등의 메소드를 재정의한다.
  3. recyclerView 의 addItemDecoration() 메소드의 매개변수로 직접 정의한 클래스 객체를 넣어줌으로써 구현한다.

 */
public class DividerRecyclerView_MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SimpleStringAdapter simpleStringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divider_recycler_view__main);
        setupRecyclerView();
    }


    private void setupRecyclerView() {

        recyclerView = findViewById(R.id.simpleRecyclerView2);
        recyclerView.setHasFixedSize(true);

        simpleStringAdapter = new SimpleStringAdapter(DummyDataGenerator.generateStringListData());
        recyclerView.setAdapter(simpleStringAdapter);

        // 직접 커스터마이징한 구분선을 적용하기 위해 addItemDecoration() 사용
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

    }

    public static Intent createIntent(Context context) {
        return new Intent(context, DividerRecyclerView_MainActivity.class);
    }

}
