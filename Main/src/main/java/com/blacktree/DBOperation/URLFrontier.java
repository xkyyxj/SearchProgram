package com.blacktree.DBOperation;

import com.blacktree.url.URL;
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.*;

/**
 * Created by wangqchf on 2016/9/24.
 */
public class URLFrontier extends AbstractFrontier{

    private EntryBinding<URL> valueEntryBinding;

    private Cursor readCursor;

    private DatabaseConfig classDatabaseConfig;

    private Database classDatabase;

    private StoredClassCatalog storedClassCatalog;

    public URLFrontier(){
        this(null);
    }

    public URLFrontier(String homeDirectory){
        super(homeDirectory);
        initialize();
    }

    public URL get(String key) {
        DatabaseEntry keyEntry = new DatabaseEntry(key.getBytes());
        DatabaseEntry valueEntry = new DatabaseEntry();
        OperationStatus operationStatus = database.get(null,keyEntry,valueEntry,LockMode.DEFAULT);
        if(operationStatus == OperationStatus.SUCCESS)
            return valueEntryBinding.entryToObject(valueEntry);
        return null;
    }

    public URL getNextAndDelete(){
        URL tempURL;
        if(readCursor == null)
            readCursor = getReadCursor();
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry valueEntry = new DatabaseEntry();
        if(readCursor.getNext(keyEntry,valueEntry, LockMode.DEFAULT) == OperationStatus.SUCCESS){
            tempURL = valueEntryBinding.entryToObject(valueEntry);
            database.delete(null,keyEntry);
            return tempURL;
        } else{
            readCursor = getReadCursor();
            if(readCursor.getNext(keyEntry,valueEntry, LockMode.DEFAULT) == OperationStatus.SUCCESS){
                tempURL = valueEntryBinding.entryToObject(valueEntry);
                database.delete(null,keyEntry);
                return tempURL;
            } else {
                DatabaseEvent databaseEvent = new DatabaseEvent(DBEventType.DBEMPTY,this);
                databaseListener.databaseChanged(databaseEvent);
            }
        }
        return null;
    }

    public boolean put(String key, URL value) {
        DatabaseEntry keyEntry = new DatabaseEntry(key.getBytes());
        DatabaseEntry valueEntry = new DatabaseEntry();
        valueEntryBinding.objectToEntry(value,valueEntry);
        OperationStatus operationStatus = database.put(null,keyEntry,valueEntry);
        if(operationStatus == OperationStatus.SUCCESS)
            return true;
        else
            return false;
    }

    public boolean putURL(URL url){
        return put(url.getUrl(),url);
    }

    public void delete(String key) {
        DatabaseEntry databaseEntry = new DatabaseEntry(key.getBytes());
        database.delete(null,databaseEntry);
    }

    private void initialize(){
        classDatabaseConfig = new DatabaseConfig();
        classDatabaseConfig.setAllowCreate(true);
        classDatabaseConfig.setSortedDuplicates(false);
        classDatabase = environment.openDatabase(null,"class_database", classDatabaseConfig);
        storedClassCatalog = new StoredClassCatalog(classDatabase);
        valueEntryBinding = new SerialBinding<URL>(storedClassCatalog,URL.class);
    }

    private Cursor getReadCursor(){
        Cursor cursor = database.openCursor(null,null);
        return cursor;
    }

    public void clean(){
        if(readCursor != null)
            readCursor.close();
        super.clean();
    }

}
