package com.example.studyandroid.No7_SupportLibrary.RecyclerView.SimpleRecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.studyandroid.No7_SupportLibrary.RecyclerView.DummyDataGenerator;
import com.example.studyandroid.R;

public class SimpleRecyclerView_MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SimpleStringAdapter simpleStringAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycler_view__main);
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.simpleRecyclerView);

        // recyclerView 의 크기가 변하지 않는 경우, setHasFixedSize(true) 옵션을 설정하면 성능이 개선됨
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adapter 설정
        simpleStringAdapter = new SimpleStringAdapter(DummyDataGenerator.generateStringListData());
        simpleStringAdapter.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SimpleRecyclerView_MainActivity.this, "Position: " + recyclerView.getChildAdapterPosition(v) + "가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(simpleStringAdapter);
    }
}
