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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAvatarActivity extends AppCompatActivity {

    Button pic1,pic2,pic3,pic4, backbtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_avatar);

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
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        pic1 = findViewById(R.id.first_button);
        pic2 = findViewById(R.id.second_button);
        pic3 = findViewById(R.id.third_button);
        pic4 = findViewById(R.id.forth_button);
        backbtn = findViewById(R.id.back_button_char);

        //goback
        backbtn.setOnClickListener(v -> onBack(v));

        //set pic
        pic1.setOnClickListener(v -> setProfilePic(uid, 1));
        pic2.setOnClickListener(v -> setProfilePic(uid, 2));
        pic3.setOnClickListener(v -> setProfilePic(uid, 3));
        pic4.setOnClickListener(v -> setProfilePic(uid, 4));


    }


    public  void setProfilePic(String uid, int pic){
        DatabaseReference reference = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String levelDB = String.valueOf(dataSnapshot.child(uid).child("level").getValue());
                int level = Integer.valueOf(levelDB);
                if(level >= pic){
                    Toast.makeText(getApplicationContext(), "Character setted!", Toast.LENGTH_LONG).show();
                    reference.child(uid).child("profilePic").setValue(pic);
                }else{
                    Toast.makeText(getApplicationContext(), "You need to be at least level " + pic +" to use it!", Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void onBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void onWelcome(){
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
    }


}