package com.defvest.devfestnorth.models;

public class organizers_model {
    private String Name, GDG, Photo, About;

    public organizers_model() {

    }

    public organizers_model(String Name, String GDG, String Photo, String About) {
        this.Name = Name;
        this.GDG   = GDG;
        this.Photo = Photo;
        this.About = About;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGDG() {
        return GDG;
    }

    public void setGDG(String GDG) {
        this.GDG = GDG;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }
}
