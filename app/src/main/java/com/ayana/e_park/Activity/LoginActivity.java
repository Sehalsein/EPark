package com.ayana.e_park.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ayana.e_park.AdminActivity.AdminHomeActivity;
import com.ayana.e_park.Model.UserData;
import com.ayana.e_park.Model.UserDetail;
import com.ayana.e_park.R;
import com.ayana.e_park.UserActivites.UserHomeActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private TextView createAccount;
    private EditText emailIdEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private static String TAG = "LoginActivity";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mUserRef;
    private DatabaseReference mStudentRef;
    private String USER_NODE = null;
    private String STUDENT_NODE = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        USER_NODE = getResources().getString(R.string.firebase_databse_node_user_detail);
//        STUDENT_NODE = getResources().getString(R.string.firebase_databse_node_student);
        mDatabase = FirebaseDatabase.getInstance();
        mUserRef = mDatabase.getReference(USER_NODE);
//        mStudentRef = mDatabase.getReference(STUDENT_NODE);

        emailIdEditText = findViewById(R.id.emailId_editText);
        passwordEditText = findViewById(R.id.password_editText);

        emailIdEditText.setText("user@epark.com");
        //emailIdEditText.setText("admin@epark.com");
        passwordEditText.setText("123456789");
        createAccount = findViewById(R.id.create_account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });

    }


    public void login(View view){

        if(validate()){
            final String email= emailIdEditText.getText().toString();
            final String password = passwordEditText.getText().toString();


            //makeToast("VALID");
            final MaterialDialog dialog = new MaterialDialog.Builder(LoginActivity.this)
                    .title("Authenticating")
                    .content("Please wait")
                    .progress(true, 0)
                    .cancelable(false)
                    .progressIndeterminateStyle(true)
                    .show();


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //makeToast("SUCS");
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //UserData.emailId = email;
                                //UserData.password = password;
                                getTypeOfUser(user.getUid());
                                dialog.dismiss();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                makeToast("Authentication failed.");
                                //updateUI(null);
                                dialog.dismiss();
                                new MaterialDialog.Builder(LoginActivity.this)
                                        .title("Unsuccessful")
                                        .content("Authentication failed.")
                                        .positiveText("Dismiss")
                                        .show();
                            }
                        }
                    });
        }else {
            makeToast("Please fill in all the fields!");
        }
    }


    private void getTypeOfUser(String userId) {
        mUserRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetail userDetail = dataSnapshot.getValue(UserDetail.class);
                updateUI(userDetail.getType(),userDetail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                makeToast("Database failure.");
            }
        });
    }

    private void openUser(){
        startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
        this.finish();
    }

    private void openAdmin(){
        startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
        this.finish();
    }

    private void updateUI(String type,UserDetail userDetail){

        UserData.userDetail = userDetail;

        switch (type){
            case "admin" :
                openAdmin();
                break;
            case "user" :
                openUser();
                break;
            default:
                makeToast("Not a valid user!!");
        }
    }


    private boolean validate() {
        boolean emailIdCheck;
        boolean passwordCheck;
        if (isEmpty(emailIdEditText)) {
            emailIdEditText.setError("Enter Email Id");
            emailIdCheck = false;
        } else {
            if (isValidEmail(emailIdEditText.getText().toString())) {
                emailIdCheck = true;
            } else {
                emailIdCheck = false;
                emailIdEditText.setError("Enter a valid email Id");
            }
        }

        if (isEmpty(passwordEditText)) {
            passwordEditText.setError("Generate Password");
            passwordCheck = false;
        } else {
            passwordCheck = true;
        }

        if (emailIdCheck && passwordCheck) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void makeToast(String message){
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}