package com.ayana.e_park.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.ayana.e_park.Activity.LoginActivity;
import com.ayana.e_park.AdminActivity.AdminNewBookingActivity;
import com.ayana.e_park.Miscl.QRTest;
import com.ayana.e_park.Model.MoreOption;
import com.ayana.e_park.R;
import com.ayana.e_park.UserActivites.UserAddNewCard;
import com.ayana.e_park.UserActivites.UserPaymentGateway;
import com.ayana.e_park.UserActivites.UserPreviousBookingActivity;

import java.util.List;




/**
 * Created by sehalsein on 07/11/17.
 */

public class MoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MoreOption> moreOptionList;
    private Context context;
    private static final String PORTAL_KEY = "portal";
    private static final String COLLEGE_KEY = "CollegeID";




    public MoreAdapter(List<MoreOption> moreOptionList, Context context) {
        this.moreOptionList = moreOptionList;
        this.context = context;
                   // Set the scanner view as the content view


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_more_option, parent, false);
        return new MoreOptionViewHolder(v);
    }

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MyAdapter", "onActivityResult");
        makeToast("SAD");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MoreOption item = moreOptionList.get(position);
        MoreOptionViewHolder moreOptionViewHolder = (MoreOptionViewHolder) holder;

        moreOptionViewHolder.setRow(item);
        moreOptionViewHolder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (item.getCode()) {
                    case "addminNewBooking" :
                        newBooking();
                        break;
                    case "Logout":
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    case "userBookings" :
                        userPreviousBooking();
                        break;
                    case "userAddCard" :
                        userAddCard();
                        break;
                    case "adminQR" :
                        userQR();
                        break;
                    case "userPay" :
                        //userPayment();
                        break;
                    default:
                        makeToast("Comming Soon");
                }
            }
        });


    }

    private void userPayment(){
       // makeToast("jhg");
        context.startActivity(new Intent(context, UserPaymentGateway.class));
    }

    private void userPreviousBooking(){
        context.startActivity(new Intent(context, UserPreviousBookingActivity.class));
    }
    private void userQR(){
        context.startActivity(new Intent(context, QRTest.class));
    }
    private void userAddCard(){
        context.startActivity(new Intent(context, UserAddNewCard.class));
    }


    private void newBooking(){
        context.startActivity(new Intent(context, AdminNewBookingActivity.class));
    }


    private void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getItemCount() {
        return moreOptionList.size();
    }



    public class MoreOptionViewHolder extends RecyclerView.ViewHolder {

        private TextView moreOptionTitle;
        private TextView moreOptionDescription;
        private ImageView moreOptionIcon;
        private View row;

        public MoreOptionViewHolder(View itemView) {
            super(itemView);
            moreOptionTitle = itemView.findViewById(R.id.more_option_title);
            moreOptionDescription = itemView.findViewById(R.id.more_option_description);
            moreOptionIcon = itemView.findViewById(R.id.more_option_icon_image_view);
            this.row = itemView;
        }

        public void setRow(MoreOption data) {
            this.moreOptionTitle.setText(data.getTitle());
            if (data.getDescription().equals("")) {
                this.moreOptionDescription.setVisibility(View.GONE);
            } else {
                this.moreOptionDescription.setVisibility(View.VISIBLE);
                this.moreOptionDescription.setText(data.getDescription());
            }
            this.moreOptionIcon.setImageResource(data.getImage());

        }
    }
}
