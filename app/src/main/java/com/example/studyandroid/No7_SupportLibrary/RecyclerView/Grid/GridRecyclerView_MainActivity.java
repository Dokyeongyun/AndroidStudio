package com.example.studyandroid.No7_SupportLibrary.RecyclerView.Grid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.studyandroid.No7_SupportLibrary.RecyclerView.DummyDataGenerator;
import com.example.studyandroid.R;
/*

[ RecyclerView _ Customizing _ GridRecyclerView ]

  1. GridLayoutManager 를 이용 -> 생성 시 열의 개수를 지정
  2. GridLayoutManager.SpanSizeLookup() 을 이용하여
        각 내용별로 그리드 상에서 차지할 면적을 정의한다.
        ex) 헤더는 3칸을 모두 차지, 내용은 1칸만 차지
            이 때, 헤더인지 내용인지, 즉 아이템뷰의 타입을 얻기 위하여
            Adapter.getItemViewType() 을 이용한다.

 */
public class GridRecyclerView_MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycler_view__main);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView =  findViewById(R.id.simpleRecyclerView);

        recyclerView.setHasFixedSize(true);

        final RichAdapter adapter = new RichAdapter(DummyDataGenerator.generateStringListData());
        recyclerView.setAdapter(adapter);

        // GridLayout 의 열을 3으로 지정하여 생성
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        // SpanSizeLookup 으로 아이템뷰별로 차지할 폭을 결정한다
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == RichAdapter.HEADER_VIEW_TYPE) {
                    // 헤더는 3열을 차지해서 표시한다(표시되는 것은 1열)

                    // getSpanCount() -> 모든 열에 꽉 채울 수 있도록 함 (나중에 열의 크기가 바뀌어도 호환됨)
                    return gridLayoutManager.getSpanCount();
                }

                // 나머지는 1열을 사용한다(표시되는 것은 3열)
                return 1;
            }
        };
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, GridRecyclerView_MainActivity.class);
    }
}
