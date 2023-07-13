package com.chetna.androidphp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editUserName, editPassword, editEmail;
    private Button buttonRegisgter;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPreferenceManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }

        textViewLogin=(TextView) findViewById(R.id.textViewLogin);
        editEmail=(EditText) findViewById(R.id.editEmail);
        editPassword=(EditText) findViewById(R.id.editPassword);
        editUserName=(EditText) findViewById(R.id.editUserName);
        buttonRegisgter=(Button) findViewById(R.id.Registerbtn);
        progressDialog=new ProgressDialog(this);
        buttonRegisgter.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }
    private void registerUser(){
         final String email=editEmail.getText().toString().trim();
         final String username=editUserName.getText().toString().trim();
         final String password=editPassword.getText().toString().trim();
         if(username.isEmpty()||email.isEmpty()||password.isEmpty()){
             Toast.makeText(getApplicationContext(),"Please enter the details",Toast.LENGTH_LONG).show();
         }
         else {


             progressDialog.setMessage("Registering User...");
             progressDialog.show();
             StringRequest stringRequest = new StringRequest(Request.Method.POST,
                     Constants.URL_REGISTER,
                     new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {
                             progressDialog.dismiss();
                             try {
                                 JSONObject jsonObject = new JSONObject(response);
                                 Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                         }
                     },
                     new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                             progressDialog.hide();
                             if (MainActivity.this != null && error != null && error.getMessage() != null) {
                                 Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                             }
                         }
                     }) {
                 @Nullable
                 @Override
                 protected Map<String, String> getParams() throws AuthFailureError {
                     Map<String, String> params = new HashMap<>();
                     params.put("username", username);
                     params.put("email", email);
                     params.put("password", password);
                     return params;
                 }
             };

             RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
         }
    }
    @Override
    public void onClick(View v) {
        if(v==buttonRegisgter){

            registerUser();}
        if(v==textViewLogin)
            startActivity(new Intent(this,LoginActivity.class));
    }
}