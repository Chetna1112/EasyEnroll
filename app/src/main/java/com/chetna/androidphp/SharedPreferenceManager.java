package com.chetna.androidphp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPreferenceManager {
    private static SharedPreferenceManager instance;
    private static Context ctx;
    private static final String SHARED_PREF_NAME="sharedpref";
    private static final String KEY_USERNAME ="username";
    private static final String KEY_USER_EMAIL="email";
    private static final String KEY_USER_ID="id";

    private SharedPreferenceManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }
    public boolean userLogin(int id, String username, String email){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USERNAME,username);
        editor.apply();
        return true;
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //if user is available it will return its username otherwise null
        if(sharedPreferences.getString(KEY_USERNAME,null)!=null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
        public String getUserName(){
            SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USERNAME,null);
        }

        public String getUserEmail(){
            SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }
}
