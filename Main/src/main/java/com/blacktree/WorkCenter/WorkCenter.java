package com.blacktree.WorkCenter;

import com.blacktree.DBOperation.DBEventType;
import com.blacktree.DBOperation.DatabaseEvent;
import com.blacktree.DBOperation.DatabaseListener;
import com.blacktree.DBOperation.URLFrontier;
import com.blacktree.url.URL;
import com.blacktree.network.GetPageUsingGet;
import com.blacktree.utils.LinkCollect;
import com.blacktree.utils.MD5Util;
import com.blacktree.utils.StringUtil;

import java.util.List;

/**
 * Created by wangqchf on 2016/9/22.
 */
//TODO make it working on multy threads
public class WorkCenter {

    private static final long DEFAULT_MAX_COUNT = 1000;

    private URLFrontier urlFrontier;

    private GetPageUsingGet getPageUsingGet;

    private LinkCollect linkCollect;

    private WorkCenterDatabaseListener workCenterDatabaseListener;

    private long maxCount = DEFAULT_MAX_COUNT;

    private long count = 0;

    public WorkCenter(URL ... urls){
        initialize(urls);
    }

    public void setMaxCount(long count){
        maxCount = count;
    }

    private void initialize(URL[] urls){
        if(urls != null) {
            urlFrontier = new URLFrontier();
            for (int i = 0; i < urls.length; i++) {
                //urlFrontier.putURL(urls[i]);
                urlFrontier.put(MD5Util.toMD5HexString(urls[i].getUrl()),urls[i]);
            }
            getPageUsingGet = new GetPageUsingGet();
            linkCollect = new LinkCollect();
            workCenterDatabaseListener = new WorkCenterDatabaseListener();
            urlFrontier.setDatabaseListener(workCenterDatabaseListener);
        }
    }

    public void run(){
        URL url;
        List<String> list;
        StringBuilder stringBuilder;
        while((url = urlFrontier.getNextAndDelete()) != null && (count++) < maxCount) {
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
                    urlFrontier.put(tempKey,tempUrl);
                }
        }
    }

    private void clean(){
        urlFrontier.clean();
    }

    class WorkCenterDatabaseListener implements DatabaseListener{

        public void databaseChanged(DatabaseEvent databaseEvent) {
            if(databaseEvent.getDbEventType() == DBEventType.DBEMPTY){
                //TODO If finish search, then clean all things
                clean();
            }
        }
    }

}
