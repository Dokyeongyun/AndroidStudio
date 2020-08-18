package com.github.advanced_android.newgithubrepo.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class BindingAdapters {


/*
   repo_item.xml 에서
    bind:imageUrl="@{viewModel.repoImageUrl}"
   과 같이 설정하게 되면 BindingAdapter Annotation 이 붙은 메소드가 호출됨
   이 경우에는 loadImage() 메소드가 호출됨
*/
    @BindingAdapter({"imageUrl"})
    public static void loadImage(final ImageView imageView, final String imageUrl) {
        // 이미지는 Glide라는 라이브러리를 사용해 데이터를 설정한다
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                // 이미지를 동그랗게 오려낸다
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
