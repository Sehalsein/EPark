package com.ayana.e_park.UserActivites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ayana.e_park.AdminAdapter.AdminBookedListAdapter;
import com.ayana.e_park.Model.BookingDetail;
import com.ayana.e_park.Model.ParkingAreaDetail;
import com.ayana.e_park.Model.UserData;
import com.ayana.e_park.Model.UserDetail;
import com.ayana.e_park.R;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;

public class UserPreviousBookingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String TAG = getClass().getName();

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference memebersReference ;
    private String NODE = null;
    private ParkingAreaDetail parkingAreaDetail;
    private GeometricProgressView geometricProgressView;
    private List<BookingDetail> bookingDetailList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_previous_booking);

        UserDetail userDetail = UserData.userDetail;
        parkingAreaDetail = UserData.parkingAreaDetail;
        NODE = getResources().getString(R.string.firebase_databse_node_user_booking);
        memebersReference = mDatabase.getReference(NODE).child(userDetail.getId());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserPreviousBookingActivity.this, LinearLayoutManager.VERTICAL, false));

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

                recyclerView.setAdapter(new AdminBookedListAdapter(UserPreviousBookingActivity.this, bookingDetailList,"user",memebersReference));
                //geometricProgressView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //   Toast.makeText(this, "Something went wrong while searching", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
