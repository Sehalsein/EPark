package com.sehalsein.e_park.AdminActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sehalsein.e_park.AdminAdapter.AdminBookedListAdapter;
import com.sehalsein.e_park.AdminAdapter.AdminParkingListAdapter;
import com.sehalsein.e_park.AdminFragments.ParkingAreaList;
import com.sehalsein.e_park.Model.BookingDetail;
import com.sehalsein.e_park.Model.ParkingAreaDetail;
import com.sehalsein.e_park.Model.UserData;
import com.sehalsein.e_park.R;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingActivity extends AppCompatActivity implements TextWatcher {

    private RecyclerView recyclerView;
    private String TAG = getClass().getName();

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference memebersReference ;
    private String NODE = null;
    private ParkingAreaDetail parkingAreaDetail;
    private EditText searchBar;
    //private GeometricProgressView geometricProgressView;

    private List<BookingDetail> bookingDetailList =  new ArrayList<>();
    private List<BookingDetail> filteredparkingAreaDetailList =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        searchBar = findViewById(R.id.editTextSearch);
        parkingAreaDetail = UserData.parkingAreaDetail;
        NODE = getResources().getString(R.string.firebase_databse_node_booking);
        memebersReference = mDatabase.getReference(NODE).child(parkingAreaDetail.getId());


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewBookingActivity.this, LinearLayoutManager.VERTICAL, false));

        loadList();
    }

    private void loadList(){

        memebersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                bookingDetailList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    bookingDetailList.add(snapshot.getValue(BookingDetail.class));
                }
                filteredparkingAreaDetailList = bookingDetailList;

                recyclerView.setAdapter(new AdminBookedListAdapter(ViewBookingActivity.this, bookingDetailList,"admin"));
                //geometricProgressView.setVisibility(View.INVISIBLE);
                searchBar.addTextChangedListener(ViewBookingActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //   Toast.makeText(this, "Something went wrong while searching", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.e(TAG, "onTextChanged: "+charSequence);
        searchInList(charSequence.toString().trim().toLowerCase());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void searchInList(String searchName){

        List<BookingDetail> memeberListToReturn =  new ArrayList<>();

        filteredparkingAreaDetailList = bookingDetailList;
        for(BookingDetail memeber: filteredparkingAreaDetailList){
            if(memeber.getRegistrationNo().toLowerCase().contains(searchName)){
                memeberListToReturn.add(memeber);
            }
        }
        //filteredparkingAreaDetailList = memeberListToReturn;
//        if(searchName.equals(" ")){
//            filteredparkingAreaDetailList = parkingAreaDetailList;
//        }
        recyclerView.setAdapter(new AdminBookedListAdapter(ViewBookingActivity.this, memeberListToReturn,"admin"));
    }
}
