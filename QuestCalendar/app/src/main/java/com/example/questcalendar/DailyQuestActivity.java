package com.example.questcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class DailyQuestActivity extends AppCompatActivity {

    CheckBox done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quest);

        getQuest();

        final Button goBack = findViewById(R.id.back_button_quest);
        goBack.setOnClickListener(v -> onBack(v));



        //checkbox
        done =(CheckBox) findViewById(R.id.done_check);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(done.isChecked()){
                    Toast.makeText(getApplicationContext(), "You received 10 exp!", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        });

    }

    public void onBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void getQuest(){
        DatabaseReference reference = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("quests");


        //Hooks
        TextView title = findViewById(R.id.title_daily_quest_text);
        TextView description = findViewById(R.id.description_daily_quest_text);
        TextView type = findViewById(R.id.type_daily_quest_text);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot random = dataSnapshot.child(String.valueOf(getRandom(1,3)));
                Log.d("valor", String.valueOf(random));
                title.setText(random.child("title").getValue().toString());
                description.setText(random.child("description").getValue().toString());
                type.setText(random.child("Type").getValue().toString());
                //done
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }


    public int getRandom(int min, int max){


        Random random = new Random();
        return random.nextInt(max - min) + min;
    }


}