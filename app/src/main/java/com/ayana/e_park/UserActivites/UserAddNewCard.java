package com.ayana.e_park.UserActivites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ayana.e_park.Adapter.CardListAdapter;
import com.ayana.e_park.Model.CardDetail;
import com.ayana.e_park.Model.UserData;
import com.ayana.e_park.R;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;

public class UserAddNewCard extends AppCompatActivity {


    private RecyclerView recyclerView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private static String NODE = null;
    private List<CardDetail> cardDetailList = new ArrayList<>();
    private RelativeLayout emptyView;
    private FirebaseAuth mAuth;
    private GeometricProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_new_card);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        NODE = getResources().getString(R.string.firebase_databse_node_user_card);

        progressView = (GeometricProgressView) findViewById(R.id.progressView);

        myRef = database.getReference(NODE);

        //Inititalizing Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserAddNewCard.this, LinearLayoutManager.VERTICAL, false));

        loadCards();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int GET_NEW_CARD = 2;

                Intent intent = new Intent(UserAddNewCard.this, CardEditActivity.class);
                startActivityForResult(intent, GET_NEW_CARD);
            }
        });
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            String cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            String key = myRef.push().getKey();
            CardDetail cardDetail = new CardDetail(key,cardNumber,cardHolderName,cvv,expiry);

            myRef.child(UserData.userDetail.getId()).child(key).setValue(cardDetail);

        }
    }

    private void loadCards() {

        myRef.child(UserData.userDetail.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cardDetailList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    cardDetailList.add(snapshot.getValue(CardDetail.class));
                }

                recyclerView.setAdapter(new CardListAdapter(UserAddNewCard.this, cardDetailList));
                progressView.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //   Toast.makeText(this, "Something went wrong while searching", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
