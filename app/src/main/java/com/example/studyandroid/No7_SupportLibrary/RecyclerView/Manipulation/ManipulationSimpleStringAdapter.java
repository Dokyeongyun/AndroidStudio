package com.example.studyandroid.No7_SupportLibrary.RecyclerView.Manipulation;


import com.example.studyandroid.No7_SupportLibrary.RecyclerView.CardView.CardViewAdapter;

import java.util.List;

// CardView 와 기능이 비슷하기 때문에 CardViewAdapter 를 상속하여 이용한다.
public class ManipulationSimpleStringAdapter extends CardViewAdapter {

    // 생성자
    public ManipulationSimpleStringAdapter(List<String> myDataset) {
        super(myDataset);
    }

    // 데이터 삽입
    public void addAtPosition(int position, String text) {

        // 삽입하고자 하는 위치가 현재 dataset 보다 크면 가장 마지막 위치에 추가
        if (position > dataset.size()) {
            position = dataset.size();
        }

        // 데이터 추가
        dataset.add(position, text);

        // 데이터의 추가를 Adapter 에 알림
        notifyItemInserted(position);
    }

    // 데이터 삭제
    public void removeAtPosition(int position) {
        if (position < dataset.size()) {
            dataset.remove(position);
            notifyItemRemoved(position);
        }
    }


    // 데이터 위치 변경
    public void move(int fromPosition, int toPosition) {
        final String text = dataset.get(fromPosition);
        dataset.remove(fromPosition);
        dataset.add(toPosition, text);
        notifyItemMoved(fromPosition, toPosition);
    }
}
