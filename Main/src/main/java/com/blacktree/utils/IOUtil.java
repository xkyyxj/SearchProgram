package com.blacktree.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The tools of IO operation
 *
 * @author wangqch
 * @time 2016-09-18
 */
public class IOUtil {

    /**
     * Get content from InputStream and then write them to the OutputStream
     *
     * @param inputStream input
     * @param outputStream output
     */
    public static void writeByInputStream(InputStream inputStream, OutputStream outputStream){
        byte[] content = new byte[1024];
        int length;
        try {
            do {
                length = inputStream.read(content);
                if(length > 0)
                    outputStream.write(content,0,length);
            } while(length > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
