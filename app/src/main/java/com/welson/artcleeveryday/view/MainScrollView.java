package com.welson.artcleeveryday.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.welson.artcleeveryday.MainActivity;

public class MainScrollView extends ScrollView {

    private MainActivity activity;


    public MainScrollView(Context context) {
        super(context);
    }

    public MainScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return MainActivity.isLeftMenuOpen || super.onTouchEvent(ev);
    }
}

