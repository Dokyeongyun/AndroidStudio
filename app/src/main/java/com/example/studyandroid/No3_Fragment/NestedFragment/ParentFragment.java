package com.example.studyandroid.No3_Fragment.NestedFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studyandroid.R;

public class ParentFragment extends Fragment {

    private int mNumber = 0;

    public static final String TAG_CHILD = "TAG_CHILD";
    public static final String KEY_NUMBER = "KEY_NUMBER";

    private FragmentManager.OnBackStackChangedListener mListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            FragmentManager fm = getChildFragmentManager();
            int count = 0;
            for (Fragment f : fm.getFragments()) {
                if (f != null) {
                    count++;
                }
            }
            mNumber = count;
        }
    };

    public ParentFragment() {
    }

    public static ParentFragment getInstance() {
        return new ParentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 추가버튼 기능 구현
        view.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager childFragmentManager = getChildFragmentManager();
                childFragmentManager.beginTransaction()
                        .add(R.id.fragment_container2, ChildFragment.getInstance(mNumber))
                        .addToBackStack(null)
                        .commit();
            }
        });

        // 삭제버튼 기능 구현
        view.findViewById(R.id.removeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNumber == 0) {
                    return;
                }
                FragmentManager childFragmentManager = getChildFragmentManager();
                childFragmentManager.popBackStack();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mNumber = savedInstanceState.getInt(KEY_NUMBER, 0);
        }

        FragmentManager childFragmentManager = getChildFragmentManager();
        Fragment fragment = childFragmentManager.findFragmentByTag(TAG_CHILD);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = childFragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container2, ChildFragment.getInstance(mNumber), TAG_CHILD)
                    .addToBackStack(null).commit();
        }

        childFragmentManager.addOnBackStackChangedListener(mListener);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.getInt(KEY_NUMBER, mNumber);
    }
}
