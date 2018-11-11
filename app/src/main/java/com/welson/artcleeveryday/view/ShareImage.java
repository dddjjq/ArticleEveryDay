package com.welson.artcleeveryday.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.welson.artcleeveryday.R;

public class ShareImage extends FrameLayout {

    private ImageView innerImage;

    public ShareImage(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ShareImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.share_layout_image,this,true);
        innerImage = view.findViewById(R.id.share_inner_image);
    }

    public void setInnerImage(int res){
        innerImage.setImageResource(res);
    }
}
