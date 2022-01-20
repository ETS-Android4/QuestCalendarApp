package com.example.questcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NotificationsActivity extends AppCompatActivity {

    Button noNoti, yesNoti, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                }else{
                    onWelcome();
                }
            }
        });



        //hooks
        noNoti = findViewById(R.id.no_notifications_button);
        yesNoti = findViewById(R.id.yes_notifications_button);
        backbtn = findViewById(R.id.back_button_noti);


        //Change Activity when clicking
        backbtn.setOnClickListener(v -> onBack(v));
        //Receive notifications
        yesNoti.setOnClickListener(v -> onYesNoti());
        //No notifications
        noNoti.setOnClickListener(v-> onNoNoti());


    }



    public void onBack(View view){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void onYesNoti(){
        Toast.makeText(getApplicationContext(), "Notifications ON", Toast.LENGTH_LONG).show();
        finish();
    }


    public void onNoNoti(){
        Toast.makeText(getApplicationContext(), "Notifications OFF", Toast.LENGTH_LONG).show();
        finish();
    }

    private void onWelcome(){
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
    }

}