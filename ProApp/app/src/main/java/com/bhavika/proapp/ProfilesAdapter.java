package com.bhavika.proapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavika on 11-12-2016.
 */
public class ProfilesAdapter extends ArrayAdapter {

    Context context;
    int res;
    ArrayList<ProfilesListBean> profilesList;
    ProfilesListBean bean;

    public ProfilesAdapter(Context context, int resource, ArrayList<ProfilesListBean> objects) {
        super(context, resource, objects);
        this.context = context;
        res = resource;
        profilesList= objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_profiles,parent,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewCircle);
        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtLocation = (TextView)view.findViewById(R.id.textViewLocation);

        bean = profilesList.get(position);
        Picasso.with(context)
                .load(bean.getProfile())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop().into(imageView);
        txtName.setText(bean.getName());
        txtLocation.setText(bean.getCity());
        return view;
    }
}
