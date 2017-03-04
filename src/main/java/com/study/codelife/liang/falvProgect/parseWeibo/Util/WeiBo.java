package com.study.codelife.liang.falvProgect.parseWeibo.Util;


import java.util.ArrayList;

/**
 * Created by liang on 2017/2/19.
 */
public class WeiBo {
    private int id;
    private String uuid;
    private String time;
    private String content;
    private String retweet;
    private String contentLook;
    private String retweetLook;
    private ArrayList<String> contentList;
    private ArrayList<String> retweetList;

    public ArrayList<String> getContentList() {
        return contentList;
    }

    public void setContentList(ArrayList<String> contentList) {
        this.contentList = contentList;
    }

    public ArrayList<String> getRetweetList() {
        return retweetList;
    }

    public void setRetweetList(ArrayList<String> retweetList) {
        this.retweetList = retweetList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRetweet() {
        return retweet;
    }

    public void setRetweet(String retweet) {
        this.retweet = retweet;
    }

    public String getContentLook() {
        return contentLook;
    }

    public void setContentLook(String contentLook) {
        this.contentLook = contentLook;
    }

    public String getRetweetLook() {
        return retweetLook;
    }
    public void setRetweetLook(String retweetLook) {
        this.retweetLook = retweetLook;
    }
}
