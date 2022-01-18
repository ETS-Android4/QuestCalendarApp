package com.example.questcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //logo
        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setImageResource(R.drawable.calendar_logo);

        final Button login = findViewById(R.id.login_button);
        login.setOnClickListener(v -> onLogin(v));

        final Button register = findViewById(R.id.register_button);
        register.setOnClickListener(v -> onRegister(v));

    }

    public void onLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}