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
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wangqchf on 2016/9/22.
 */
//TODO make it working on multy threads
public class WorkCenter {

    private static final long DEFAULT_MAX_COUNT = 1000;

    private URLFrontier urlFrontier;

    private long maxCount = DEFAULT_MAX_COUNT;

    private AtomicLong atomicMaxCount;

    private int machineProcessorsNum = 0;

    private int threadNum = 2;

    private ThreadGroup threadGroup;

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
            machineProcessorsNum = Runtime.getRuntime().availableProcessors();
            threadGroup = new ThreadGroup("WorkItemGroup");
            atomicMaxCount = new AtomicLong(maxCount);
        }
    }

    public void run(){
        //TODO finish this method
        if(machineProcessorsNum > 0){
            threadNum += machineProcessorsNum;
        }
        Thread thread;
        WorkItemContext workItemContext = new WorkItemContext(atomicMaxCount,urlFrontier);
        workItemContext.setNumLimit(true);
        WorkItemEventListener workItemEventListener = new WorkItemEventListener();
        for(int i = 0;i < threadNum;i++){
            thread = new Thread(threadGroup,new WorkItem(workItemContext,workItemEventListener));
            thread.start();
        }
    }

    private void clean(){
        urlFrontier.clean();
    }

    private class WorkItemEventListener implements WorkItemListener{

        public void handleEvent(WorkItemEvent event) {
            //TODO finish this method!
            if(event.getEventType() == WorkItemEventType.DB_EMPTY){
                System.out.println("some thread are waiting!!!");
            } else if (event.getEventType() == WorkItemEventType.MAX_NUMBER_LIMIT){
                synchronized (WorkCenter.this) {
                    threadGroup.interrupt();
                }
            }
        }
    }

}
