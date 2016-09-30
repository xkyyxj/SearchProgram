package com.blacktree.WorkCenter;

/**
 * Created by wangqchf on 2016/9/30.
 */
public class WorkItemEvent {

    private WorkItem sourceWorkItem;

    private WorkItemEventType eventType;

    public WorkItemEvent(WorkItem workItem,WorkItemEventType eventType){
        this.sourceWorkItem = workItem;
        this.eventType = eventType;
    }

    public WorkItem getSourceWorkItem() {
        return sourceWorkItem;
    }

    public WorkItemEventType getEventType() {
        return eventType;
    }
}
