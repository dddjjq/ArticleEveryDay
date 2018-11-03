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
        init(context);
    }

    public MainScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        activity = (MainActivity)context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return MainActivity.isLeftMenuOpen || super.onTouchEvent(ev);
    }

}

