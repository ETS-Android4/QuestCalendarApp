package com.example.questcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    //variables
    TextInputLayout regUsername, regEmail, regPassword, regConfirmPassword;
    Button regToLogin, regBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Hooks
        regToLogin = findViewById(R.id.signin_button);
        regBtn = findViewById(R.id.register_button);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        regConfirmPassword = findViewById(R.id.confirm_password);

        //Change Activity when clicking
        regToLogin.setOnClickListener(v -> onLogin(v));


        //Save data in Firebase on button click
        regBtn.setOnClickListener(v -> registerUser(v));

    }

    private Boolean validateUsername(){
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace= "\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            regUsername.setError("Username cannot be empty");
            return false;
        } else if(val.length()<4){
            regUsername.setError("Username cannot be less than 4 letters");
            return false;
        }else if(val.length()>15){
            regUsername.setError("Username cannot be more than 15 letters");
            return false;
        }else if(!val.matches(noWhiteSpace)){
            regUsername.setError("White spaces not allowed");
            return false;
        }//maybe validate username unique???
        else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            regEmail.setError("Email cannot be empty");
            return false;
        } else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }//maybe validate username unique???
        else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal ="^[a-zA-Z0-9]+$";

        if(val.isEmpty()){
            regPassword.setError("Password cannot be empty");
            return false;
        } else if(!val.matches(passwordVal)){
            regPassword.setError("Password is too weak");
            return false;
        }else if(val.length()<4){
            regPassword.setError("Password cannot be less than 4 letters");
            return false;
        }
        else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateConfirmPassword() {
        String val = regConfirmPassword.getEditText().getText().toString();
        String pass = regPassword.getEditText().getText().toString();
        String passwordVal = "^[a-zA-Z0-9]+$";

        if (val.isEmpty()) {
            regConfirmPassword.setError("Confirm Password cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regConfirmPassword.setError("Password is too weak");
            return false;
        } else if (val.length() < 4) {
            regConfirmPassword.setError("Confirm Password cannot be less than 4 letters");
            return false;
        } else if (!val.equals(pass)) {
            regConfirmPassword.setError("Passwords should match");
            return false;
        } else {
            regConfirmPassword.setError(null);
            regConfirmPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View v) {
        rootNode = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("users");

        if(!validateUsername() | !validateEmail() | !validatePassword() | !validateConfirmPassword()){
            return;
        }


            //get all the values form the text fields
            String username = regUsername.getEditText().getText().toString();
            String email = regEmail.getEditText().getText().toString();
            String password = regPassword.getEditText().getText().toString();
            String confirmPassword = regConfirmPassword.getEditText().getText().toString();

            UserHelperClass helperClass = new UserHelperClass(username, email, password);
            //gets the username as a identifier (idk how to create with uid)
            reference.child(username).setValue(helperClass);
            onLogin(v);



    }

    public Boolean UserNotExists(){
        //true when the user doesn't exists
        //false when there is one with the same name
        final int[] flag = {0};
        String userEnteredUsername = regUsername.getEditText().getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue().toString();
                    if(userEnteredUsername.equals(usernameFromDB)){
                        regUsername.setError("This user already exists");
                        flag[0] = 1;
                    }

                }else{
                    regUsername.setError(null);
                    regUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(flag[0]==1){
            return false;
        }else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }



    public void onLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}