package com.sehalsein.e_park.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sehalsein.e_park.AdminActivity.AddNewParkingArea;
import com.sehalsein.e_park.Model.BikeDetail;
import com.sehalsein.e_park.Model.BusDetail;
import com.sehalsein.e_park.Model.CarDetail;
import com.sehalsein.e_park.Model.ParkingAreaDetail;
import com.sehalsein.e_park.Model.UserDetail;
import com.sehalsein.e_park.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText emailIdEditText;
    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private UserDetail userDetail;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private String NODE = null;
    private String TAG = getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        fullNameEditText = findViewById(R.id.full_name_edit_text);
        emailIdEditText = findViewById(R.id.email_id_editText);
        phoneNumberEditText = findViewById(R.id.mobile_no_edit_text);
        passwordEditText = findViewById(R.id.password_editText);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        NODE = getResources().getString(R.string.firebase_databse_node_parking);
        mRef = mDatabase.getReference(NODE);
    }

    public void signUp(View view) {
        if (validate()) {
            createUser();
        } else {
            makeToast("Error");
        }
    }


    private void updateUserDetail(FirebaseUser user){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userDetail.getName())
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    private void createUser() {

        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Loading")
                .content("Please wait!!")
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(true)
                .show();


        mAuth.createUserWithEmailAndPassword(userDetail.getEmail(), userDetail.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification();
                            updateUserDetail(user);
                            String key = user.getUid();
                            userDetail.setId(key);
                            mRef.child(key).setValue(userDetail, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(databaseError != null){
                                        makeToast(databaseError.getMessage());
                                    }else{
                                        //updateDatabase();
                                        successProgress();
                                    }

                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            makeToast("Authentication failed");
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void successProgress() {
        new MaterialDialog.Builder(SignUpActivity.this)
                .title("Successful")
                .content("User Created")
                .iconRes(R.drawable.ic_notifications_black_24dp)
                .positiveText("Ok")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SignUpActivity.this.finish();
                    }
                })
                .show();

    }

    private void makeToast(String message) {
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean validate() {
        boolean fullnameCheck;
        boolean phoneNumberCheck;
        boolean emailIdCheck;
        boolean passwordCheck;
        boolean bikeAddCheck;
        boolean carCapacityCheck;
        boolean carBaseCheck;
        boolean carAddCheck;
        boolean busCapacityCheck;
        boolean busBaseCheck;
        boolean busAddCheck;

        String fullName = "";
        String phoneNumber = "";
        String emailId = "";
        String password = "";


        if (isEmpty(fullNameEditText)) {
            fullNameEditText.setError("Enter name");
            fullnameCheck = false;
        } else {
            fullnameCheck = true;
            fullName = fullNameEditText.getText().toString();
        }

        if (isEmpty(phoneNumberEditText)) {
            phoneNumberEditText.setError("Enter phone number");
            phoneNumberCheck = false;
        } else {
            phoneNumberCheck = true;
            phoneNumber = phoneNumberEditText.getText().toString();
        }

        if (isEmpty(emailIdEditText)) {
            emailIdEditText.setError("Enter Email Id");
            emailIdCheck = false;
        } else {
            emailIdCheck = true;
            emailId = emailIdEditText.getText().toString();
        }
        if (isEmpty(passwordEditText)) {
            passwordEditText.setError("Enter Bike Fare");
            passwordCheck = false;
        } else {
            passwordCheck = true;
            password = passwordEditText.getText().toString();
        }


        if (fullnameCheck && phoneNumberCheck && emailIdCheck && passwordCheck ) {
            userDetail = new UserDetail(fullName, phoneNumber, emailId, password);
            return true;
        } else {
            return false;
        }
    }
}
