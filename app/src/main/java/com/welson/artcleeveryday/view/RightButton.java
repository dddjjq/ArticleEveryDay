package com.welson.artcleeveryday.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welson.artcleeveryday.R;

public class RightButton extends LinearLayout {

    private int imageId;
    private String text;
    private ImageView imageView;
    private TextView textView;

    public RightButton(Context context) {
        super(context);
    }

    public RightButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RightButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context,AttributeSet attributeSet){
        View view = LayoutInflater.from(context).inflate(R.layout.right_button,this,true);
        imageView = view.findViewById(R.id.image);
        textView = view.findViewById(R.id.text_view);
        TypedArray ta = context.obtainStyledAttributes(attributeSet,R.styleable.RightButton);
        imageId = ta.getResourceId(R.styleable.RightButton_image,R.drawable.right_collect_icon);
        text = ta.getString(R.styleable.RightButton_text);
        ta.recycle();
        imageView.setImageResource(imageId);
        textView.setText(text);
    }
}
