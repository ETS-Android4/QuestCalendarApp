package com.example.questcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    Button saveButton;
    TextInputLayout updUsername, updEmail, updPassword, updConfirmPassword;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Hooks
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        saveButton = findViewById(R.id.save_button);
        updUsername = findViewById(R.id.edit_username);
        updEmail =findViewById(R.id.edit_email);
        updPassword = findViewById(R.id.edit_password);
        updConfirmPassword = findViewById(R.id.edit_confirm_password);



        //comprobamos que esté logueado
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                }else{
                    onWelcome();
                }
            }
        });


        //En on create hacemos la request para recibir los datos y ponerlo
        rootNode = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot user = dataSnapshot.child(uid);
                //getting and setting username
                String username = user.child("username").getValue().toString();
                updUsername.getEditText().setText(username);
                //getting and setting password
                String password = user.child("password").getValue().toString();
                updPassword.getEditText().setText(password);
                //getting and setting email
                String email = user.child("email").getValue().toString();
                updEmail.getEditText().setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        //Cuando pulsemos el boton de guardar, que se actualicen los datos
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave(v);
            }
        });


    }



    private void onSave(View v){
        rootNode = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        if(!validateUsername() | !validateEmail() | !validatePassword() | !validateConfirmPassword()){
            return;
        }
        //get all the values form the text fields
        String username = updUsername.getEditText().getText().toString();
        String email = updEmail.getEditText().getText().toString();
        String password = updPassword.getEditText().getText().toString();
        String confirmPassword = updConfirmPassword.getEditText().getText().toString();


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot user = dataSnapshot.child(uid);
                //getting and inting level
                String strlevel = user.child("level").getValue().toString();
                int level = Integer.parseInt(strlevel);
                //getting and inting experience
                String strexperience = user.child("experience").getValue().toString();
                int experience = Integer.parseInt(strexperience);
                //getting and inting profile pic
                String strpp = user.child("profilePic").getValue().toString();
                int pp = Integer.parseInt(strpp);

                UserHelperClass helperClass = new UserHelperClass(username, email, password, level, experience, pp);
                reference.child(mUser.getUid()).setValue(helperClass);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        mUser.updateEmail(email);

        Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_LONG).show();
        onMain(v);

    }



    private Boolean validateUsername(){
        String val = updUsername.getEditText().getText().toString();
        String noWhiteSpace= "\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            updUsername.setError("Username cannot be empty");
            return false;
        } else if(val.length()<4){
            updUsername.setError("Username cannot be less than 4 letters");
            return false;
        }else if(val.length()>15){
            updUsername.setError("Username cannot be more than 15 letters");
            return false;
        }else if(!val.matches(noWhiteSpace)){
            updUsername.setError("White spaces not allowed");
            return false;
        }//maybe validate username unique???
        else{
            updUsername.setError(null);
            updUsername.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail(){
        String val = updEmail.getEditText().getText().toString();
        String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            updEmail.setError("Email cannot be empty");
            return false;
        } else if(!val.matches(emailPattern)){
            updEmail.setError("Invalid email address");
            return false;
        }//maybe validate username unique???
        else{
            updEmail.setError(null);
            updEmail.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword(){
        String val = updPassword.getEditText().getText().toString();
        String passwordVal ="^[a-zA-Z0-9]+$";

        if(val.isEmpty()){
            updPassword.setError("Password cannot be empty");
            return false;
        } else if(!val.matches(passwordVal)){
            updPassword.setError("Password is too weak");
            return false;
        }else if(val.length()<6){
            updPassword.setError("Password cannot be less than 6 characters");
            return false;
        }
        else{
            updPassword.setError(null);
            updPassword.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateConfirmPassword() {
        String val = updConfirmPassword.getEditText().getText().toString();
        String pass = updPassword.getEditText().getText().toString();
        String passwordVal = "^[a-zA-Z0-9]+$";

        if (val.isEmpty()) {
            updConfirmPassword.setError("Confirm Password cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            updConfirmPassword.setError("Password is too weak");
            return false;
        } else if (val.length() < 4) {
            updConfirmPassword.setError("Confirm Password cannot be less than 4 letters");
            return false;
        } else if (!val.equals(pass)) {
            updConfirmPassword.setError("Passwords should match");
            return false;
        } else {
            updConfirmPassword.setError(null);
            updConfirmPassword.setErrorEnabled(false);
            return true;
        }
    }

    private void onWelcome(){
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
    }

    public void onMain(View view){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}