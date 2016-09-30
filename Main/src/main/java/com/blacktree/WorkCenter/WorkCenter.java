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

    private WorkCenterDatabaseListener workCenterDatabaseListener;

    private long maxCount = DEFAULT_MAX_COUNT;

    private int machineProcessorsNum = 0;

    private int threadNum = 2;

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
            workCenterDatabaseListener = new WorkCenterDatabaseListener();
            urlFrontier.setDatabaseListener(workCenterDatabaseListener);
            machineProcessorsNum = Runtime.getRuntime().availableProcessors();
        }
    }

    public void run(){
        //TODO finish this method
        if(machineProcessorsNum > 0){
            threadNum += machineProcessorsNum;
        }
        for(int i = 0;i < threadNum;i++){

        }
    }

    private void clean(){
        urlFrontier.clean();
    }

    private class WorkCenterDatabaseListener implements DatabaseListener{

        public void databaseChanged(DatabaseEvent databaseEvent) {
            if(databaseEvent.getDbEventType() == DBEventType.DBEMPTY){
                //TODO 如果仍有线程正在产生网页链接信息，需处理
                clean();
            }
        }
    }

    private class WorkItemEventListener implements WorkItemListener{

        public void handleEvent(WorkItemEvent event) {
            //TODO finish this method!
        }
    }

}
