package com.sehalsein.e_park.AdminActivity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sehalsein.e_park.Model.BookingDetail;
import com.sehalsein.e_park.Model.ParkingAreaDetail;
import com.sehalsein.e_park.Model.UserData;
import com.sehalsein.e_park.R;
import com.sehalsein.e_park.UserActivites.BookParkingActivity;
import com.sehalsein.e_park.UserActivites.BookingValidation;

public class AdminNewBookingActivity extends AppCompatActivity {


    private ImageView bikeImageView;
    private ImageView carImageView;
    private ImageView busImageView;
    private EditText vehicleRegNoEditText;
    private EditText nameEditText;
    private EditText emailIdEdotText;
    private EditText mobileNoEditText;

    private ParkingAreaDetail detail;
    private String bookinType = null;
    private BookingDetail bookingDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_booking);

        detail = UserData.parkingAreaDetail;
        bikeImageView = findViewById(R.id.bike_type_image);
        carImageView = findViewById(R.id.car_type_image);
        busImageView = findViewById(R.id.bus_type_image);
        vehicleRegNoEditText = findViewById(R.id.vehicle_reg_no_edit_text);
        nameEditText = findViewById(R.id.name_edit_text);
        emailIdEdotText = findViewById(R.id.email_id_edit_text);
        mobileNoEditText = findViewById(R.id.mobile_no_edit_text);
        bikeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBike();
            }
        });

        carImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCar();
            }
        });

        busImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBus();
            }
        });


    }

    private void selectBike() {
        bikeImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_black));
        carImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_grey));
        busImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_grey));
        bookinType = "bike";
      //  baseFareTextView.setText(detail.getBikeDetail().getBaseFare());
    //    addFareTextView.setText(detail.getBikeDetail().getAdditionalFare());
    }

    private void selectCar() {
        bikeImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_grey));
        carImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_black));
        busImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_grey));
        bookinType = "car";
  //      baseFareTextView.setText(detail.getCarDetail().getBaseFare());
//        addFareTextView.setText(detail.getCarDetail().getAdditionalFare());
    }

    private void selectBus() {
        bikeImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_grey));
        carImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_grey));
        busImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_black));
        bookinType = "bus";
       // baseFareTextView.setText(detail.getBusDetail().getBaseFare());
        //addFareTextView.setText(detail.getBusDetail().getAdditionalFare());
    }

    public void bookSlot(View view) {

        if(validate()){
          //  startActivity(new Intent(BookParkingActivity.this,BookingValidation.class));
            UserData.bookingDetail = bookingDetail;
        }else{
            makeToast("INComplete!!");
        }

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void makeToast(String message) {
        //Toast.makeText(BookParkingActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    private boolean validate() {
        boolean registrationCheck;
        boolean nameCheck;
        boolean emailCheck;
        boolean mobileChecl;
        boolean typeCheck;
        String registration = "";
        String name = "";
        String email = "";
        String mobile = "";

        if (isEmpty(vehicleRegNoEditText)) {
            vehicleRegNoEditText.setError("Enter Vehicle Registration No");
            registrationCheck = false;
        } else {
            registrationCheck = true;
            registration = vehicleRegNoEditText.getText().toString();
        }

        if (bookinType == null) {
            makeToast("Please select the type of vehicle");
            typeCheck = false;
        } else {
            typeCheck = true;
        }

        if (registrationCheck && typeCheck ) {
            bookingDetail = new BookingDetail();
            bookingDetail.setParkingId(detail.getId());
            bookingDetail.setType(bookinType);
            bookingDetail.setRegistrationNo(registration);
            return true;
        } else {
            return false;
        }
    }
}
