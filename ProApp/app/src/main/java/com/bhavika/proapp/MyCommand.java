package com.bhavika.proapp;

import android.content.Context;

import com.android.volley.Request;

import java.util.ArrayList;

/**
 * Created by bhavika on 24-11-2016.
 */
public class MyCommand<T> {

    private Context context;
    ArrayList<Request<T>> requestList = new ArrayList<>();

    public MyCommand(Context context){
        this.context=context;
    }

    public  void add(Request<T> request){
        requestList.add(request);
    }

    public  void remove(Request<T> request){
        requestList.remove(request);
    }

    public void execute(){
        for (Request<T> request : requestList){
            MySingleton.getInstance(context).addToRequestQueue(request);
        }
    }
}
