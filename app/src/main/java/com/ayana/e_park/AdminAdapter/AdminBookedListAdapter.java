package com.ayana.e_park.AdminAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.ayana.e_park.UserActivites.UserPaymentGateway;
import com.google.firebase.database.DatabaseReference;
import com.ayana.e_park.Miscl.QRTest;
import com.ayana.e_park.Model.BookingDetail;
import com.ayana.e_park.Model.UserData;
import com.ayana.e_park.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sehalsein on 08/11/17.
 */

public class AdminBookedListAdapter extends RecyclerView.Adapter<AdminBookedListAdapter.SearchViewHolder>  {

    private Context context;
    private List<BookingDetail> parkingAreaDetailList = new ArrayList<>();
    private String type;

    private DatabaseReference mRef ;
    private String NODE = null;

    public AdminBookedListAdapter(Context context, List<BookingDetail> parkingAreaDetailList) {
        this.context = context;
        this.parkingAreaDetailList = parkingAreaDetailList;
    }

    public AdminBookedListAdapter(Context context, List<BookingDetail> parkingAreaDetailList, String type, DatabaseReference mRef) {
        this.context = context;
        this.parkingAreaDetailList = parkingAreaDetailList;
        this.type = type;
        this.mRef = mRef;
    }

    public AdminBookedListAdapter(Context context, List<BookingDetail> parkingAreaDetailList, String type) {
        this.context = context;
        this.parkingAreaDetailList = parkingAreaDetailList;
        this.type = type;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_booked_parking, parent, false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        final BookingDetail detail = parkingAreaDetailList.get(position);

        holder.parkingNumber.setText(detail.getParkingNumber());
        holder.fromTime.setText(detail.getFromTime());

        holder.toTime.setText(detail.getToTime());
        if(detail.getToTime().equals("00:00")) {
            holder.toTime.setText("-");
        }
        holder.registrationNo.setText(detail.getRegistrationNo());

        if(detail.isPaidStatus()){
            holder.typeImage.setColorFilter(Color.argb(255, 233, 187, 0)) ;
        }else{
            holder.typeImage.setColorFilter(Color.argb(0, 0, 0, 0));
        }


        if (detail.getType().equals("bike")) {
            if(detail.isPaidStatus()){
                holder.typeImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_bike_gold));
            }else{
                holder.typeImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_bike_black));
            }

        } else if (detail.getType().equals("car")) {
            if(detail.isPaidStatus()){
                holder.typeImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_car_gold));
            }else{
                holder.typeImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_car_black));
            }

        } else {
            if(detail.isPaidStatus()){
                holder.typeImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_bus_gold));
            }else{
                holder.typeImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_bus_black));
            }
        }

        if(type!=null) {
            if (type.equals("admin")) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserData.bookingDetail = detail;
                        userQR();
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        new MaterialDialog.Builder(context)
                                .title("Sure you want to cancel?")
                                .content("We have already blocked the parking slot for you. You will be allotted different slots if you go back " +
                                        "\n\nNote : Previously allotted parking would be available after a while")
                                .positiveText("Dismiss")
                                .negativeText("Cancel")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        mRef.child(detail.getId()).setValue(null);
                                        makeToast("ASD");
                                        return;
                                    }
                                })
                                .stackingBehavior(StackingBehavior.ADAPTIVE)
                                .show();
                        return true;
                    }
                });
            }else {
                //makeToast("hjgj");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userPayment(detail);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        new MaterialDialog.Builder(context)
                                .title("Sure you want to cancel?")
                                .content("We have already blocked the parking slot for you. You will be allotted different slots if you go back " +
                                        "\n\nNote : Previously allotted parking would be available after a while")
                                .positiveText("Dismiss")
                                .negativeText("Cancel")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        mRef.child(detail.getId()).setValue(null);
                                        makeToast("ASD");
                                        return;
                                    }
                                })
                                .stackingBehavior(StackingBehavior.ADAPTIVE)
                                .show();
                        return false;
                    }
                });
            }
        }
    }

    private void userPayment(BookingDetail data){

        UserData.bookingDetail = data;
        // makeToast("jhg");
        context.startActivity(new Intent(context, UserPaymentGateway.class));
    }


    private void userQR(){
        context.startActivity(new Intent(context, QRTest.class));
    }

    @Override
    public int getItemCount() {
        return parkingAreaDetailList.size();
    }

    private void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private TextView parkingNumber, registrationNo, fromTime, toTime;
        private ImageView typeImage;


        public SearchViewHolder(View itemView) {
            super(itemView);

            parkingNumber = itemView.findViewById(R.id.parking_number_text_view);
            registrationNo = itemView.findViewById(R.id.car_reg_text_view);
            fromTime = itemView.findViewById(R.id.from_time_text_view);
            toTime = itemView.findViewById(R.id.to_time_text_view);
            typeImage = itemView.findViewById(R.id.type_image_view);
        }
    }
}
