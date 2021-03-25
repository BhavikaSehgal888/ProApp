package com.bhavika.proapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Profile extends AppCompatActivity {

    int id;

    @InjectView(R.id.imgViewCover)
    ImageView imageViewCover;

    @InjectView(R.id.imgViewProfile)
    ImageView imageViewProfile;

 //   @InjectView(R.id.textViewName)
 //   TextView txtName;

    @InjectView(R.id.textViewEmail)
    TextView txtEmail;

    @InjectView(R.id.textViewPhone1)
    TextView txtPhone1;

    @InjectView(R.id.textViewPhone2)
    TextView txtPhone2;

    @InjectView(R.id.textViewGender)
    TextView txtGender;

    @InjectView(R.id.textViewCity)
    TextView txtCity;

    @InjectView(R.id.textViewCandidShoots)
    TextView txtCandid;

    @InjectView(R.id.textViewCinemaShoots)
    TextView txtCinema;

    @InjectView(R.id.textViewPreShoots)
    TextView txtPre;

    @InjectView(R.id.textViewStudioShoots)
    TextView txtStudio;

    @InjectView(R.id.textViewExe)
    TextView txtExe;

    @InjectView(R.id.textViewPayTerms)
    TextView txtPay;

    @InjectView(R.id.textViewTravelCost)
    TextView txtCost;

    @InjectView(R.id.btnShow)
    Button btnAlbumsShow;

    RequestQueue queue;
    StringRequest request;
    ArrayList<OtherProfileBean> resultList;
    OtherProfileBean bean;

    CollapsingToolbarLayout collapsingToolbarLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent rcv = getIntent();
        id = rcv.getIntExtra("id",0);

        ButterKnife.inject(this);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dynamicToolbarColor();
        toolbarTextAppernce();

        queue = Volley.newRequestQueue(this);
        retrieveRecords();

        btnAlbumsShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,ShowAlbums.class);
                intent.putExtra("id",bean.getId());
                startActivity(intent);
            }
        });
    }

    private void dynamicToolbarColor() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cover);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }


    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    void retrieveRecords(){
        request = new StringRequest(Request.Method.POST, Util.OTHER_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");

                            if (success==1){
                                Gson gson = new Gson();
                                JSONArray jsonArray = jsonObject.getJSONArray("persons");

                                Type listType = new TypeToken<List<OtherProfileBean>>(){}.getType();
                                resultList= (ArrayList<OtherProfileBean>)gson.fromJson(jsonArray.toString(),listType);
                                Log.i("List","list"+resultList);

                             //   Toast.makeText(Profile.this,"Size of list : "+resultList.size(),Toast.LENGTH_LONG).show();
                             //   Toast.makeText(Profile.this,"List : "+resultList,Toast.LENGTH_LONG).show();

                                bean = resultList.get(0);
                                // txtName.setText("Name : "+bean.getName());
                                collapsingToolbarLayout.setTitle(bean.getName());
                                txtEmail.setText(bean.getEmail());
                                txtPhone1.setText(bean.getPhone1());
                                txtPhone2.setText(bean.getPhone2());
                                txtCity.setText(bean.getCity());
                                txtGender.setText(bean.getGender());
                                txtCandid.setText(bean.getCandidShoots());
                                txtCinema.setText(bean.getCinemaShoots());
                                txtStudio.setText(bean.getStudioShoots());
                                txtPre.setText(bean.getPreShoots());
                                txtExe.setText(bean.getExperience());
                                txtPay.setText(bean.getPayTerms());
                                txtCost.setText(bean.getTravelCost());

                                Picasso.with(Profile.this)
                                        .load(bean.getProfile())
                                        .placeholder(R.drawable.placeholder)
                                        .fit()
                                        .centerCrop().into(imageViewProfile);

                                Picasso.with(Profile.this)
                                        .load(bean.getCover())
                                        .placeholder(R.drawable.placeholder)
                                        .fit()
                                        .centerCrop().into(imageViewCover);

                            }else{
                                Toast.makeText(Profile.this,"No Profiles Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            Toast.makeText(Profile.this,"Some JSON parsing error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Profile.this,"Some Volley Error"+volleyError,Toast.LENGTH_LONG).show();
                        Log.i("RETRIEVE_PROFILES",volleyError.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("id",String.valueOf(id));
                return map;
            }
        };

        queue.add(request);

    }

}
