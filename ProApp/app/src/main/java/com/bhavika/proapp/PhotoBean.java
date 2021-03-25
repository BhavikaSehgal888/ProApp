package com.bhavika.proapp;

import java.io.Serializable;

/**
 * Created by bhavika on 10-12-2016.
 */
public class PhotoBean implements Serializable {

    String photo;


    public PhotoBean(){

    }

    public PhotoBean( String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "PhotoBean{" +
                ", photo='" + photo + '\'' +
                '}';
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
