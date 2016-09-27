package com.blacktree.utils;

/**
 * Created by wangqchf on 2016/9/27.
 */
public class StringUtil {

    public static String removeHttpPrefix(String url) {
        String result;
        if(url.contains("http:"))
            result = url.substring(url.indexOf("http:") + 7, url.length() - 1);
        else if(url.contains("https:"))
            result = url.substring(url.indexOf("https:") + 8, url.length() - 1);
        else
            result = url;
        return result;
    }

    //TODO make the to a path of file !!

}
