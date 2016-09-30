package com.blacktree.WorkCenter;

import com.blacktree.DBOperation.URLFrontier;
import com.blacktree.network.GetPageUsingGet;
import com.blacktree.url.URL;
import com.blacktree.utils.LinkCollect;
import com.blacktree.utils.MD5Util;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wangqchf on 2016/9/30.
 */
public class WorkItem implements Runnable {

    private WorkItemContext workItemContext;

    private AtomicLong maxCount;

    private URLFrontier urlFrontier;

    private GetPageUsingGet getPageUsingGet;

    private LinkCollect linkCollect;

    private WorkItemListener workItemListener;

    public WorkItem(WorkItemContext workItemContext, WorkItemListener workItemListener){
        this.workItemContext = workItemContext;
        urlFrontier = workItemContext.getUrlFrontier();
        this.workItemListener = workItemListener;
    }

    public void run() {
        URL url;
        List<String> list;
        StringBuilder stringBuilder;
        while(true) {
            checkMaxCount();
            url = getURLByURLFrontire();
            //TODO using the url as filePath, should use filePath variable of URL class.
            getPageUsingGet.setURI(url.getUrl());
            getPageUsingGet.saveHTMLInFile();
            linkCollect.setDocFile(url.getLocalFilePath());
            list = linkCollect.getLinkURL();
            if(list != null && list.size() > 0)
                for(int i = 0;i < list.size();i++) {
                    stringBuilder = new StringBuilder();
                    URL tempUrl = new URL();
                    tempUrl.setUrl(list.get(i));
                    //TODO use md5 key as the path of file，which should be fixed.
                    String tempKey = MD5Util.toMD5HexString(tempUrl.getUrl());
                    tempUrl.setLocalFilePath(tempKey);
                    stringBuilder.append(GetPageUsingGet.DEFAULT_DIRECTORY).append("\\").append(tempKey);
                    tempUrl.setLocalFilePath(stringBuilder.toString());
                    //urlFrontier.put(tempKey,tempUrl);
                    putURLInURLFrontier(tempKey,tempUrl);
                }
        }
    }

    private void checkMaxCount(){
        if(maxCount.get() <= 0){
            WorkItemEvent workItemEvent = new WorkItemEvent(this, WorkItemEventType.MAX_NUMBER_LIMIT);
            workItemListener.handleEvent(workItemEvent);
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private URL getURLByURLFrontire(){
        synchronized (urlFrontier){
            URL url = urlFrontier.getNextAndDelete();
            return url;
        }
    }

    private void putURLInURLFrontier(String key,URL url){
        synchronized (urlFrontier){
            urlFrontier.put(key,url);
        }
    }
}
