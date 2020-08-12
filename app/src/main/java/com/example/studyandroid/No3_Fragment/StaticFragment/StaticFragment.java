package com.example.studyandroid.No3_Fragment.StaticFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studyandroid.R;


public class StaticFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    // Fragment를 사용하기 위해서는 빈 생성자가 필요하다.
    public StaticFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_static, container, false);
    }

    // 프래그먼트에서 뷰가 생성되면 onViewCreated 메소드가 콜백됨.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 버튼이 클릭되었을 때 처리내용을 액티비티에서 수행하기 위해 리스너를 정의한다.
                // 즉 이 리스너는 액티비티와 프래그먼트를 연결하는 인터페이스에 해당된다.
                if (mListener != null) {
                    mListener.onFragmentInteraction();
                }
            }
        });
    }


    // 액티비티와의 연결되긴 전 콜백 메소드인 onAttach() 에서 리스너가 구현되어있지 않다면 예외 발생시킴
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // 액티비티에 연결을 위한 인터페이스(리스너)가 구현되어있는 지 확인
        if(context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()
            + " OnFragmentInteractionListener를 구현하여야 합니다.");
        }
    }

    // 액티비티와의 연결을 해제하기 전에 콜백되는 onDetach() 에서 리스너에 대한 참조 제거
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // 액티비티와 연결하기 위한 인터페이스 OnFragmentInteractionListener
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(); // 추상메소드
    }


}
