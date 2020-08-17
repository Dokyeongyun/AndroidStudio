package com.example.studyandroid.No7_SupportLibrary.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studyandroid.No7_SupportLibrary.RecyclerView.Divider.DividerRecyclerView_MainActivity;
import com.example.studyandroid.No7_SupportLibrary.RecyclerView.SimpleRecyclerView.SimpleRecyclerView_MainActivity;
import com.example.studyandroid.R;
/*

[ RecyclerView ]
 : v7 Support Library 에서부터 사용가능하게 된 RecyclerView 는
   리스트를 표시할 때 아주 유용하고 자주 사용되는 위젯이다.
------------------------------------------------------------------

[ RecyclerView _ 특징 ]

 1. 많은 데이터를 한정된 View를 재사용하여 표시한다.
 2. 스크롤 등 성능이 좋음
 3. 기존 ListView, GridView 위젯의 기능을 대체
 4. 확장성이 높으며 레이아웃 변경, 애니메이션 등의 풍부한 조작 가능
 5. Adapter 와 ViewHolder, LayoutManager 를 정의해야 함
    * Adapter
     - RecyclerView.Adapter 를 상속하는 클래스를 만들어 정의
     - View 와 Data 를 연결하는 역할
    * ViewHolder
     - Adapter 내에 RecyclerView.ViewHolder 를 상속하는 클래스를 만들어 정의
     - View 에 대한 참조를 가짐
     - Adapter 의 onCreateViewHolder() 메소드로 인스턴스를 생성하여 반환
     - onBindViewHolder() 메소드로 ViewHolder 에 설정한 View 에 데이터를 설정
     - ViewHolder() 의 멤버 변수에 View 를 저장함으로써 findViewById() 를 매번 실행할 필요가 없어 성능이 향상됨
    * LayoutManager
     - RecyclerView.LayoutManager 를 정의
     - RecyclerView 에서 View 의 위치와 크기를 결정, View 의 재사용 규칙을 관리
     - 종류
        1. LinearLayoutManager : 아이템을 한 줄로 나열
        2. GridLayoutManager : 아이템을 격자형태로 나열
        3. StaggeredGridLayoutManager : 크기가 일정하지 않은 아이템을 격자형태로 나열
-------------------------------------------------------------------------------------------------------

[ RecyclerView _ 사용법 ]

    1. Adapter 의 onCreateViewHolder() 메소드로 ViewHolder 를 LayoutManager 에 넘겨줌
    2. onBindViewHolder() 로 ViewHolder 에 데이터를 설정
    3. View 의 크기를 결정하고 화면에 띄움

    * RecyclerView 의 View 는 재사용되는데,
      목록으로 나열된 RecyclerView 의 View 중 스크롤 된 View, 즉 필요없어진 View 는
      ViewHolder 의 Scap 리스트에 추가되게 되고 다시 필요해질 때 onBindViewHolder() 를 이용해
      RecyclerView 의 View 로써 지정해주게 되는 과정을 반복한다.

 */
public class RecyclerView_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view__main);
        setupViews();
    }

    private void setupViews() {
        findViewById(R.id.simple_execute_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SimpleRecyclerView_MainActivity.createIntent(RecyclerView_MainActivity.this);
                startActivity(intent);
            }
        });
        findViewById(R.id.divider_execute_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DividerRecyclerView_MainActivity.createIntent(RecyclerView_MainActivity.this);
                startActivity(intent);
            }
        });
/*        findViewById(R.id.cardview_execute_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CardViewRecyclerViewActivity.createIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        findViewById(R.id.grid_execute_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GridRecyclerViewActivity.createIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        findViewById(R.id.manipulation_execute_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ManipulationActivity.createIntent(MainActivity.this);
                startActivity(intent);
            }
        });*/
    }
}
