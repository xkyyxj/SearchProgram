package com.blacktree.url;

import java.io.Serializable;

/**
 * Created by wangqchf on 2016/9/26.
 */
public class URL implements Serializable {

    private String url;

    private String localFilePath;

    private long depth;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDepth() {
        return depth;
    }

    public void setDepth(long depth) {
        this.depth = depth;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }
}
