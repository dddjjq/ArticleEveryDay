package com.welson.artcleeveryday.util;

import android.util.Log;

public class StringUtil {
    public static String getRealString(String body){
        String result = body.substring(0,body.length()-4).replaceAll("\\<[p]\\>","        ")
                .replaceAll("\\<\\/[p]\\>","\r\n\n") + "\r\n";
        return result;
    }
}
