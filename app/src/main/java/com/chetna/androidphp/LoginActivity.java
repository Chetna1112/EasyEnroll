package com.chetna.androidphp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContextParams;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText editUsername, passwordLogin;
    private MaterialButton loginBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPreferenceManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }
        editUsername=(TextInputEditText)findViewById(R.id.username);
        passwordLogin=(TextInputEditText) findViewById(R.id.passwordlogin);
        loginBtn=(MaterialButton)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

    }


    private void userLogin(){
        final String username= editUsername.getText().toString().trim();
        final String password =passwordLogin.getText().toString().trim();
        progressDialog.show( ) ;
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                    //convert response to json object
                        try {
                            JSONObject obj= new JSONObject(response);
                            //user is successfully authenticated
                            if(!obj.getBoolean("error")){
                                SharedPreferenceManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("username"),
                                                obj.getString("email")
                                        );
                                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                        return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v==loginBtn)
            userLogin();
    }
}