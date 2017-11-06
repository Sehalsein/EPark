package com.sehalsein.e_park.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sehalsein.e_park.AdminActivity.AdminHomeActivity;
import com.sehalsein.e_park.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    public void login(View view){
        startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
       // startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
    }
}
