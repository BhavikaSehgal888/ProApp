package com.bhavika.proapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavika on 08-12-2016.
 */
public class AlbumNameAdapter extends ArrayAdapter {


    Context context;
    int resource;
    ArrayList<AlbumBean> albumList;
    AlbumBean bean;

    public AlbumNameAdapter(Context context, int resource, ArrayList<AlbumBean> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        albumList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        view= LayoutInflater.from(context).inflate(R.layout.list_item_alb_name,parent,false);
        TextView albName = (TextView)view.findViewById(R.id.txtViewAlbName);

        bean=albumList.get(position);
//        Log.i("BEAN",bean.getAlbName());
        albName.setText(bean.getAlbName());

        return view;
    }
}
