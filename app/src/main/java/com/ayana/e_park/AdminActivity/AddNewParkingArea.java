package com.ayana.e_park.AdminActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ayana.e_park.Model.BikeDetail;
import com.ayana.e_park.Model.BusDetail;
import com.ayana.e_park.Model.CarDetail;
import com.ayana.e_park.Model.ParkingAreaDetail;
import com.ayana.e_park.R;

public class AddNewParkingArea extends AppCompatActivity {

    private EditText parkingNameEditText;
    private EditText areaNameEditText;
    private EditText bikeCapacityEditText;
    private EditText bikeBaseFareEditText;
    private EditText bikeAdditionalFareEditText;
    private EditText carCapacityEditText;
    private EditText carBaseFareEditText;
    private EditText carAdditionalFareEditText;
    private EditText busCapacityEditText;
    private EditText busBaseFareEditText;
    private EditText busAdditionalFareEditText;
    private ParkingAreaDetail parkingAreaDetail;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private String NODE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_parking_area);

        parkingNameEditText = findViewById(R.id.parking_name_edit_text);
        areaNameEditText = findViewById(R.id.area_name_edit_text);
        bikeCapacityEditText = findViewById(R.id.bike_capacity_edit_text);
        bikeBaseFareEditText = findViewById(R.id.bike_price_edit_text);
        bikeAdditionalFareEditText = findViewById(R.id.bike_add_price_edit_text);
        carCapacityEditText = findViewById(R.id.car_capacity_edit_text);
        carBaseFareEditText = findViewById(R.id.car_price_edit_text);
        carAdditionalFareEditText = findViewById(R.id.car_add_price_edit_text);
        busCapacityEditText = findViewById(R.id.bus_capacity_edit_text);
        busBaseFareEditText = findViewById(R.id.bus_price_edit_text);
        busAdditionalFareEditText = findViewById(R.id.bus_add_price_edit_text);

        mDatabase = FirebaseDatabase.getInstance();
        NODE = getResources().getString(R.string.firebase_databse_node_parking);
        mRef = mDatabase.getReference(NODE);
    }


    public void addArea(View view) {
        if (validate()) {
            updateDatabse();
        } else {
            makeToast("Error");
        }
    }

    private void updateDatabse() {
        String key = mRef.push().getKey();
        parkingAreaDetail.setId(key);
        mRef.child(key).setValue(parkingAreaDetail);
        this.finish();
    }
    private void makeToast(String message) {
        Toast.makeText(AddNewParkingArea.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean validate() {
        boolean parkingNameCheck;
        boolean areaNameCheck;
        boolean bikeCapacityCheck;
        boolean bikeBaseCheck;
        boolean bikeAddCheck;
        boolean carCapacityCheck;
        boolean carBaseCheck;
        boolean carAddCheck;
        boolean busCapacityCheck;
        boolean busBaseCheck;
        boolean busAddCheck;

        String parkingName = "";
        String areaName = "";
        String bikeCapacity = "";
        String bikeBase = "";
        String bikeAdd = "";
        String carCapacity = "";
        String carBase = "";
        String carAdd = "";
        String busCapacity = "";
        String busBase = "";
        String busAdd = "";


        if (isEmpty(parkingNameEditText)) {
            parkingNameEditText.setError("Enter Parking name");
            parkingNameCheck = false;
        } else {
            parkingNameCheck = true;
            parkingName = parkingNameEditText.getText().toString();
        }

        if (isEmpty(areaNameEditText)) {
            areaNameEditText.setError("Enter Area name");
            areaNameCheck = false;
        } else {
            areaNameCheck = true;
            areaName = areaNameEditText.getText().toString();
        }

        if (isEmpty(bikeCapacityEditText)) {
            bikeCapacityEditText.setError("Enter Bike capacity");
            bikeCapacityCheck = false;
        } else {
            bikeCapacityCheck = true;
            bikeCapacity = bikeCapacityEditText.getText().toString();
        }
        if (isEmpty(bikeBaseFareEditText)) {
            bikeBaseFareEditText.setError("Enter Bike Fare");
            bikeBaseCheck = false;
        } else {
            bikeBaseCheck = true;
            bikeBase = bikeBaseFareEditText.getText().toString();
        }
        if (isEmpty(bikeAdditionalFareEditText)) {
            bikeAdditionalFareEditText.setError("Enter Bike Fare");
            bikeAddCheck = false;
        } else {
            bikeAddCheck = true;
            bikeAdd = bikeAdditionalFareEditText.getText().toString();
        }


        if (isEmpty(carCapacityEditText)) {
            carCapacityEditText.setError("Enter car capacity");
            carCapacityCheck = false;
        } else {
            carCapacityCheck = true;
            carCapacity = carCapacityEditText.getText().toString();
        }
        if (isEmpty(carBaseFareEditText)) {
            carBaseFareEditText.setError("Enter car Fare");
            carBaseCheck = false;
        } else {
            carBaseCheck = true;
            carBase = carBaseFareEditText.getText().toString();
        }
        if (isEmpty(carAdditionalFareEditText)) {
            carAdditionalFareEditText.setError("Enter car Fare");
            carAddCheck = false;
        } else {
            carAddCheck = true;
            carAdd = carAdditionalFareEditText.getText().toString();
        }


        if (isEmpty(busCapacityEditText)) {
            busCapacityEditText.setError("Enter bus capacity");
            busCapacityCheck = false;
        } else {
            busCapacityCheck = true;
            busCapacity = busCapacityEditText.getText().toString();
        }
        if (isEmpty(busBaseFareEditText)) {
            busBaseFareEditText.setError("Enter bus Fare");
            busBaseCheck = false;
        } else {
            busBaseCheck = true;
            busBase = busBaseFareEditText.getText().toString();
        }
        if (isEmpty(busAdditionalFareEditText)) {
            busAdditionalFareEditText.setError("Enter bus Fare");
            busAddCheck = false;
        } else {
            busAddCheck = true;
            busAdd = busAdditionalFareEditText.getText().toString();
        }

        if (parkingNameCheck && areaNameCheck &&
                bikeCapacityCheck && bikeBaseCheck && bikeAddCheck &&
                carCapacityCheck && carBaseCheck && carAddCheck &&
                busCapacityCheck && busBaseCheck && busAddCheck) {

            BikeDetail bikeDetail = new BikeDetail(bikeCapacity, bikeBase, bikeAdd);
            CarDetail carDetail = new CarDetail(carCapacity, carBase, carAdd);
            BusDetail busDetail = new BusDetail(busCapacity, busBase, busAdd);

            parkingAreaDetail = new ParkingAreaDetail(parkingName, areaName, bikeDetail, carDetail, busDetail);

            return true;
        } else {
            return false;
        }
    }
}
