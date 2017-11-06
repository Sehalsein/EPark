package com.sehalsein.e_park.UserActivites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sehalsein.e_park.Model.BookingDetail;
import com.sehalsein.e_park.Model.UserData;
import com.sehalsein.e_park.R;

import java.util.Random;

public class BookingValidation extends AppCompatActivity {

    private TextView parkingNumber;
    private BookingDetail bookingDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_validation);

        bookingDetail = UserData.bookingDetail;
        parkingNumber = findViewById(R.id.parking_number_text_view);


        parkingNumber.setText(generateLetter(1,bookingDetail.getType())+" "+generateNumber(2));
    }

    public void pay(View view){

    }

    static String generateLetter(int argPasswordLength,String type) {
        String password = "";
        Random random = new Random();
        String full = "ABCDEFGHIJKLMNOPQRESTUVWXYZ";
        if(type.equals("bike")){
             full = "ABCD";
        }else if(type.equals("car")){
            full = "EFGHIJKLMNOPQREST";
        }else{
            full = "UVWXYZ";
        }

        while (password.length() < argPasswordLength) {
            int index = random.nextInt(full.length());
            String buffer = "" + full.charAt(index);
            password += buffer;
        }
        return password;
    }
    static String generateNumber(int argPasswordLength) {
        String password = "";
        Random random = new Random();
        String full = "1234567890";
        while (password.length() < argPasswordLength) {
            int index = random.nextInt(full.length());
            String buffer = "" + full.charAt(index);
            password += buffer;
        }
        return password;
    }

}
