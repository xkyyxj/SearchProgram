package com.blacktree.DBOperation;

/**
 * Created by wangqchf on 2016/9/27.
 */
public class DatabaseEvent {

    private AbstractFrontier eventSource;

    private DBEventType dbEventType;

    public DatabaseEvent(DBEventType dbEventType, AbstractFrontier abstractFrontier){
        this.dbEventType = dbEventType;
        eventSource = abstractFrontier;
    }

    public AbstractFrontier getEventSource() {
        return eventSource;
    }

    public DBEventType getDbEventType() {
        return dbEventType;
    }

}
