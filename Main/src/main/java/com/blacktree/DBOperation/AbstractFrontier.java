package com.blacktree.DBOperation;

import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqchf on 2016/9/22.
 */
public abstract class AbstractFrontier {

    public static final String  DEFAULT_DATABASE_DIRECTORY = "C:\\database";

    protected Environment environment;

    protected Database database;

    protected EnvironmentConfig environmentConfig;

    protected DatabaseConfig databaseConfig;

    protected List<DatabaseListener> databaseListenerList;

    private String homeDirectory;

    public AbstractFrontier(){
        this(null);
    }

    public AbstractFrontier(String homeDirectory){
        if(homeDirectory != null)
            this.homeDirectory = homeDirectory;
        else
            this.homeDirectory = DEFAULT_DATABASE_DIRECTORY;
        initialize();
    }


    public void addDatabaseListener(DatabaseListener databaseListener) {
        databaseListenerList.add(databaseListener);
    }

    private void initialize(){
        //TODO remove the next line
        System.out.println("URLFrontierTemp Initialized!");
        environmentConfig = new EnvironmentConfig();
        environmentConfig.setAllowCreate(true);

        File file = new File(homeDirectory);
        if(!file.exists())
            file.mkdir();
        environment = new Environment(file,environmentConfig);
        databaseConfig = new DatabaseConfig();
        databaseConfig.setAllowCreate(true);
        databaseConfig.setSortedDuplicates(false);
        database = environment.openDatabase(null, "url_database", databaseConfig);

        databaseListenerList = new ArrayList<DatabaseListener>();
    }

    protected void clean(){
        databaseConfig.clone();
        database.close();
    }

}
