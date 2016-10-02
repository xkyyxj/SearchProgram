package com.blacktree.WorkCenter;

import com.blacktree.DBOperation.URLFrontier;
import com.blacktree.url.URL;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wangqchf on 2016/9/30.
 */
public class WorkItemContext {

    private AtomicLong maxCount;

    private URLFrontier urlFrontier;

    private boolean numLimit;

    public WorkItemContext(AtomicLong maxCount, URLFrontier urlFrontier){
        this.maxCount = maxCount;
        this.urlFrontier = urlFrontier;
    }

    public AtomicLong getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(AtomicLong maxCount) {
        this.maxCount = maxCount;
    }

    public URLFrontier getUrlFrontier() {
        return urlFrontier;
    }

    public void setUrlFrontier(URLFrontier urlFrontier) {
        this.urlFrontier = urlFrontier;
    }

    public boolean isNumLimit() {
        return numLimit;
    }

    public void setNumLimit(boolean numLimit) {
        this.numLimit = numLimit;
    }
}
