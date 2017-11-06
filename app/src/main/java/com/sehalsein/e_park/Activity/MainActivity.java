package com.sehalsein.e_park.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sehalsein.e_park.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void loginPage (View view){
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }
}
