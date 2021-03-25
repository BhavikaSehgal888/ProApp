package com.bhavika.proapp;

import java.io.Serializable;

/**
 * Created by bhavika on 12-12-2016.
 */
public class ProfilesListBean implements Serializable {

    int id;
    String name,city,profile;

    public ProfilesListBean(){

    }


    public ProfilesListBean(String city, int id, String name, String profile) {
        this.city = city;
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ProfilesListBean{" +
                "city='" + city + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
