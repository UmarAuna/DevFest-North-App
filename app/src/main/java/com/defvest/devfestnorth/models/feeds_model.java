package com.defvest.devfestnorth.models;

public class feeds_model {
    private String Title, Content, Time;

    public feeds_model(String title, String content, String time) {
        Title = title;
        Content = content;
        Time = time;
    }

    public feeds_model() {

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
