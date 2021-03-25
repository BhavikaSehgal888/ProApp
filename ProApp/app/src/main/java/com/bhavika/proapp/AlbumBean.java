package com.bhavika.proapp;

import java.io.Serializable;

/**
 * Created by bhavika on 08-12-2016.
 */
public class AlbumBean  implements Serializable{
    int alb_id;
    String alb_name;

    public AlbumBean(){

    }

    public AlbumBean(int alb_id, String albName) {
        this.alb_id = alb_id;
        this.alb_name = albName;
    }

    public int getAlb_id() {
        return alb_id;
    }

    public void setAlb_id(int alb_id) {
        this.alb_id = alb_id;
    }

    public String getAlbName() {
        return alb_name;
    }

    public void setAlbName(String albName) {
        this.alb_name = albName;
    }

    @Override
    public String toString() {
        return "AlbumBean{" +
                "alb_id=" + alb_id +
                ", albName='" + alb_name + '\'' +
                '}';
    }
}
