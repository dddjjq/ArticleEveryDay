package com.welson.artcleeveryday.util;

import android.util.Log;

public class StringUtil {
    public static String getRealString(String body){
        String result = body.replaceAll("\\<[p]\\>","        ")
                .replaceAll("\\<\\/[p]\\>","\r\n\n");
        return result;
    }
}
