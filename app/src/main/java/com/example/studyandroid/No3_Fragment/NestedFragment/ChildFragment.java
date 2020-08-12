package com.example.studyandroid.No3_Fragment.NestedFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studyandroid.R;

public class ChildFragment extends Fragment {
    private static final String ARG_NO = "ARG_NO";

    public ChildFragment() {
    }

    public static ChildFragment getInstance(int mNumber) {
        ChildFragment fragment = new ChildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NO, mNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.text);
        int no = getArguments().getInt(ARG_NO,0);
        String text = ""+no+" 번째 자식 프래그먼트";
        textView.setText(text);
    }
}
