package com.example.studyandroid.No7_SupportLibrary.RecyclerView.Grid;

import android.icu.text.AlphabeticIndex;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyandroid.R;

import java.util.List;

public class RichAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_VIEW_TYPE = 0;
    public static final int HEADER_VIEW_TYPE = 1;
    private List<String> dataset;


    // ITEM_VIEW_TYPE 용 ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.simpleTextView);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleTextView;
        public final TextView detailTextView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            detailTextView = itemView.findViewById(R.id.detailTextView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataset.get(position).startsWith("■")) {
            return HEADER_VIEW_TYPE;
        } else {
            return ITEM_VIEW_TYPE;
        }
    }

    public RichAdapter(List<String> myDataset) {
        dataset = myDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case ITEM_VIEW_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_row, parent, false);
                return new ItemViewHolder(view);
            }
            case HEADER_VIEW_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_row, parent, false);
                return new HeaderViewHolder(view);
            }
            default:
                throw new RuntimeException("존재하지 않는 ViewType 입니다.");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String text = dataset.get(position);

        switch (holder.getItemViewType()) {
            case ITEM_VIEW_TYPE: {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.textView.setText(text);
                break;
            }
            case HEADER_VIEW_TYPE: {
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.titleTextView.setText("시리즈: " + text);
                headerViewHolder.detailTextView.setText(text + " 시리즈입니다.");
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
