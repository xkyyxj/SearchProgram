package com.blacktree.utils;

import java.util.*;

/**
 * Created by wangqchf on 2016/9/27.
 */
public class StringUtil {

    private static List<String> keyString, valueString;

    static{
        keyString = new ArrayList<String>();
        valueString = new ArrayList<String>();
        keyString.add(" ");
        valueString.add("%20");
    }

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

    public static String encodingSpecialChaOfURL(String input){
        String key, result = input;
        for(int i = 0;i < keyString.size();i++){
            key = keyString.get(i);
            if(input.contains(key)){
                result = input.replace(key,valueString.get(i));
            }
        }
        return result;
    }

    //TODO make the to a path of file !!

}
