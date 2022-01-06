package com.example.questcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NotificationsActivity extends AppCompatActivity {

    Button noNoti, yesNoti, backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void onYesNoti(){
        Toast.makeText(getApplicationContext(), "Notifications ON", Toast.LENGTH_LONG).show();
    }


    public void onNoNoti(){
        Toast.makeText(getApplicationContext(), "Notifications OFF", Toast.LENGTH_LONG).show();
    }
}