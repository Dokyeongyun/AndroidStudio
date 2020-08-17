package com.example.studyandroid.No7_SupportLibrary.RecyclerView.CardView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.studyandroid.No7_SupportLibrary.RecyclerView.DummyDataGenerator;
import com.example.studyandroid.R;
/*

[ RecyclerView _ CardView ]

  구분선을 이용하여 데이터간의 구분을 하게 할 수도 있지만,
  Android 에서 제공하는 CardView 형태를 이용하여
  더 효과적으로, 간단하게 구현할 수도 있다.

  구현방법은 비슷한데,
  차이점이 있다면 뷰가 담겨지는 xml 파일에서
  androidx.cardview.widget.CardView 태그 또는 직접 드래그 앤 드롭하여
  CardView 뷰를 생성해주고 이를 어댑터 생성시 지정해주기만 하면 된다.

 */
public class CardViewRecyclerView_MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardViewAdapter cardViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycler_view__main);
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        recyclerView = findViewById(R.id.simpleRecyclerView);

        recyclerView.setHasFixedSize(true);

        cardViewAdapter = new CardViewAdapter(DummyDataGenerator.generateStringListData());
        recyclerView.setAdapter(cardViewAdapter);

    }

    public static Intent createIntent(Context context){
        return new Intent(context, CardViewRecyclerView_MainActivity.class);
    }
}
