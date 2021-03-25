package com.bhavika.proapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyImages extends AppCompatActivity {


    private RecyclerView recyclerView;

    int alb_id;
    PhotoBean bean;
    ProgressDialog dialog;
    RequestQueue queue;
    StringRequest request;
    ArrayList<PhotoBean> imagesList;
    ImagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_images);

        ButterKnife.inject(this);
        bean = new PhotoBean();

        Intent rcv = getIntent();
        alb_id = rcv.getIntExtra("alb_id",0);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // adapter = new AlbumsAdapter(this, gridProfiles);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dialog=new ProgressDialog(this);
        dialog.setMessage("Fetching");
        dialog.setCancelable(false);

        queue= Volley.newRequestQueue(this);
        retriveImages();
    }



    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }


    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    void retriveImages(){
        dialog.show();

        request = new StringRequest(Request.Method.POST, Util.ALBUM_IMAGES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");

                            if (success==1){
                                Gson gson = new Gson();
                                JSONArray jsonArray = jsonObject.getJSONArray("persons");

                                Type listType = new TypeToken<List<PhotoBean>>(){}.getType();
                                imagesList = (ArrayList<PhotoBean>)gson.fromJson(jsonArray.toString(),listType);
                                Log.i("List","list"+imagesList);

                                Toast.makeText(MyImages.this,"Size of list : "+imagesList.size(),Toast.LENGTH_LONG).show();

                                adapter = new ImagesAdapter(MyImages.this,imagesList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                recyclerView.addOnItemTouchListener(
                                        new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override public void onItemClick(View view, int position) {
                                                // do whatever
                                                PhotoBean bean = imagesList.get(position);
                                                Toast.makeText(MyImages.this,bean.toString(),Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(MyImages.this,FullScreenActivity.class);
                                                intent.putExtra("photo",bean.getPhoto());
                                                startActivity(intent);
                                            }

                                            @Override public void onLongItemClick(View view, int position) {
                                                // do whatever
                                            }
                                        })
                                );


                            }else{
                                dialog.dismiss();
                                Toast.makeText(MyImages.this,"No Images Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            dialog.dismiss();
                            Toast.makeText(MyImages.this,"Some JSON parsing error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MyImages.this,"Some Volley Error"+volleyError,Toast.LENGTH_LONG).show();
                        Log.i("RETRIEVE_IMAGE",volleyError.getMessage());
                    }
                })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("id",String.valueOf(alb_id));
                return map;
            }
        };

        queue.add(request);

    }
    }

