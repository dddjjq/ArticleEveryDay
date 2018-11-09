package com.welson.artcleeveryday.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.welson.artcleeveryday.R;

public class ToastUtil {

    private static Toast toast;
    private TextView toastText;
    private View view;

    public ToastUtil(Context context){
        toast = new Toast(context);
        view = LayoutInflater.from(context).inflate(R.layout.toast_layout,null);
        toastText = view.findViewById(R.id.toast_text);
    }

    public void setToastText(String text){
        toastText.setText(text);
        toast.setGravity(Gravity.CENTER,12,10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
