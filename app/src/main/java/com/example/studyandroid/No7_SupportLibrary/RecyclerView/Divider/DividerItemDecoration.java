package com.example.studyandroid.No7_SupportLibrary.RecyclerView.Divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final int dividerHeight;
    private Drawable divider;

    public DividerItemDecoration(Context context) {
        // 기본 ListView 구분선의 Drawable 을 얻음
        final TypedArray a = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        divider = a.getDrawable(0);

        // 표시할 때마다 높이를 가져오지 않아도 되도록 미리 구해둠
        dividerHeight = divider.getIntrinsicHeight();
        a.recycle();
    }

    // View 의 아이템 위에 그리고 싶을 때 사용하는 메소드
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    // View 의 아이템 아래에 그리고 싶을 때 사용하는 메소드
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int lineLeft = parent.getPaddingLeft();
        final int lineRight = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // 애니메이션 등의 상황에서 제대로 이동하기 위해
            int childTransitionY = Math.round(ViewCompat.getTranslationY(child));
            final int top = child.getBottom() + params.bottomMargin + childTransitionY;
            final int bottom = top + dividerHeight;

            // View 아래에 선을 그림
            divider.setBounds(lineLeft, top, lineRight, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.set(0, 0, 0, dividerHeight);
    }
}
