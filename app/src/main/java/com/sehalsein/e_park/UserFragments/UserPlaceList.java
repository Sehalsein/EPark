package com.sehalsein.e_park.UserFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sehalsein.e_park.AdminActivity.AddNewParkingArea;
import com.sehalsein.e_park.AdminAdapter.AdminParkingListAdapter;
import com.sehalsein.e_park.AdminFragments.ParkingAreaList;
import com.sehalsein.e_park.Model.ParkingAreaDetail;
import com.sehalsein.e_park.R;
import com.sehalsein.e_park.UserAdapter.UserPlaceListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPlaceList extends Fragment implements TextWatcher {


    private RecyclerView recyclerView;
    private String TAG = getClass().getName();

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference memebersReference ;
    private String NODE = null;
    private EditText searchBar;

    private String jamathId;
    //private GeometricProgressView geometricProgressView;

    private List<ParkingAreaDetail> parkingAreaDetailList =  new ArrayList<>();
    private List<ParkingAreaDetail> filteredparkingAreaDetailList =  new ArrayList<>();


    public UserPlaceList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_user_place_list, container, false);

        searchBar = layout.findViewById(R.id.editTextSearch);
        NODE = getResources().getString(R.string.firebase_databse_node_parking);
        memebersReference = mDatabase.getReference(NODE);

        recyclerView = layout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        loadList();

        return  layout;
    }
    private void loadList(){

        memebersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    parkingAreaDetailList.add(snapshot.getValue(ParkingAreaDetail.class));
                }

                filteredparkingAreaDetailList = parkingAreaDetailList;
                recyclerView.setAdapter(new UserPlaceListAdapter(getActivity(), parkingAreaDetailList));
                //geometricProgressView.setVisibility(View.INVISIBLE);
                searchBar.addTextChangedListener(UserPlaceList.this);

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

        List<ParkingAreaDetail> memeberListToReturn =  new ArrayList<>();

        for(ParkingAreaDetail memeber: filteredparkingAreaDetailList){
            if(memeber.getAreaName().toLowerCase().contains(searchName)
                    || memeber.getParkingName().toLowerCase().contains(searchName)
                    ){
                memeberListToReturn.add(memeber);
            }
        }
        filteredparkingAreaDetailList = memeberListToReturn;
//        if(searchName.equals(" ")){
//            filteredparkingAreaDetailList = parkingAreaDetailList;
//        }
        recyclerView.setAdapter(new UserPlaceListAdapter(getActivity(), memeberListToReturn));
    }
}
