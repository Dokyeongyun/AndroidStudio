package com.example.studyandroid.No7_SupportLibrary.RecyclerView.SimpleRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyandroid.R;

import java.util.List;

// RecyclerView.Adapter 를 상속한 Adapter 를 정의
public class SimpleStringAdapter extends RecyclerView.Adapter<SimpleStringAdapter.ViewHolder> {

    List<String> dataset;
    private View.OnClickListener onItemViewClickListener;

    // Adapter 내에 ViewHolder 클래스를 정의 ( RecyclerView.ViewHolder 를 상속 )
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.simpleTextView);
        }
    }


    // 생성자로 데이터 넘겨주기
    public SimpleStringAdapter(List<String> myDataset) {
        dataset = myDataset;
    }

    // 클릭 리스너 정의
    public void setOnItemViewClickListener(View.OnClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }


    // View 를 inflate 하고 ViewHolder 를 정의
    @NonNull
    @Override
    public SimpleStringAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 새로운 View 만들기
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_row, parent, false);

        // 새로 만든 View 에 클릭 리스너 지정
        if(onItemViewClickListener!=null){
            v.setOnClickListener(onItemViewClickListener);
        }

        // 사용하지 않는 레이아웃 ViewHolder 에 저장
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // View 에 들어갈 데이터를 변경 -> ViewHolder 에 데이터를 설정
    @Override
    public void onBindViewHolder(@NonNull SimpleStringAdapter.ViewHolder holder, int position) {
        // dataset 에서 설정할 데이터 가져오기
        String text = dataset.get(position);

        // ViewHolder 의 데이터 변경
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
