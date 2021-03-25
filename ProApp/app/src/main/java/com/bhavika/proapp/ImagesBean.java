package com.bhavika.proapp;

import java.io.Serializable;

/**
 * Created by bhavika on 11-12-2016.
 */
public class ImagesBean implements Serializable {

    int id;
    String profile,cover;


    @Override
    public String toString() {
        return "ImagesBean{" +
                "cover='" + cover + '\'' +
                ", id=" + id +
                ", profile='" + profile + '\'' +
                '}';
    }

    public ImagesBean(){

    }

    public ImagesBean(String cover, int id, String profile) {
        this.cover = cover;
        this.id = id;
        this.profile = profile;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
