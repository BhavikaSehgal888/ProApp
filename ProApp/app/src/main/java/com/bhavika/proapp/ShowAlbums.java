package com.bhavika.proapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class ShowAlbums extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.listView)
    ListView listView;

    AlbumNameAdapter adapter;
    int id=0;
    ProgressDialog dialog;
    RequestQueue queue;
    StringRequest request;
    ArrayList<AlbumBean> albList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_albums);

        ButterKnife.inject(this);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Fetching");
        dialog.setCancelable(false);

        Intent rcv = getIntent();
        id = rcv.getIntExtra("id",0);


        listView.setOnItemClickListener(this);
        queue= Volley.newRequestQueue(this);

        retrieveAlbName();

    }

    void  retrieveAlbName(){
        dialog.show();

        request = new StringRequest(Request.Method.POST, Util.ALBUM_NAME_URL,
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

                                Type listType = new TypeToken<List<AlbumBean>>(){}.getType();
                                albList = (ArrayList<AlbumBean>)gson.fromJson(jsonArray.toString(),listType);
                                Log.i("List","list"+albList);

                                Toast.makeText(ShowAlbums.this,"Size of list : "+albList.size(),Toast.LENGTH_LONG).show();

                                adapter = new AlbumNameAdapter(ShowAlbums.this,R.layout.list_item_alb_name,albList);
                                listView.setAdapter(adapter);

                            }else{
                                dialog.dismiss();
                                Toast.makeText(ShowAlbums.this,"No Albums Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            dialog.dismiss();
                            Toast.makeText(ShowAlbums.this,"Some JSON parsing error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ShowAlbums.this,"Some Volley Error"+volleyError,Toast.LENGTH_LONG).show();
                        Log.i("RETRIEVE_ALBUM",volleyError.getMessage());
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlbumBean bean = albList.get(i);
        Toast.makeText(this,bean.toString(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ShowAlbums.this,MyImages.class);
        intent.putExtra("alb_id",bean.getAlb_id());
        startActivity(intent);
    }
}
