package com.example.questcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    //variables
    Button login_btn;
    TextInputLayout mail, password;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //to go to sign up activity
        final Button register = findViewById(R.id.signup_button);
        register.setOnClickListener(v -> onRegister(v));

        //Hooks
        login_btn = findViewById(R.id.login_button);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);


        //Read data in Firebase on button click
        login_btn.setOnClickListener(v -> loginUser(v));



    }

    private Boolean validateEmail(){
        String val = mail.getEditText().getText().toString();
        String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            mail.setError("Email cannot be empty");
            return false;
        } else if(!val.matches(emailPattern)){
            mail.setError("Invalid email address");
            return false;
        }//maybe validate username unique???
        else{
            mail.setError(null);
            mail.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();

        if(val.isEmpty()){
            password.setError("Password cannot be empty");
            return false;
        } else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }



    public void loginUser(View view){
        //Validate login info
        if(!validateEmail() | !validatePassword()){
            return;
        } else {
            isUser(view);
        }


    }


    private void isUser(View v){
        String userEnteredMail = mail.getEditText().getText().toString().trim();
        //String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();
        //mUser = mAuth.getCurrentUser();
        //String uid = mUser.getUid();
        //DatabaseReference reference = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/")
                //.getReference("users").child(uid);


        mAuth.signInWithEmailAndPassword(userEnteredMail, userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    goToMain(v);
                }
            }
        });

        //Query checkUser = reference.orderByChild("username");

//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//          @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
        //                  username.setError(null);
        //          username.setErrorEnabled(false);
//
        //                  String objeto = dataSnapshot.getValue().toString();
        //          String emailFromDB = dataSnapshot.child("email")
        //                  .getValue(String.class);
//
        //                  String passwordFromDB = dataSnapshot.child("password")
        //                  .getValue(String.class);
//
        //                  String usernameFromDB = dataSnapshot.child("username")
        ////                  .getValue(String.class);
//
        //                  if(passwordFromDB.equals(userEnteredPassword)){
        //              username.setError(null);
        //              username.setErrorEnabled(false);
//

//                    }
        //                   else{
        //              password.setError("Wrong password");
        //                password.requestFocus();
        //          }
        //      }else{
        //           username.setError("This user doesn't exist");
        //          username.requestFocus();
        //      }
        //  }
//
        //          @Override
        //  public void onCancelled(@NonNull DatabaseError databaseError) {
//
        //          }
        //    });
//
  }


    public void onRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToMain(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra("username", usernameFromDB);
        //intent.putExtra("email", emailFromDB);
        //intent.putExtra("password", passwordFromDB);

        startActivity(intent);
    }
}