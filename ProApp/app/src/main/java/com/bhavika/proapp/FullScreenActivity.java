package com.bhavika.proapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FullScreenActivity extends AppCompatActivity {


    @InjectView(R.id.imgDisplay)
    ImageView imageView;

    @InjectView(R.id.btnClose)
    Button btnClose;

    String photo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        ButterKnife.inject(this);

        Intent rcv = getIntent();
        photo = rcv.getStringExtra("photo");


        Picasso.with(this)
                .load(photo)
                .placeholder(R.drawable.placeholder)
                .into(imageView);


        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FullScreenActivity.this.finish();
            }
        });
    }
}
