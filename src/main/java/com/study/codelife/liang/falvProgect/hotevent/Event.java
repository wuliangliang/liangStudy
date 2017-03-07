package com.study.codelife.liang.falvProgect.hotevent;
import java.sql.Date;

/**
 * Created by liang on 2017/3/7.
 */
    public class Event {
    private int id;
    private String title;
    private Date pubTime;
    private String content;
    private String source;
    private String imgs;
    private int number;
    private int day ;
    private int topic;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getPubTime() {
        return pubTime;
    }
    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getImgs() {
        return imgs;
    }
    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getTopic() {
        return topic;
    }
    public void setTopic(int topic) {
        this.topic = topic;
    }



}


