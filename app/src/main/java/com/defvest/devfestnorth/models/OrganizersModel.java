package com.defvest.devfestnorth.models;

public class OrganizersModel {
    private String Name, GDG, Photo, About;

    public OrganizersModel() {

    }

    public OrganizersModel(String Name, String GDG, String Photo, String About) {
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
