package com.chetna.androidphp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvUserName, tvUserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPreferenceManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        tvUserName=(TextView) findViewById(R.id.tvUserName);
        tvUserEmail=(TextView) findViewById(R.id.tvUserEmail);

        tvUserEmail.setText(SharedPreferenceManager.getInstance(this).getUserEmail());
        tvUserName.setText(SharedPreferenceManager.getInstance(this).getUserName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch(item.getItemId()){
          case R.id.menuLogout:
              SharedPreferenceManager.getInstance(this).logout();
              finish();
              startActivity(new Intent(this,MainActivity.class))      ;
              break;
      }
return true;
    }
}