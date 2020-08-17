package com.example.studyandroid.No7_SupportLibrary.RecyclerView.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyandroid.R;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    List<String> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.simpleTextView2);
        }
    }

    public CardViewAdapter(List<String> myDataset) {
        dataset = myDataset;
    }

    @NonNull
    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_row_card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.ViewHolder holder, int position) {
        String text = dataset.get(position);
        holder.textView.setText(text);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
