package com.bhavika.proapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    @InjectView(R.id.txtLayName)
    TextInputLayout txtLayName;
    @InjectView(R.id.txtLayEmail)
    TextInputLayout txtLayEmail;
    @InjectView(R.id.txtLayPhone1)
    TextInputLayout txtLayPhn1;
    @InjectView(R.id.txtLayPhone2)
    TextInputLayout txtLayPhn2;
    @InjectView(R.id.txtLayCity)
    TextInputLayout txtLayCity;
    @InjectView(R.id.txtLayCandid)
    TextInputLayout txtLayCandid;
    @InjectView(R.id.txtLayCinema)
    TextInputLayout txtLayCinema;
    @InjectView(R.id.txtLayStudio)
    TextInputLayout txtLayStudio;
    @InjectView(R.id.txtLayPre)
    TextInputLayout txtLayPre;
    @InjectView(R.id.txtLayExp)
    TextInputLayout txtLayExp;
    @InjectView(R.id.txtLayTerms)
    TextInputLayout txtLayTerms;
    @InjectView(R.id.txtLayCost)
    TextInputLayout txtLayCost;
    @InjectView(R.id.txtLayUser)
    TextInputLayout txtLayUser;
    @InjectView(R.id.txtLayPass)
    TextInputLayout txtLayPass;


    @InjectView(R.id.eTxtName)
    EditText eTxtName;
    @InjectView(R.id.eTxtEmail)
    EditText eTxtEmail;
    @InjectView(R.id.eTxtPhone1)
    EditText eTxtPhn1;
    @InjectView(R.id.eTxtPhone2)
    EditText eTxtPhn2;
    @InjectView(R.id.autoTxtView)
    AutoCompleteTextView autoTxtViewCity;
    @InjectView(R.id.eTxtCandid)
    EditText eTxtCandid;
    @InjectView(R.id.eTxtCinema)
    EditText eTxtCinema;
    @InjectView(R.id.eTxtStudio)
    EditText eTxtStudio;
    @InjectView(R.id.eTxtPre)
    EditText eTxtPre;
    @InjectView(R.id.eTxtExp)
    EditText eTxtExp;
    @InjectView(R.id.eTxtTerms)
    EditText eTxtTerms;
    @InjectView(R.id.eTxtCost)
    EditText eTxtCost;
    @InjectView(R.id.eTxtUser)
    EditText eTxtUser;
    @InjectView(R.id.eTxtPass)
    EditText eTxtPass;

    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(R.id.radioButtonMale)
    RadioButton rBtnMale;
    @InjectView(R.id.radioButtonFemale)
    RadioButton rBtnFemale;

    @InjectView(R.id.btnRegister)
    Button btnRegister;

    boolean chk = true;
    ProfileBean bean;
    ArrayAdapter<String> adapter;
    RequestQueue requestQueue;
    StringRequest request;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        bean=new ProfileBean();
        requestQueue= Volley.newRequestQueue(this);

        String[] cities= getResources().getStringArray(R.array.array_cities);
        adapter=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,cities);
        autoTxtViewCity.setAdapter(adapter);
        autoTxtViewCity.setThreshold(1);

        rBtnMale.setOnClickListener(this);
        rBtnFemale.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    void validateFields(){
        if (TextUtils.isEmpty(bean.getName())){
            txtLayName.setError("Please enter your name");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getEmail())){
            txtLayEmail.setError("Please enter your email");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getPhone1())){
            txtLayPhn1.setError("Please enter your contact number1");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getPhone2())){
            txtLayPhn2.setError("Please enter your contact number2");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getGender())){
            Toast.makeText(this,"Please enter your gender",Toast.LENGTH_LONG).show();
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getCity())){
            txtLayCity.setError("Please enter your location");
            chk=false;
        }

        if (TextUtils.isEmpty(bean.getCandidShoots())){
            txtLayCandid.setError("Please enter your range for candid shoots");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getCinemaShoots())){
            txtLayCinema.setError("Please enter your range for cinematography");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getStudioShoots())){
            txtLayStudio.setError("Please enter your range for studio photography");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getPreShoots())){
            txtLayPre.setError("Please enter your range for pre wedding shoots");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getExperience())){
            txtLayExp.setError("Please enter your industry experience");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getPayTerms())){
            txtLayTerms.setError("Please enter your payment terms");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getTravelCost())){
            txtLayCost.setError("Please enter your travel cost");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getUsername())){
            txtLayUser.setError("Please enter your username");
            chk=false;
        }
        if (TextUtils.isEmpty(bean.getPassword())){
            txtLayPass.setError("Please enter your password");
            chk=false;
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.radioButtonMale:
                bean.setGender("Male");
                break;
            case R.id.radioButtonFemale:
                bean.setGender("Female");
                break;
            case R.id.btnRegister:
                bean.setName(eTxtName.getText().toString().trim());
                bean.setEmail(eTxtEmail.getText().toString().trim());
                bean.setPhone1(eTxtPhn1.getText().toString().trim());
                bean.setPhone2(eTxtPhn2.getText().toString().trim());
                bean.setCity(autoTxtViewCity.getText().toString().trim());
                bean.setCandidShoots(eTxtCandid.getText().toString().trim());
                bean.setCinemaShoots(eTxtCinema.getText().toString().trim());
                bean.setStudioShoots(eTxtStudio.getText().toString().trim());
                bean.setPreShoots(eTxtPre.getText().toString().trim());
                bean.setExperience(eTxtExp.getText().toString().trim());
                bean.setPayTerms(eTxtTerms.getText().toString().trim());
                bean.setTravelCost(eTxtCost.getText().toString().trim());
                bean.setUsername(eTxtUser.getText().toString().trim());
                bean.setPassword(eTxtPass.getText().toString().trim());

                validateFields();

                if (chk){
                    if (Util.isNetworkConnected(this)){
                       // Toast.makeText(this,bean.toString(),Toast.LENGTH_LONG).show();
                        insertIntoDB();
                        clearFields();
                    }else{
                     Toast.makeText(this,"Please check your connectivity",Toast.LENGTH_LONG).show();
                    }
                }else {
                    chk=true;
                }

        }
    }


    public void insertIntoDB(){
        request=new StringRequest(Request.Method.POST, Util.INSERT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {

                            JSONObject jsonObject = new JSONObject(s);
                            int success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");
                            if (success==1){
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                                id = jsonObject.getInt("id");
                                bean.setId(id);
                                Intent intent = new Intent(RegisterActivity.this, MyProfile.class);
                                intent.putExtra("profileInfo",bean);
                                startActivity(intent);
                                Log.i("BEAN",bean.toString());
                            }else {
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Log.i("error","Some Err"+e);
                            Toast.makeText(RegisterActivity.this,"Some Error :"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(RegisterActivity.this,volleyError.toString(),Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap();
                map.put("name",bean.getName());
                map.put("email",bean.getEmail());
                map.put("phone1",bean.getPhone1());
                map.put("phone2",bean.getPhone2());
                map.put("gender",bean.getGender());
                map.put("city",bean.getCity());
                map.put("candid",bean.getCandidShoots());
                map.put("cinema",bean.getCinemaShoots());
                map.put("studio",bean.getStudioShoots());
                map.put("pre",bean.getPreShoots());
                map.put("exp",bean.getExperience());
                map.put("pay",bean.getPayTerms());
                map.put("travel",bean.getTravelCost());
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

    void clearFields(){
        eTxtName.setText("");
        eTxtEmail.setText("");
        eTxtPhn1.setText("");
        eTxtPhn2.setText("");
        autoTxtViewCity.setText("");
        eTxtCandid.setText("");
        eTxtCinema.setText("");
        eTxtCost.setText("");
        eTxtPass.setText("");
        eTxtPre.setText("");
        eTxtStudio.setText("");
        eTxtUser.setText("");
        eTxtExp.setText("");
        eTxtTerms.setText("");
        radioGroup.clearCheck();
    }
}
