package com.blacktree.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqchf on 2016/9/18.
 */
public class LinkCollect {

    private String docFilePath = null;

    private File docFile = null;

    private Document document = null;

    public LinkCollect(String filePath) {
        if(filePath != null) {
            docFilePath = filePath;
            initialize();
        }
    }

    public LinkCollect(){
        this(null);
    }

    public List<String> getLinkURL(){
        String temp;
        List<String> returnList = new ArrayList<String>();
        Elements links = document.select("a[href]");
        Elements imports = document.select("link[href]");
        for (Element link : links) {
            temp = link.attr("abs:href");
            if(temp != null && !temp.equals(""))
                returnList.add(temp);
        }
        for(Element link : imports) {
            temp = link.attr("abs:href");
            if(temp != null && !temp.equals(""))
                returnList.add(temp);
        }
        return returnList;
    }

    public void setDocFile(String filePath){
        docFilePath = filePath;
        initialize();
    }

    private void initialize(){
        docFile = new File(docFilePath);
        int index = docFilePath.lastIndexOf("\\");
        String baseURL = docFilePath.substring(index + 1);
        try {
            document = Jsoup.parse(docFile,"utf-8",baseURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
