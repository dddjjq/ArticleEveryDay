package com.welson.artcleeveryday.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.welson.artcleeveryday.R;
import com.welson.artcleeveryday.activity.MainActivity;
import com.welson.artcleeveryday.constants.Constants;
import com.welson.artcleeveryday.util.DensityUtil;
import com.welson.artcleeveryday.util.SharedPreferenceUtil;
import com.welson.artcleeveryday.view.BackgroundColorView;

import java.util.ArrayList;

public class SettingDialogFragment extends DialogFragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    private Button smallSizeBtn;
    private Button middleSizeBtn;
    private Button largeSizeBtn;
    private BackgroundColorView backgroundColorView1;
    private BackgroundColorView backgroundColorView2;
    private BackgroundColorView backgroundColorView3;
    private BackgroundColorView backgroundColorView4;
    private Switch mSwitch;
    private int[] colors = {Color.rgb(247,247,247),Color.rgb(214,238,211)
            ,Color.rgb(221,198,158),Color.rgb(251,226,226)};
    private SharedPreferenceUtil sharedPreferenceUtil;
    private ArrayList<BackgroundColorView> backgroundColorViews;
    private MainActivity activity;
    private ArrayList<Button> buttons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setWindowAnimations(R.style.setting_dialog_anim);
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        initBackground();
    }

    private void init(){
        activity = (MainActivity) getActivity();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.setDimAmount(0f);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lm = window.getAttributes();
        lm.width = WindowManager.LayoutParams.MATCH_PARENT;
        lm.height = DensityUtil.dp2px(getContext(),180);
        lm.gravity = Gravity.BOTTOM;
        window.setAttributes(lm);
        sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        backgroundColorViews = new ArrayList<>();
        boolean isNightMode = sharedPreferenceUtil.getEnableNightMode();
        mSwitch.setChecked(isNightMode);

    }

    private void initView(View view){
        buttons = new ArrayList<>();
        smallSizeBtn = view.findViewById(R.id.text_size_small_btn);
        middleSizeBtn = view.findViewById(R.id.text_size_middle_btn);
        largeSizeBtn = view.findViewById(R.id.text_size_large_btn);
        mSwitch = view.findViewById(R.id.switch_night);
        backgroundColorView1 = view.findViewById(R.id.backgroundColorView1);
        backgroundColorView2 = view.findViewById(R.id.backgroundColorView2);
        backgroundColorView3 = view.findViewById(R.id.backgroundColorView3);
        backgroundColorView4 = view.findViewById(R.id.backgroundColorView4);
        backgroundColorView1.setOnClickListener(this);
        backgroundColorView2.setOnClickListener(this);
        backgroundColorView3.setOnClickListener(this);
        backgroundColorView4.setOnClickListener(this);
        mSwitch.setOnCheckedChangeListener(this);
        smallSizeBtn.setOnClickListener(this);
        middleSizeBtn.setOnClickListener(this);
        largeSizeBtn.setOnClickListener(this);
        buttons.add(smallSizeBtn);
        buttons.add(middleSizeBtn);
        buttons.add(largeSizeBtn);
    }

    private void initBackground(){
        int textSize = sharedPreferenceUtil.getTextSizeIdx();
        int colorIdx = sharedPreferenceUtil.getBackColor();
        backgroundColorView1.setBackgroundColor(colors[0]);
        backgroundColorView2.setBackgroundColor(colors[1]);
        backgroundColorView3.setBackgroundColor(colors[2]);
        backgroundColorView4.setBackgroundColor(colors[3]);
        backgroundColorViews.add(backgroundColorView1);
        backgroundColorViews.add(backgroundColorView2);
        backgroundColorViews.add(backgroundColorView3);
        backgroundColorViews.add(backgroundColorView4);
        selectBack(colorIdx);
        setTextSizeIdx(textSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backgroundColorView1:
                sharedPreferenceUtil.setBackColor(0);
                selectBack(0);
                activity.initSizeColor();
                mSwitch.setChecked(false);
                break;
            case R.id.backgroundColorView2:
                sharedPreferenceUtil.setBackColor(1);
                selectBack(1);
                activity.initSizeColor();
                mSwitch.setChecked(false);
                break;
            case R.id.backgroundColorView3:
                sharedPreferenceUtil.setBackColor(2);
                selectBack(2);
                activity.initSizeColor();
                mSwitch.setChecked(false);
                break;
            case R.id.backgroundColorView4:
                sharedPreferenceUtil.setBackColor(3);
                selectBack(3);
                activity.initSizeColor();
                mSwitch.setChecked(false);
                break;
            case R.id.text_size_small_btn:
                sharedPreferenceUtil.setTextSizeIdx(0);
                activity.initSizeColor();
                setTextSizeIdx(0);
                break;
            case R.id.text_size_middle_btn:
                sharedPreferenceUtil.setTextSizeIdx(1);
                activity.initSizeColor();
                setTextSizeIdx(1);
                break;
            case R.id.text_size_large_btn:
                sharedPreferenceUtil.setTextSizeIdx(2);
                activity.initSizeColor();
                setTextSizeIdx(2);
                break;
        }
    }

    private void selectBack(int colorIdx){
        for (int i = 0;i<4;i++){
            if (i == colorIdx){
                backgroundColorViews.get(i).setSelected(true);
            }else {
                backgroundColorViews.get(i).setSelected(false);
            }
        }
    }

    private void setTextSizeIdx(int idx){
        for (int i=0;i<buttons.size();i++){
            if (idx == i){
                buttons.get(i).setSelected(true);
                buttons.get(i).setTextColor(Color.WHITE);
            }else {
                buttons.get(i).setSelected(false);
                buttons.get(i).setTextColor(Color.BLACK);
            }
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        sharedPreferenceUtil.setEnableNightMode(isChecked);
        if (isChecked){
            activity.initSizeColor();
        }else {
            activity.initSizeColor();
        }
    }
}
