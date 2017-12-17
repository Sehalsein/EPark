package com.ayana.e_park.Miscl;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayana.e_park.Model.BookingDetail;
import com.ayana.e_park.Model.ParkingAreaDetail;
import com.ayana.e_park.Model.UserData;
import com.ayana.e_park.R;

import net.glxn.qrgen.android.QRCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QRTest extends AppCompatActivity {


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    private TextView amountTextView;
    private TextView fromTextView;
    private TextView toTextView;
    private Button btnGenerate, btnReset;
    private ImageView imgResult;
    private BookingDetail bookingDetail;
    private ParkingAreaDetail parkingDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrtest);

        bookingDetail = UserData.bookingDetail;
        parkingDetail = UserData.parkingAreaDetail;

        //txtQRText = (EditText) findViewById(R.id.txtQR);
        amountTextView = (TextView) findViewById(R.id.amount_text_view);
        fromTextView = (TextView) findViewById(R.id.from_time_text_view);
        toTextView = (TextView) findViewById(R.id.to_time_text_view);

        imgResult = findViewById(R.id.imgResult);


        fromTextView.setText(bookingDetail.getFromTime());
        bookingDetail.setToTime(getCurrentTimeStamp());
        toTextView.setText(bookingDetail.getToTime());


        int price = 0;
        try {
            long time = printDifference(simpleDateFormat.parse(bookingDetail.getFromTime()), simpleDateFormat.parse(bookingDetail.getToTime()));
            if( time >= 2){
                price = getBasePrice() + (((int) time)-1) * getAddiPRice();
            }else{
                price = getBasePrice();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        amountTextView.setText("$ " + price);
        Bitmap myBitmap = QRCode.from(parkingDetail.getId()+"EPARK"+bookingDetail.getId()).bitmap();
        imgResult.setImageBitmap(myBitmap);

    }

    private int getBasePrice(){
        if(bookingDetail.getType().equals("bike")){
            return Integer.parseInt(parkingDetail.getBikeDetail().getBaseFare().toString());
        }else if(bookingDetail.getType().equals("car")){
            return Integer.parseInt(parkingDetail.getCarDetail().getBaseFare().toString());
        }else{
            return Integer.parseInt(parkingDetail.getBusDetail().getBaseFare().toString());
        }
    }

    private int getAddiPRice(){
        if(bookingDetail.getType().equals("bike")){
            return Integer.parseInt(parkingDetail.getBikeDetail().getAdditionalFare().toString());
        }else if(bookingDetail.getType().equals("car")){
            return Integer.parseInt(parkingDetail.getCarDetail().getAdditionalFare().toString());
        }else{
            return Integer.parseInt(parkingDetail.getBusDetail().getAdditionalFare().toString());
        }
    }



    private void makeToast(String message) {
        Toast.makeText(QRTest.this, message, Toast.LENGTH_SHORT).show();
    }

    public static String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public long printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if (elapsedHours > 0) {
            return elapsedHours;
        }  else {
            return 1;
        }

    }
}
