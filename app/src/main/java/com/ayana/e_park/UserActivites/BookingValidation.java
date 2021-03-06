package com.ayana.e_park.UserActivites;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ayana.e_park.Model.BookingDetail;
import com.ayana.e_park.Model.UserData;
import com.ayana.e_park.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookingValidation extends AppCompatActivity {

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
        setContentView(R.layout.activity_booking_validation);

        //makeToast("VALID");
        dialog = new MaterialDialog.Builder(BookingValidation.this)
                .title("Please wait")
                .content("Generating parking number..")
                .progress(true, 0)
                .cancelable(false)
                .show();
        bookingDetailList = UserData.bookingDetailList;
        //makeToast(bookingDetailList.size() + "");
        bookingDetail = UserData.bookingDetail;
        bookingDetail.setUserDetail(UserData.userDetail);
        parkingNumber = findViewById(R.id.parking_number_text_view);
        fromTimeTextView = findViewById(R.id.from_time_text_view);
        addressTextView = findViewById(R.id.address_text_view);
        nameTextView = findViewById(R.id.name_text_view);
        carRegnoTextView = findViewById(R.id.car_reg_text_view);
        typeImage = findViewById(R.id.type_image_view);
        payButton = findViewById(R.id.pay_button);


        setUI();


        parkingNo = generateParkingNumber();
        isValidParkingNumber(parkingNo);
            dialog.dismiss();

        parkingNumber.setText(parkingNo);
        bookingDetail.setParkingNumber(parkingNo);
        carRegnoTextView.setText(bookingDetail.getRegistrationNo());

        mDatabase = FirebaseDatabase.getInstance();
        NODE = getResources().getString(R.string.firebase_databse_node_booking);
        USER_NODE = getResources().getString(R.string.firebase_databse_node_user_booking);
        mRef = mDatabase.getReference(NODE);
        mUserRef = mDatabase.getReference(USER_NODE);
    }

    private String generateParkingNumber(){
        return generateLetter(1, bookingDetail.getType()) + "" + generateNumber(2);
    }

    private void isValidParkingNumber(String parkingNo) {

        for(int i=0; i<bookingDetailList.size(); i++){
            //makeToast(bookingDetailList.get(i).getParkingNumber() + " " + parkingNo);
            if(bookingDetailList.get(i).getParkingNumber().equals(parkingNo)){
                //makeToast(bookingDetailList.get(i).getParkingNumber() + " " + parkingNo);
                isValidParkingNumber(generateParkingNumber());
                return;
            }
        }
        this.parkingNo = parkingNo;
        //makeToast(parkingNo);

    }

    private void setUI() {
        if (bookingDetail.getType().equals("bike")) {
            typeImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bike_black));
        } else if (bookingDetail.getType().equals("car")) {
            typeImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_car_black));
        } else {
            typeImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_bus_black));
        }
        payButton.setText("Book");
        nameTextView.setText(UserData.userDetail.getName());
        addressTextView.setText("ASD");
        fromTimeTextView.setText(bookingDetail.getFromTime());
    }

    public void pay(View view) {

//        if(!paidStatus) {
//            final int GET_NEW_CARD = 2;
//
//            Intent intent = new Intent(BookingValidation.this, CardEditActivity.class);
//            startActivityForResult(intent, GET_NEW_CARD);
//        }else{

        final MaterialDialog dialog = new MaterialDialog.Builder(BookingValidation.this)
                .title("Booking!!")
                .content("Please wait")
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(true)
                .show();
        String key = mRef.push().getKey();
        bookingDetail.setId(key);
        mUserRef.child(UserData.userDetail.getId()).child(key).setValue(bookingDetail);
        mRef.child(bookingDetail.getParkingId()).child(key).setValue(bookingDetail, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                dialog.dismiss();
                if (databaseError != null) {
                    //makeToast(databaseError.getMessage());
                    new MaterialDialog.Builder(BookingValidation.this)
                            .title("Unsuccessful")
                            .content("Booking failed.")
                            .positiveText("Dismiss")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    goback();
                                }
                            })
                            .show();
                } else {

                    BackgroundMail.newBuilder(BookingValidation.this)
                            .withUsername("username@gmail.com")
                            .withPassword("password12345")
                            .withMailto("toemail@gmail.com")
                            .withType(BackgroundMail.TYPE_PLAIN)
                            .withSubject("Booking Confirmation")
                            .withBody("this email is regrading your booking od parking slot "+parkingNo+".")
                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                @Override
                                public void onSuccess() {
                                    //do some magic
                                }
                            })
                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                @Override
                                public void onFail() {
                                    //do some magic
                                }
                            })
                            .send();


                    new MaterialDialog.Builder(BookingValidation.this)
                            .title("Successful")
                            .content("Booking complete")
                            .positiveText("Dismiss")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    goback();
                                }
                            })
                            .show();
                }
            }
        });

        // }
    }

    private void goback() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
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
        Toast.makeText(BookingValidation.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        new MaterialDialog.Builder(this)
                .title("Sure you want to go back?")
                .content("We have already blocked the parking slot for you. You will be allotted different seats if you go back " +
                        "\n\nNote : Previously allotted parking would be available after a while")
                .positiveText("Dismiss")
                .negativeText("Go back")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onBackPressed();
                        return;
                    }
                })
                .stackingBehavior(StackingBehavior.ADAPTIVE)
                .show();

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {


            //Toast.makeText(getBaseContext(), "You will loose this slot if you go back\nTap back button  twice in order to go back", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }

    static String generateLetter(int argPasswordLength, String type) {
        String password = "";
        Random random = new Random();
        String full = "ABCDEFGHIJKLMNOPQRESTUVWXYZ";
        if (type.equals("bike")) {
            full = "ABCD";
        } else if (type.equals("car")) {
            full = "EFGHIJKLMNOPQREST";
        } else {
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
