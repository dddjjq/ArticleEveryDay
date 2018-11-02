package com.welson.artcleeveryday.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.welson.artcleeveryday.R;

public class MainToolbar extends Toolbar {

    private ImageView left,right;
    private OnLeftClickListener onLeftClickListener;
    private OnRightClickListener onRightClickListener;

    public MainToolbar(Context context) {
        super(context);
        init(context);
        initListener();
    }

    public MainToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initListener();
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.toolbar_layout,this,false);
        left = view.findViewById(R.id.toolbar_left);
        right = view.findViewById(R.id.toolbar_right);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(view,layoutParams);
    }

    private void initListener(){
        left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftClickListener.onLeftClick();
            }
        });
        right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightClickListener.onRightClick();
            }
        });
    }

    public void setOnLeftClickListener(OnLeftClickListener onLeftClickListener){
        this.onLeftClickListener = onLeftClickListener;
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener){
        this.onRightClickListener = onRightClickListener;
    }

    public interface OnLeftClickListener{
        void onLeftClick();
    }

    public interface OnRightClickListener{
        void onRightClick();
    }
}
