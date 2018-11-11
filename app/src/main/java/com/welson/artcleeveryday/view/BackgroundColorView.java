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

public class BackgroundColorView extends FrameLayout {

    private CircleImageView backgroundImage;
    private ImageView selectImage;

    public BackgroundColorView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BackgroundColorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.background_color_layout,this,true);
        backgroundImage = view.findViewById(R.id.background_color);
        selectImage = view.findViewById(R.id.background_select);
    }

    public void setBackgroundColor(int color){
        backgroundImage.setColor(color);
    }

    public void setSelected(boolean selected){
        if (selected){
            selectImage.setVisibility(VISIBLE);
        }else {
            selectImage.setVisibility(GONE);
        }
    }
}
