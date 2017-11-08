package com.sehalsein.e_park.UserAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sehalsein.e_park.Model.ParkingAreaDetail;
import com.sehalsein.e_park.Model.UserData;
import com.sehalsein.e_park.R;
import com.sehalsein.e_park.UserActivites.BookParkingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sehalsein on 06/11/17.
 */

public class UserPlaceListAdapter extends RecyclerView.Adapter<UserPlaceListAdapter.SearchViewHolder> {

    private Context context;
    private List<ParkingAreaDetail> parkingAreaDetailList = new ArrayList<>();

    public UserPlaceListAdapter(Context context, List<ParkingAreaDetail> parkingAreaDetailList) {
        this.context = context;
        this.parkingAreaDetailList = parkingAreaDetailList;
    }

    @Override
    public UserPlaceListAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_parking_area_detail, parent, false);
        UserPlaceListAdapter.SearchViewHolder searchViewHolder = new UserPlaceListAdapter.SearchViewHolder(view);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(UserPlaceListAdapter.SearchViewHolder holder, int position) {
        final ParkingAreaDetail detail = parkingAreaDetailList.get(position);

        holder.parkingName.setText(detail.getParkingName());
        holder.areaName.setText(detail.getAreaName());
        holder.bikeFare.setText(detail.getBikeDetail().getBaseFare());
        holder.carFare.setText(detail.getCarDetail().getBaseFare());
        holder.busFare.setText(detail.getBusDetail().getBaseFare());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //makeToast("Clicked : " + detail.getParkingName() );
                bookArea(detail);
            }
        });


    }

    private void bookArea(ParkingAreaDetail data) {
        Intent intent = new Intent(context, BookParkingActivity.class);
        UserData.parkingAreaDetail = data;
        context.startActivity(intent);
    }

    private void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return parkingAreaDetailList.size();
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private TextView parkingName, areaName, bikeFare, carFare, busFare;
        private View itemView;


        public SearchViewHolder(View itemView) {
            super(itemView);
            parkingName = (TextView) itemView.findViewById(R.id.parking_name_text_view);
            areaName = (TextView) itemView.findViewById(R.id.area_name_text_view);
            bikeFare = (TextView) itemView.findViewById(R.id.bike_fare_text_view);
            carFare = (TextView) itemView.findViewById(R.id.car_fare_text_view);
            busFare = (TextView) itemView.findViewById(R.id.bus_fare_text_view);
            this.itemView = itemView;
        }
    }
}
