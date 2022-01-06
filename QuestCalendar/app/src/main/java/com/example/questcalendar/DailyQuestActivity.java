package com.example.questcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class DailyQuestActivity extends AppCompatActivity {

    CheckBox done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quest);

        final Button goBack = findViewById(R.id.back_button_quest);
        goBack.setOnClickListener(v -> onBack(v));



        //checkbox
        done =(CheckBox) findViewById(R.id.done_check);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(done.isChecked()){
                    Toast.makeText(getApplicationContext(), "You received 10 exp!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}