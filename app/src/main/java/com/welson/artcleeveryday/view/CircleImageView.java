package com.welson.artcleeveryday.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.welson.artcleeveryday.R;

public class CircleImageView extends AppCompatImageView {

    private Paint paint;
    private int color;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        color = ta.getColor(R.styleable.CircleImageView_color, Color.WHITE);
        ta.recycle();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        int radius = Math.min(getMeasuredHeight(),getMeasuredWidth())/2;
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,radius,paint);
    }

    public void setColor(int color){
        this.color = color;
        Log.d("dingyl","setColor : " + color);
        invalidate();
    }
}
