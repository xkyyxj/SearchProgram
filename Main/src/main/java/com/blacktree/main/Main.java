package com.blacktree.main;

import com.blacktree.WorkCenter.WorkCenter;
import com.blacktree.url.URL;

/**
 * Created by wangqchf on 2016/9/26.
 */
public class Main {

    public static void main(String[] args){
        URL url  = new URL();
        url.setUrl("https://www.douyu.com/");
        url.setLocalFilePath("D:\\HTML\\www.douyu.com");
        WorkCenter workCenter = new WorkCenter(url);
        workCenter.setMaxCount(2);
        workCenter.run();
    }

}
