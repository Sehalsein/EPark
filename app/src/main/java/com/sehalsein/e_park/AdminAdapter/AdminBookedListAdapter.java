package com.sehalsein.e_park.AdminAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sehalsein.e_park.Miscl.QRTest;
import com.sehalsein.e_park.Model.BookingDetail;
import com.sehalsein.e_park.Model.UserData;
import com.sehalsein.e_park.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sehalsein on 08/11/17.
 */

public class AdminBookedListAdapter extends RecyclerView.Adapter<AdminBookedListAdapter.SearchViewHolder>  {

    private Context context;
    private List<BookingDetail> parkingAreaDetailList = new ArrayList<>();
    private String type;

    public AdminBookedListAdapter(Context context, List<BookingDetail> parkingAreaDetailList) {
        this.context = context;
        this.parkingAreaDetailList = parkingAreaDetailList;
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
        holder.toTime.setText("-");
        holder.registrationNo.setText(detail.getRegistrationNo());

        if (detail.getType().equals("bike")) {
            holder.typeImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_bike_black));
        } else if (detail.getType().equals("car")) {
            holder.typeImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_car_black));
        } else {
            holder.typeImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_bus_black));
        }

        if(type.equals("admin")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserData.bookingDetail = detail;
                    userQR();
                }
            });
        }
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
