package com.ayana.e_park.UserActivites;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ayana.e_park.R;
import com.ayana.e_park.UserFragments.UserPlaceList;
import com.ayana.e_park.UserFragments.UserSettingsPage;

public class UserHomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private static int bottomTabIndexNo;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(0);
                    bottomTabIndexNo = 0;
                    setupActionBar(getNavTitle(bottomTabIndexNo));
                    return true;
                case R.id.navigation_dashboard:
                    changeFragment(1);
                    bottomTabIndexNo = 1;
                    setupActionBar(getNavTitle(bottomTabIndexNo));
                    return true;
//                case R.id.navigation_notifications:
//                    changeFragment(1);
//                    bottomTabIndexNo = 1;
//                    setupActionBar(getNavTitle(bottomTabIndexNo));
//                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        changeFragment(bottomTabIndexNo);
        setupActionBar(getNavTitle(bottomTabIndexNo));
    }

    private void setupActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    private String getNavTitle(int index){
        switch (index){
            case 0 :
                return "Area List";
            case 1 :
                return "Settings";
            default:
                return "Not Available";
        }
    }

    private void makeToast(String message){
        Toast.makeText(UserHomeActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    private void changeFragment(int position) {

        Fragment newFragment = null;

        if (position == 0) {
            newFragment = new UserPlaceList();
        } else if (position == 1) {
            newFragment = new UserSettingsPage();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, newFragment);
        fragmentTransaction.commit();

    }

}
