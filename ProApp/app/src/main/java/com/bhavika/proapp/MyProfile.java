package com.bhavika.proapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyProfile extends AppCompatActivity {

    ProfileBean bean;

    ImageView imageViewCover;
    ImageView imageViewProfile;
    TextView txtName;
    TextView txtEmail;
    TextView txtPhone1;
    TextView txtPhone2;
    TextView txtGender;
    TextView txtCity;
    TextView txtCandid;
    TextView txtCinema;
    TextView txtPre;
    TextView txtStudio;
    TextView txtExe;
    TextView txtPay;
    TextView txtCost;
    RelativeLayout layout;
    Button btnAlbumsUpload;
    Button btnAlbumsShow;

    final int PROFILE_REQUEST = 100;
    final int COVER_REQUEST = 101;
    final int ALBUM_REQUEST = 102;
    GalleryPhoto galleryPhoto;
    String photoPath;
    String selectedPhoto;
    Snackbar snackbar;
    RequestQueue requestQueue;
    StringRequest request;
    String encodedImage;
    String encodedImageProfile;
    String encodedImageCover;
    boolean chk = false;
    ArrayList<String> imgList;
    AlbumBean albumBean;
    ImagesBean imagesBean;
    ArrayList<ImagesBean> profileList;
    CollapsingToolbarLayout collapsingToolbarLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_new_layout);


        initViews();

        albumBean = new AlbumBean();
        Intent rcv = getIntent();
        bean = (ProfileBean) rcv.getSerializableExtra("profileInfo");
//        Log.i("ProfileBean",bean.toString());
        // Toast.makeText(this,bean.toString(),Toast.LENGTH_LONG).show();
        // Toast.makeText(this,bean.getName(),Toast.LENGTH_LONG).show();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(bean.getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dynamicToolbarColor();
        toolbarTextAppernce();

