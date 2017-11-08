package com.sehalsein.e_park.AdminAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.sehalsein.e_park.AdminActivity.AdminEditParkingArea;
import com.sehalsein.e_park.AdminActivity.ViewBookingActivity;
import com.sehalsein.e_park.Model.ParkingAreaDetail;
import com.sehalsein.e_park.Model.UserData;
import com.sehalsein.e_park.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sehalsein on 05/11/17.
 */

public class AdminParkingListAdapter extends RecyclerView.Adapter<AdminParkingListAdapter.SearchViewHolder> {

    private Context context;
    private List<ParkingAreaDetail> parkingAreaDetailList = new ArrayList<>();

    public AdminParkingListAdapter(Context context, List<ParkingAreaDetail> parkingAreaDetailList) {
        this.context = context;
        this.parkingAreaDetailList = parkingAreaDetailList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_parking_area_detail, parent, false);
        SearchViewHolder searchViewHolder = new SearchViewHolder(view);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
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
                BottomSheetMenuDialog dialog = new BottomSheetBuilder(context, R.style.AppTheme_BottomSheetDialog)
                        .expandOnStart(true)
                        .setMode(BottomSheetBuilder.MODE_LIST)
                        .setMenu(R.menu.parking_area_option)
                        .setItemClickListener(new BottomSheetItemClickListener() {
                            @Override
                            public void onBottomSheetItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.edit_area:
                                        makeToast("Edit");
                                        editArea(detail);
                                        break;
                                    case R.id.view_area:
                                        viewBooking(detail);
                                        makeToast("View");
                                        break;
                                    case R.id.delete_area:
                                        makeToast("Delete");
                                        break;
                                }
                            }
                        })
                        .createDialog();
                dialog.show();
            }
        });


    }

    private void viewBooking(ParkingAreaDetail data) {
        Intent intent = new Intent(context, ViewBookingActivity.class);
        UserData.parkingAreaDetail = data;
        context.startActivity(intent);
    }

    private void editArea(ParkingAreaDetail data) {
        Intent intent = new Intent(context, AdminEditParkingArea.class);
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
