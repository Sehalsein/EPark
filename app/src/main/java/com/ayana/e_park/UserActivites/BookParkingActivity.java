package com.ayana.e_park.UserActivites;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ayana.e_park.Model.BookingDetail;
import com.ayana.e_park.Model.ParkingAreaDetail;
import com.ayana.e_park.Model.UserData;
import com.ayana.e_park.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookParkingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private ImageView bikeImageView;
    private ImageView carImageView;
    private ImageView busImageView;
    private EditText vehicleRegNoEditText;
    private TextView remainingCapacityTextView;
    private TextView baseFareTextView;
    private TextView addFareTextView;
    private ParkingAreaDetail detail;
    private EditText fromTimeEditText;
    private String bookinType = null;
    private BookingDetail bookingDetail;
    static final int COMPLETE_BOOKING_REQUEST = 1;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private DatabaseReference mUserRef;
    private String NODE = null;
    private String USER_NODE = null;
    private MaterialDialog dialog;
    private List<BookingDetail> bookingDetailList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_parking);

        mDatabase = FirebaseDatabase.getInstance();
        NODE = getResources().getString(R.string.firebase_databse_node_booking);
        USER_NODE = getResources().getString(R.string.firebase_databse_node_user_booking);
        mRef = mDatabase.getReference(NODE);

        detail = UserData.parkingAreaDetail;
        bikeImageView = findViewById(R.id.bike_type_image);
        carImageView = findViewById(R.id.car_type_image);
        busImageView = findViewById(R.id.bus_type_image);
        vehicleRegNoEditText = findViewById(R.id.vehicle_reg_no_edit_text);
        remainingCapacityTextView = findViewById(R.id.remaining_parking_text_view);
        baseFareTextView = findViewById(R.id.base_fare_text_view);
        addFareTextView = findViewById(R.id.additional_fare_text_view);
        fromTimeEditText = findViewById(R.id.from_time_edit_text);

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

        getBookingList();

    }

    private void getBookingList(){
        mRef.child(detail.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                bookingDetailList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    bookingDetailList.add(snapshot.getValue(BookingDetail.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //   Toast.makeText(this, "Something went wrong while searching", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void openFromTime(View view) {
        //fromTimeEditText.setText("1000");

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Calendar rightNow = Calendar.getInstance();
                        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
                        int currentMinute = rightNow.get(Calendar.MINUTE);
                        int currentTime = currentHour*1000 + currentMinute;
                        int selectedTime = hourOfDay*1000 + minute;
                        if(selectedTime>currentTime ) {
                            fromTimeEditText.setText(hourOfDay + ":" + minute);
                        }else {
                            makeToast("Please select a valid time");
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    private void selectBike() {
        bikeImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_black));
        carImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_grey));
        busImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_grey));
        bookinType = "bike";
        baseFareTextView.setText(detail.getBikeDetail().getBaseFare());
        addFareTextView.setText(detail.getBikeDetail().getAdditionalFare());
        remainingCapacityTextView.setText(getRemaining(detail.getBikeDetail().getCapacity(),"bike")+"");
    }

    private int getRemaining(String rem,String type){
        if(bookingDetailList.size() == 0)
            return Integer.parseInt(rem);
        else
            return Integer.parseInt(rem) - getRemainingType(type);
    }

    private int getRemainingType(String type){
        int j=0;
        for(int i = 0; i<bookingDetailList.size();i++ ){
            if(bookingDetailList.get(i).getType().equals(type)){
                j++;
            }
        }

        return j;
    }
    private void selectCar() {
        bikeImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_grey));
        carImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_black));
        busImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_grey));
        bookinType = "car";
        baseFareTextView.setText(detail.getCarDetail().getBaseFare());
        addFareTextView.setText(detail.getCarDetail().getAdditionalFare());
        remainingCapacityTextView.setText(getRemaining(detail.getCarDetail().getCapacity(),"car")+"");
    }

    private void selectBus() {
        bikeImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_grey));
        carImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_grey));
        busImageView.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_black));
        bookinType = "bus";
        baseFareTextView.setText(detail.getBusDetail().getBaseFare());
        addFareTextView.setText(detail.getBusDetail().getAdditionalFare());
        remainingCapacityTextView.setText(getRemaining(detail.getBusDetail().getCapacity(),"bus")+"");
    }

    public void bookSlot(View view) {

        if (validate()) {
            UserData.bookingDetail = bookingDetail;
            UserData.bookingDetailList = bookingDetailList;
            startActivityForResult(new Intent(BookParkingActivity.this, BookingValidation.class), COMPLETE_BOOKING_REQUEST);



        }

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void makeToast(String message) {
        Toast.makeText(BookParkingActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COMPLETE_BOOKING_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                this.finish();
            }

            if (requestCode == RESULT_CANCELED) {
                makeToast("CANCELED");
            }
        }

    }

    private boolean validate() {
        boolean registrationCheck;
        boolean fromTimeCheck;
        boolean typeCheck;
        String registration = "";
        String fromTime = "";

        if (isEmpty(vehicleRegNoEditText)) {
            vehicleRegNoEditText.setError("Enter Vehicle Registration No");
            registrationCheck = false;
        } else {


            if(isValidNoPlate(vehicleRegNoEditText.getText().toString())){
                registrationCheck = true;
                registration = vehicleRegNoEditText.getText().toString();
            }else{
                registrationCheck = false;
                vehicleRegNoEditText.setError("Enter Valid Vehicle Registration No");
            }

        }

        if (isEmpty(fromTimeEditText)) {
            fromTimeEditText.setError("Select a valid time");
            fromTimeCheck = false;
        } else {
            fromTimeCheck = true;
            fromTime = fromTimeEditText.getText().toString();
        }

        if (bookinType == null) {
            makeToast("Please select the type of vehicle");
            typeCheck = false;
        } else {
            typeCheck = true;
        }

        if (registrationCheck && typeCheck && fromTimeCheck) {
            //makeToast(detail.getId());
            bookingDetail = new BookingDetail();
            bookingDetail.setParkingId(detail.getId());
            bookingDetail.setType(bookinType);
            bookingDetail.setRegistrationNo(registration);
            bookingDetail.setFromTime(fromTime);
            bookingDetail.setToTime("00:00");
            return true;
        } else {
            return false;
        }
    }


    private boolean isValidNoPlate(String email) {
        String EMAIL_PATTERN = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}
