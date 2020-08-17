package com.example.studyandroid.No7_SupportLibrary.RecyclerView.Manipulation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studyandroid.No7_SupportLibrary.RecyclerView.DummyDataGenerator;
import com.example.studyandroid.R;

/*

[ RecyclerView _ Customizing _ 아이템 추가, 삭제 및 조작 ]

  : RecyclerView 에는 아이템을 추가, 삭제는 물론,
  드래그 앤 드롭, 스와이프 등의 조작을 가능하게 할 수 있다.

 */
public class ManipulationRecyclerView_MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ManipulationSimpleStringAdapter simpleStringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulation_recycler_view__main);
        setupRecyclerView();
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleStringAdapter.addAtPosition(3, "새 아이템");
            }
        });
        findViewById(R.id.remove_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleStringAdapter.removeAtPosition(3);
            }
        });

    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.simpleRecyclerView3);

        recyclerView.setHasFixedSize(true);

        simpleStringAdapter = new ManipulationSimpleStringAdapter(DummyDataGenerator.generateStringListData());
        recyclerView.setAdapter(simpleStringAdapter);

        // ItemTouchHelper 클래스를 구현 -> 드래그 앤 드롭, 스와이프 등의 동작으로 삭제 등의 기능을 구현할 수 있다.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // 드래그 앤 드롭하여 아이템 이동
                simpleStringAdapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 스와이프하여 아이템 삭제
                simpleStringAdapter.removeAtPosition(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, ManipulationRecyclerView_MainActivity.class);
    }

}
