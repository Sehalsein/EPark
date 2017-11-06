package com.sehalsein.e_park.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sehalsein.e_park.AdminActivity.AdminHomeActivity;
import com.sehalsein.e_park.R;
import com.sehalsein.e_park.UserActivites.UserHomeActivity;

public class LoginActivity extends AppCompatActivity {

    private TextView createAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        createAccount = findViewById(R.id.create_account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });

    }


    public void login(View view){
       // startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
        startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
    }
}
