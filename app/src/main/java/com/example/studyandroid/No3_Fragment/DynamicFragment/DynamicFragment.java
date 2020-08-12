package com.example.studyandroid.No3_Fragment.DynamicFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studyandroid.R;

public class DynamicFragment extends Fragment {

    private static final String ARG_NO = "ARG_NO";

    public DynamicFragment() {
    }

    // 프래그먼트는 액티비티 내에 구현되어 있어 액티비티가 재생성될 경우 액티비티와 함께 재생성된다.
    // 액티비티에서의 onSaveInstanceState / onRestoreInstanceState 와 같이
    // 프래그먼트에도 재생성 시에 프래그먼트의 초깃값을 설정하는 방법으로
    // Fragment.setArguments(Bundle)이 있다.
    public static DynamicFragment getInstance(int mNumber) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NO, mNumber);
        // 1. 프래그먼트의 초깃값을 설정한 Bundle 을 setArguments()로 설정한다.
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView fragmentText = view.findViewById(R.id.fragmentText);
        // getArguments()를 이용하여 초깃값을 불러옴
        int mNumber = getArguments().getInt(ARG_NO, 0);
        String text = "" + mNumber + "번째 프래그먼트";
        fragmentText.setText(text);
    }
}
