package com.defvest.devfestnorth.models;

public class schedules_model {
    private String Title, Time, Image, When, Where, Speaker, Category;
    public schedules_model() {

    }

    public schedules_model(String title, String time, String image, String when, String where, String speaker, String category) {
        Title = title;
        Time = time;
        Image = image;
        When = when;
        Where = where;
        Speaker = speaker;
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getWhen() {
        return When;
    }

    public void setWhen(String when) {
        When = when;
    }

    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }

    public String getSpeaker() {
        return Speaker;
    }

    public void setSpeaker(String speaker) {
        Speaker = speaker;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
