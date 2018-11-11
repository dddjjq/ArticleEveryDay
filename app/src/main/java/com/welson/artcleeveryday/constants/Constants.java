package com.welson.artcleeveryday.constants;

import android.graphics.Color;

import com.welson.artcleeveryday.R;

public class Constants {
    public static final String BASE_URL = "https://interface.meiriyiwen.com/article/";
    public static final String TODAY = "today?dev=1";
    public static final String DATE_ARTICLE = "day?dev=1&date=";
    public static final String RANDOM = "random?dev=1";
    public static final String DATE_SP = "date";
    public static final String CURRENT_DATE = "current_date";
    public static final String TODAY_DATE = "today_date";
    public static final String BEFORE_DATE = "before_date";
    public static final String NEXT_DATE = "next_date";

    public static final String TEXT_SIZE = "text_size";
    public static final String BACK_COLOR = "back_color";
    public static final String NIGHT_MODE = "night_mode";
    public static int[] textSizesTitle = {24,25,26};
    public static int[] textSizesAuthor = {12,13,14};
    public static int[] textSizesContent = {17,18,19};
    public static int[] colors = {Color.rgb(247,247,247),Color.rgb(214,238,211)
            ,Color.rgb(221,198,158),Color.rgb(251,226,226)};
    public static int[] textColors = {Color.rgb(0,0,0),Color.rgb(53,87,62)
            ,Color.rgb(125,98,74),Color.rgb(165,107,117)};
    public static int nightBackColor = Color.rgb(50,55,59);
    public static int nightTextColor = Color.rgb(99,99,99);
}
