package com.bhavika.proapp;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.txtItLay1)
    TextInputLayout txtLay1;

    @InjectView(R.id.txtItLay2)
    TextInputLayout txtLay2;

    @InjectView(R.id.eTxtUser)
    EditText eTxtUser;

    @InjectView(R.id.eTxtPass)
    EditText eTxtPass;

    @InjectView(R.id.buttonLogin)
    Button btnLogin;

    @InjectView(R.id.textViewSignUp)
    TextView txtSignUp;

    @InjectView(R.id.relativeLayout)
    RelativeLayout layout;

    boolean chk=true;
    ProfileBean bean;
    RequestQueue requestQueue;
    StringRequest request;
    ArrayList<ProfileBean> profileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        bean=new ProfileBean();
        requestQueue= Volley.newRequestQueue(this);

    }

    public void loginHandler(View view) {
        if (view.getId() == R.id.buttonLogin) {
            bean.setUsername(eTxtUser.getText().toString().trim());
            bean.setPassword(eTxtPass.getText().toString().trim());

            if (TextUtils.isEmpty(bean.getUsername())) {
                txtLay1.setError("Please enter username");
                chk = false;
            }
            if (TextUtils.isEmpty(bean.getPassword())) {
                txtLay2.setError("Please enter password");
                chk = false;
            }

            if (chk) {
                if (Util.isNetworkConnected(this)) {
                     insertIntoDB();
                    clearFields();
                } else {
                    Toast.makeText(this,"Please check your connectivity",Toast.LENGTH_LONG).show();
                    chk = true;
                }
            }
        }
    }

    public void signUpHandler(View view){
        if (view.getId()==R.id.textViewSignUp){
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }

    public void insertIntoDB(){
        request=new StringRequest(Request.Method.POST, Util.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                            if (success==1){
                                Log.i("FROMSERVER",s.toString());
                                Gson gson = new Gson();
                                JSONArray jsonArray = jsonObject.getJSONArray("persons");

                                Type listType = new TypeToken<List<ProfileBean>>(){}.getType();
                                profileInfo = (ArrayList<ProfileBean>)gson.fromJson(jsonArray.toString(),listType);

                                ProfileBean pb = profileInfo.get(0);

                                if(profileInfo!=null && profileInfo.size()>0) {
                                    Log.i("BEAN",pb.toString());

                                  //  Toast.makeText(LoginActivity.this, "Size of list : " + profileInfo.size(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MyProfile.class);
                                    intent.putExtra("profileInfo", pb);
                                    startActivity(intent);
                                }else{
                                    Snackbar snackbar = Snackbar.make(layout,"Invalid username or password",Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }else{
                                Snackbar snackbar = Snackbar.make(layout,"Invalid username or password",Snackbar.LENGTH_LONG);
                                snackbar.show();
                           //     Toast.makeText(LoginActivity.this,"not login",Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Log.i("error","Some Err"+e);
                            Toast.makeText(LoginActivity.this,"Some Error :"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginActivity.this,volleyError.toString(),Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap();
                map.put("username",bean.getUsername());
                map.put("password",bean.getPassword());
                return map;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                120*1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    void clearFields() {
        eTxtUser.setText("");
        eTxtPass.setText("");
    }
}
