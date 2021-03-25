package com.bhavika.proapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavika on 10-12-2016.
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<PhotoBean> imagesList;
    PhotoBean photoBean;


    public ImagesAdapter(Context mContext, ArrayList<PhotoBean> albumList) {
        this.mContext = mContext;
        imagesList = albumList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }


    @Override
    public ImagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImagesAdapter.MyViewHolder holder, int position) {
        photoBean = imagesList.get(position);

        Picasso.with(mContext)
                .load(photoBean.getPhoto())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop().into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }
}
