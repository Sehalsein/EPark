package com.sehalsein.e_park.UserActivites;

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

public class BookParkingActivity extends AppCompatActivity {

    private ImageView bikeImageView;
    private ImageView carImageView;
    private ImageView busImageView;
    private EditText vehicleRegNoEditText;
    private TextView remainingCapacityTextView;
    private TextView baseFareTextView;
    private TextView addFareTextView;
    private ParkingAreaDetail detail;
    private String bookinType = null;
    private BookingDetail bookingDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_parking);

        detail = UserData.parkingAreaDetail;
        bikeImageView = findViewById(R.id.bike_type_image);
        carImageView = findViewById(R.id.car_type_image);
        busImageView = findViewById(R.id.bus_type_image);
        vehicleRegNoEditText = findViewById(R.id.vehicle_reg_no_edit_text);
        remainingCapacityTextView = findViewById(R.id.remaining_parking_text_view);
        baseFareTextView = findViewById(R.id.base_fare_text_view);
        addFareTextView = findViewById(R.id.additional_fare_text_view);

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
        baseFareTextView.setText(detail.getBikeDetail().getBaseFare());
        addFareTextView.setText(detail.getBikeDetail().getAdditionalFare());
    }

    private void selectCar() {
        bikeImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_grey));
        carImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_black));
        busImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_grey));
        bookinType = "car";
        baseFareTextView.setText(detail.getCarDetail().getBaseFare());
        addFareTextView.setText(detail.getCarDetail().getAdditionalFare());
    }

    private void selectBus() {
        bikeImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_grey));
        carImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_grey));
        busImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_black));
        bookinType = "bus";
        baseFareTextView.setText(detail.getBusDetail().getBaseFare());
        addFareTextView.setText(detail.getBusDetail().getAdditionalFare());
    }

    public void bookSlot(View view) {

        if(validate()){
           startActivity(new Intent(BookParkingActivity.this,BookingValidation.class));
           UserData.bookingDetail = bookingDetail;
        }else{
            makeToast("INComplete!!");
        }

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void makeToast(String message) {
        Toast.makeText(BookParkingActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        boolean registrationCheck;
        boolean typeCheck;
        String registration = "";

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
