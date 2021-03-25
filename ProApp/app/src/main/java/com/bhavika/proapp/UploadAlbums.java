package com.bhavika.proapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.PhotoLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UploadAlbums extends AppCompatActivity {

    @InjectView(R.id.txtLayName)
    TextInputLayout txtLayName;

    @InjectView(R.id.eTxtAlbName)
    EditText eTxtAlbName;

    @InjectView(R.id.btnCreate)
    Button btnCreate;

    @InjectView(R.id.btnDone)
    Button btnDone;

    @InjectView(R.id.imageViewGallery)
    ImageView imageViewGallery;

    @InjectView(R.id.imageViewUpload)
    ImageView imageViewUpload;

    @InjectView(R.id.linearMain)
    LinearLayout linearMain;

    GalleryPhoto galleryPhoto;
    RequestQueue queue;
    int id=0;
    String albName="";
    int alb_id=0;
    AlbumBean albumBean;
    ArrayList<String> imgList;
    final int GALLERY_REQUEST=100;
    StringRequest request;
     MyCommand myCommand;
    Boolean chk = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_albums);
        ButterKnife.inject(this);


        albumBean = new AlbumBean();

        Intent rcv = getIntent();
        id = rcv.getIntExtra("id",0);
  //      Toast.makeText(UploadAlbums.this,"id"+id,Toast.LENGTH_LONG).show();

        queue= Volley.newRequestQueue(this);
        imgList = new ArrayList<>();
        galleryPhoto = new GalleryPhoto(this);
        myCommand = new MyCommand(getApplicationContext());


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId()==R.id.btnCreate){
                    albName=eTxtAlbName.getText().toString().trim();
                    albumBean.setAlbName(albName);
                    validateField();
                    if(chk){
                        createAlbum();
                        imageViewGallery.setVisibility(view.VISIBLE);
                        imageViewUpload.setVisibility(view.VISIBLE);
                    }else {
                        chk=true;
                    }
                }
            }
        });

        imageViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(UploadAlbums.this,"Gallery clicked",Toast.LENGTH_LONG).show();
                Intent intent = galleryPhoto.openGalleryIntent();
                startActivityForResult(intent,GALLERY_REQUEST);
            }
        });


        imageViewUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(UploadAlbums.this,"Upload clicked",Toast.LENGTH_LONG).show();
                for (String imgPath : imgList){
                    try {
                        Bitmap bitmap= PhotoLoader.init().from(imgPath).requestSize(512,512).getBitmap();
                        final String encImage = ImageBase64.encode(bitmap);

                        request = new StringRequest(Request.Method.POST, Util.IMAGES_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                if (s.equals("uploaded_successfully")){
                                    Toast.makeText(UploadAlbums.this,s,Toast.LENGTH_LONG).show();
                                }else {
                                    Log.i("WARNING",s);
                                    Toast.makeText(UploadAlbums.this,s,Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.i("VOLLEY_ERR","Err"+volleyError);
                                Toast.makeText(UploadAlbums.this,"Vollery Error"+volleyError,Toast.LENGTH_LONG).show();
                            }
                        })

                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map map = new HashMap<String, String>();
                                map.put("alb_id",String.valueOf(albumBean.getAlb_id()));
                                map.put("image",encImage);
                                return map;
                            }
                        };

                        request.setRetryPolicy(new DefaultRetryPolicy(
                                120*1000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        myCommand.add(request);

                    } catch (FileNotFoundException e) {
                        Toast.makeText(UploadAlbums.this,"Error while uploading"+e,Toast.LENGTH_LONG).show();
                    }
                }
                myCommand.execute();

            }
        });
    }

    void createAlbum() {
        request = new StringRequest(Request.Method.POST, Util.ALBUM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("RESPONSE_ALB", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            String message = object.getString("message");
                            int success = object.getInt("success");
                            alb_id = object.getInt("alb_id");
                            albumBean.setAlb_id(alb_id);
                            Toast.makeText(UploadAlbums.this, "id" + alb_id, Toast.LENGTH_LONG).show();
                            Toast.makeText(UploadAlbums.this, message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.i("SomeError", "error" + e);
                            Toast.makeText(UploadAlbums.this, "Some Error 1" + e, Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(UploadAlbums.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("id", String.valueOf(id));
                map.put("name", albumBean.getAlbName());
                return map;
            }
        };

        queue.add(request);
    }





    void validateField() {
        if (TextUtils.isEmpty(albName)) {
            txtLayName.setError("Please enter name of album");
            chk=false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==GALLERY_REQUEST){
                galleryPhoto.setPhotoUri(data.getData());
                String photoPath = galleryPhoto.getPath();
                imgList.add(photoPath);
                Log.i("PHOTO_PATH",photoPath);
                try {


                    Bitmap bitmap= PhotoLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                    final ImageView imageView = new ImageView(getApplicationContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.MATRIX);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setPadding(0,0,0,10);
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageBitmap(bitmap);

                    linearMain.addView(imageView);

                } catch (FileNotFoundException e) {
                    Toast.makeText(this,"Error while loading"+e,Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

