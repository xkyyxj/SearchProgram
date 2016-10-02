package com.blacktree.network;

import com.blacktree.utils.IOUtil;
import com.blacktree.utils.MD5Util;
import com.blacktree.utils.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.HOST;
import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 *
 * @author wangqch
 * @time 2016-09-18
 */
public class GetPageUsingGet {

    /**
     * default directory of the documents
     */
    public static final String DEFAULT_DIRECTORY="D:\\HTML2";

    private String uri = null;

    private CloseableHttpClient httpClient = null;
    private HttpUriRequest httpUriRequest = null;

    static{
        File file = new File(DEFAULT_DIRECTORY);
        if(!file.exists())
            file.mkdir();
    }

    public GetPageUsingGet(String _uri){
        uri = _uri;
        initialize();
    }

    public GetPageUsingGet(){
        initialize();
    }

    /**
     * Use HttpClient to get HTML from <code>uri</code> and write the content
     * to file whose path is <code>filePath</code>
     *
     * @param filePath the path of file contain the HTML
     * @return result true if get the content and write them to the file
     */
    public boolean saveHTMLInFile(String filePath)
    {
        boolean result = false;
        File file = new File(filePath);
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return result;
            }
        if(file.exists()) {
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpUriRequest);
                HttpEntity httpEntity = response.getEntity();
                InputStream inputStream = httpEntity.getContent();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                IOUtil.writeByInputStream(inputStream,fileOutputStream);
                EntityUtils.consume(httpEntity);
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                return result;
            } finally{
                try {
                    if(response != null)
                        response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * Use HttpClient to get HTML from <code>uri</code> and write the content
     * to file whose path is from <code>formatFilePath</code>
     *
     * @return result true if get the content and write them to the file
     */
    public boolean saveHTMLInFile()
    {
        String filePath = formatFilePath();
        return saveHTMLInFile(filePath);
    }

    public void setURI(String uri){
        this.uri = uri;
        httpUriRequest = getHttpGet(uri);
    }

    /**
     * initialize of the object
     */
    private void initialize(){
        if(uri != null)
            httpUriRequest = getHttpGet(uri);
        httpClient = HttpClients.createDefault();
    }

    /**
     * Make default filePath of HTML document
     *
     * @return the path of HTML document
     */
    public String formatFilePath(){
        StringBuilder returnResult = new StringBuilder();
        /*String temp;
        temp = StringUtil.removeHttpPrefix(uri);*/
        //TODO using MD5 value as name of file
        returnResult.append(DEFAULT_DIRECTORY).append("\\").append(MD5Util.toMD5HexString(uri));
        return returnResult.toString();
    }

    private HttpUriRequest getHttpGet(String uri){
        HttpUriRequest result;
        result = new HttpGet(uri);
        //result.addHeader("Accept-Charset", DEFAULT_CHARSET);
        /*result.addHeader("Host", HOST);
        result.addHeader("Accept", ACCEPT);
        result.addHeader("User-Agent", USER_AGENT);*/
        return result;
    }

}
