package com.example.studyandroid.No2_ViewAndLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.studyandroid.R;


public class MyCustomView extends LinearLayout {
    private ImageView mStar1;
    private ImageView mStar2;
    private ImageView mStar3;
    private int mSelected = 0;

    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context, attrs);
    }


    // 레이아웃을 초기화하는 메소드
    private void initializeViews(Context context, AttributeSet attrs) {

        // 1. 인플레이터 선언
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. 인플레이터 전개 (매개변수 : int resource, ViewGroup root)
        inflater.inflate(R.layout.three_stars_indicator, this);
        if (attrs != null) {
            // 3. attrs.xml 에 정의한 스타일을 가져온다.
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView);
            mSelected = a.getInteger(0, 0);
            a.recycle(); // 4. 이용완료 했으면 recycle() 호출
        }
    }

    // inflate가 완료되는 시점에서 콜백되는 메소드
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mStar1 = findViewById(R.id.star1);
        mStar2 = findViewById(R.id.star2);
        mStar3 = findViewById(R.id.star3);

        // 처음에 xml로부터의 지정을 반영시키기 위해 force를 true로 함
        setSelected(mSelected, true);
    }

    public void setSelected(int select) {
        setSelected(select, false);
    }


    private void setSelected(int select, boolean force) {

        if (force || mSelected != select) {
            if (2 > mSelected && mSelected < 0) {
                return;
            }

            mSelected = select;
            if(mSelected==0){
                mStar1.setImageResource(R.drawable.star);
                mStar2.setImageResource(R.drawable.star_empty);
                mStar3.setImageResource(R.drawable.star_empty);
            }else if(mSelected==1){
                mStar1.setImageResource(R.drawable.star_empty);
                mStar2.setImageResource(R.drawable.star);
                mStar3.setImageResource(R.drawable.star_empty);
            }else if(mSelected==2){
                mStar1.setImageResource(R.drawable.star_empty);
                mStar2.setImageResource(R.drawable.star_empty);
                mStar3.setImageResource(R.drawable.star);
            }
        }
    }

    public int getSelected(){
        return mSelected;
    }

}
