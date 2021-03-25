package com.bhavika.proapp;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class AllProfiles extends AppCompatActivity implements AdapterView.OnItemClickListener{


    @InjectView(R.id.listViewProfiles)
    ListView listView;


    ProfilesAdapter adapter;
    ArrayList<ProfilesListBean> allProfiles;
    RequestQueue queue;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_profiles);

        ButterKnife.inject(this);


        queue= Volley.newRequestQueue(this);

        if (Util.isNetworkConnected(this)){
            // Toast.makeText(this,bean.toString(),Toast.LENGTH_LONG).show();
            retrieveProfiles();
        }else{
            Toast.makeText(this,"Please check your connectivity",Toast.LENGTH_LONG).show();
        }
        listView.setOnItemClickListener(this);

    }

    void retrieveProfiles(){
        request = new StringRequest(Request.Method.POST, Util.ALL_PROFILES_URL,
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

                                Type listType = new TypeToken<List<ProfilesListBean>>(){}.getType();
                                allProfiles = (ArrayList<ProfilesListBean>)gson.fromJson(jsonArray.toString(),listType);
                                Log.i("List","list"+allProfiles);

                                Toast.makeText(AllProfiles.this,"Size of list : "+allProfiles.size(),Toast.LENGTH_LONG).show();

                                adapter = new ProfilesAdapter(AllProfiles.this,R.layout.item_all_profiles,allProfiles);
                                listView.setAdapter(adapter);

                            }else{
                                Toast.makeText(AllProfiles.this,"No Profiles Found",Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            Toast.makeText(AllProfiles.this,"Some JSON parsing error",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(AllProfiles.this,"Some Volley Error"+volleyError,Toast.LENGTH_LONG).show();
                        Log.i("RETRIEVE_PROFILES",volleyError.getMessage());
                    }
                });

        queue.add(request);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ProfilesListBean bean = allProfiles.get(i);
        Toast.makeText(this,bean.toString(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AllProfiles.this,Profile.class);
        intent.putExtra("id",bean.getId());
        startActivity(intent);
    }
}
