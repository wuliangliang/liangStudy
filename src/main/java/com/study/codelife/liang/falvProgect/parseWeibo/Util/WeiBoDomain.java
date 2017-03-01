package com.study.codelife.liang.falvProgect.parseWeibo.Util;

import java.sql.Blob;

/**
 * Created by liang on 2017/2/25.
 */
public class WeiBoDomain {
    private int id;
    private String uuid;
    private String time;
    private Blob content;
    private Blob retweet;
    private String contentLook;
    private String retweetLook;

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

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

    public Blob getRetweet() {
        return retweet;
    }

    public void setRetweet(Blob retweet) {
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