//        txtName.setText("Name : " + bean.getName());
        txtEmail.setText(bean.getEmail());
        txtPhone1.setText(bean.getPhone1());

        txtPhone2.setText(bean.getPhone2());
        txtCity.setText(bean.getCity());
        txtGender.setText( bean.getGender());
        txtCandid.setText( bean.getCandidShoots());
        txtCinema.setText( bean.getCinemaShoots());
        txtStudio.setText(bean.getStudioShoots());
        txtPre.setText( bean.getPreShoots());
        txtExe.setText( bean.getExperience());
        txtPay.setText( bean.getPayTerms());
        txtCost.setText( bean.getTravelCost());

        galleryPhoto = new GalleryPhoto(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);
        imgList = new ArrayList<>();

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = galleryPhoto.openGalleryIntent();
                startActivityForResult(intent, PROFILE_REQUEST);
            }
        });

        imageViewCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = galleryPhoto.openGalleryIntent();
                startActivityForResult(intent, COVER_REQUEST);
            }
        });


        btnAlbumsUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, UploadAlbums.class);
                intent.putExtra("id", bean.getId());
                //   Toast.makeText(MyProfile.this,"id"+bean.getId(),Toast.LENGTH_LONG).show();
                startActivityForResult(intent, ALBUM_REQUEST);
            }
        });

        btnAlbumsShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, ShowAlbums.class);
                intent.putExtra("id", bean.getId());
                startActivity(intent);
            }
        });

        retrieveImages();
    }


    void initViews() {

        imageViewCover = (ImageView) findViewById(R.id.imageViewCover);
        imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
//        txtName = (TextView) findViewById(R.id.textViewName);
        txtEmail = (TextView) findViewById(R.id.textViewEmail);
        txtPhone1 = (TextView) findViewById(R.id.textViewPhone1);
        txtPhone2 = (TextView) findViewById(R.id.textViewPhone2);
        txtGender = (TextView) findViewById(R.id.textViewGender);
        txtCity = (TextView) findViewById(R.id.textViewCity);
        txtCandid = (TextView) findViewById(R.id.textViewCandidShoots);
        txtCinema = (TextView) findViewById(R.id.textViewCinemaShoots);
        txtPre = (TextView) findViewById(R.id.textViewPreShoots);
        txtStudio = (TextView) findViewById(R.id.textViewStudioShoots);
        txtExe = (TextView) findViewById(R.id.textViewExe);
        txtPay = (TextView) findViewById(R.id.textViewPayTerms);
        txtCost = (TextView) findViewById(R.id.textViewTravelCost);
        layout = (RelativeLayout) findViewById(R.id.layout);
        btnAlbumsUpload = (Button) findViewById(R.id.uploadAlbums);
        btnAlbumsShow = (Button) findViewById(R.id.btnShow);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PROFILE_REQUEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                imgList.add(selectedPhoto);
                Bitmap bitmap = null;
                try {
                    bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    imageViewProfile.setImageBitmap(bitmap);
                    try {
                        bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512, 512).getBitmap();
                        encodedImageProfile = ImageBase64.encode(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    Log.i("ProfileTag", "Err" + e);
                    Toast.makeText(this, "ERR regarding profile Picture" + e, Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == COVER_REQUEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                imgList.add(selectedPhoto);
                Bitmap bitmap = null;
                try {
                    bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    imageViewCover.setImageBitmap(bitmap);
                    try {
                        bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512, 512).getBitmap();
                        encodedImageCover = ImageBase64.encode(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    Log.i("ProfileTag", "Err" + e);
                    Toast.makeText(this, "ERR regarding profile Picture" + e, Toast.LENGTH_LONG).show();
                }

                Snackbar snackbar = Snackbar.make(layout, "Do u want 2 upload ?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                uploadProfileImage();
                            }
                        });
                snackbar.show();
            }
        }
    }


    void uploadProfileImage() {
        request = new StringRequest(Request.Method.POST, Util.PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("cover_uploaded_successfully")) {
                    Log.i("Response", s);
                    Toast.makeText(MyProfile.this, s, Toast.LENGTH_LONG).show();
                } else {
                    Log.i("Response", s);
                    Toast.makeText(MyProfile.this, s, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("Err", "Error" + volleyError);
                Toast.makeText(MyProfile.this, "Volley Error" + volleyError, Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            protected Map getParams() throws AuthFailureError {
                Map map = new HashMap<>();
                map.put("id", String.valueOf(bean.getId()));
                map.put("imageProfile", encodedImageProfile);
                map.put("imageCover", encodedImageCover);
                return map;

            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(
                120 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    void retrieveImages() {
        request = new StringRequest(Request.Method.POST, Util.PROFILE_IMAGES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");

                            if (success == 1) {
                                Gson gson = new Gson();
                                JSONArray jsonArray = jsonObject.getJSONArray("persons");

                                Type listType = new TypeToken<List<ImagesBean>>() {
                                }.getType();
                                profileList = (ArrayList<ImagesBean>) gson.fromJson(jsonArray.toString(), listType);
                                Log.i("List", "list" + profileList);

                           //     Toast.makeText(MyProfile.this, "Size of list : " + profileList.size(), Toast.LENGTH_LONG).show();
                                imagesBean = profileList.get(0);

                                if (profileList != null && profileList.size() > 0) {
                                    Log.i("IMAGES_BEAN", imagesBean.toString());
                                    Picasso.with(MyProfile.this)
                                            .load(imagesBean.getProfile())
                                            .placeholder(R.drawable.placeholder)
                                            .fit()
                                            .centerCrop().into(imageViewProfile);

                                    Picasso.with(MyProfile.this)
                                            .load(imagesBean.getCover())
                                            .placeholder(R.drawable.placeholder)
                                            .fit()
                                            .centerCrop().into(imageViewCover);
                                }

                            } else {
                                Toast.makeText(MyProfile.this, "No profile Images Found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(MyProfile.this, "Some JSON parsing error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MyProfile.this, "Some Volley Error" + volleyError, Toast.LENGTH_LONG).show();
                        Log.i("RETRIEVE_PROFILE", volleyError.getMessage());
                    }
                })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("id", String.valueOf(bean.getId()));
                return map;
            }
        };

        requestQueue.add(request);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activities, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.all_profiles:
                Intent intent = new Intent(MyProfile.this, AllProfiles.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
