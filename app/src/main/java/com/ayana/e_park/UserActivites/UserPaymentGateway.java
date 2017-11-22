package com.ayana.e_park.UserActivites;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ayana.e_park.Model.BookingDetail;
import com.ayana.e_park.Model.UserData;
import com.ayana.e_park.R;

import java.util.ArrayList;
import java.util.List;

public class UserPaymentGateway extends AppCompatActivity {


    private TextView parkingNumber;
    private BookingDetail bookingDetail;
    private ImageView typeImage;
    private TextView carRegnoTextView;
    private TextView fromTimeTextView;
    private TextView addressTextView;
    private TextView nameTextView;
    private Button payButton;
    private Boolean paidStatus = false;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private DatabaseReference mUserRef;
    private String NODE = null;
    private String USER_NODE = null;
    private MaterialDialog dialog;
    private List<BookingDetail> bookingDetailList =  new ArrayList<>();
    private String parkingNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment_gateway);

//        bookingDetailList = UserData.bookingDetailList;
//        bookingDetail = UserData.bookingDetail;
//       // bookingDetail.setUserDetail(UserData.userDetail);
//        parkingNumber = findViewById(R.id.parking_number_text_view);
//        fromTimeTextView = findViewById(R.id.from_time_text_view);
//        addressTextView = findViewById(R.id.address_text_view);
//        nameTextView = findViewById(R.id.name_text_view);
//        carRegnoTextView = findViewById(R.id.car_reg_text_view);
//        typeImage = findViewById(R.id.type_image_view);
//        payButton = findViewById(R.id.pay_button);
//
//
//        setUI();
//
//        carRegnoTextView.setText(bookingDetail.getRegistrationNo());

//        mDatabase = FirebaseDatabase.getInstance();
//        NODE = getResources().getString(R.string.firebase_databse_node_booking);
//        USER_NODE = getResources().getString(R.string.firebase_databse_node_user_booking);
//        mRef = mDatabase.getReference(NODE);
//        mUserRef = mDatabase.getReference(USER_NODE);
    }


    private void setUI() {
        if (bookingDetail.getType().equals("bike")) {
            typeImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_black));
        } else if (bookingDetail.getType().equals("car")) {
            typeImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_black));
        } else {
            typeImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_black));
        }
        payButton.setText("Pay");
        nameTextView.setText(UserData.userDetail.getName());
        addressTextView.setText("ASD");
        fromTimeTextView.setText(bookingDetail.getFromTime());
    }


    public void pay(View view) {

        if(!paidStatus) {
            final int GET_NEW_CARD = 2;
            Intent intent = new Intent(UserPaymentGateway.this, CardEditActivity.class);
            startActivityForResult(intent, GET_NEW_CARD);
        }


    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            String cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            // Your processing goes here.
            makeToast("Payment Complete");

            payButton.setText("Done");

            paidStatus = true;

        }
    }

    private void makeToast(String message) {
        Toast.makeText(UserPaymentGateway.this, message, Toast.LENGTH_SHORT).show();
    }


}
