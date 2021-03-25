package com.bhavika.proapp;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavika on 26-12-2016.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<ProfilesListBean> gridList;
    ProfilesListBean bean;

    public AlbumsAdapter(Context mContext, ArrayList<ProfilesListBean> albumList) {
        this.mContext = mContext;
        gridList = albumList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, count;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        bean = gridList.get(position);
        holder.title.setText(bean.getName());
        holder.count.setText(bean.getCity());

        // loading album cover using Glide library
        Glide.with(mContext).load(bean.getProfile()).into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return gridList.size();
    }
}
