package com.welson.artcleeveryday.util;

public class HtmlUtil {
    public static String getHtmlString(String body){
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + body + "</body></html>";
    }
}
