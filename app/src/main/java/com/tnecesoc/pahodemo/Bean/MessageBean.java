package com.tnecesoc.pahodemo.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tnecesoc on 2016/9/8.
 */
public class MessageBean {
    private String topic;
    private String publishTime;

    private String author;
    private String content;

    public MessageBean(String topic, String publishTime, String author, String content) {
        this.topic = topic;
        this.publishTime = publishTime;
        this.author = author;
        this.content = content;
    }

    public MessageBean(String topic, Date publishTime, String author, String content) {
        this.topic = topic;
        this.publishTime = new SimpleDateFormat("y:M:d H:m:s", Locale.getDefault()).format(publishTime);
        this.author = author;
        this.content = content;
    }

    public Date toNativeDate() {
        try {
            return new SimpleDateFormat("y-M-d H:m:s", Locale.getDefault()).parse(publishTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String  getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

}
