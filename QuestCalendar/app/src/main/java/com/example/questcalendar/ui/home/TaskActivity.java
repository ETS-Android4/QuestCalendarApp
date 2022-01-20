package com.example.questcalendar.ui.home;

import static com.example.questcalendar.ui.dashboard.TaskManager.QUEST_CALENDAR_LINK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.questcalendar.R;
import com.example.questcalendar.calendar.Date;
import com.example.questcalendar.calendar.Task;
import com.example.questcalendar.databinding.FragmentAddTaskBinding;
import com.example.questcalendar.ui.dashboard.TaskManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    private TextView titleView, descriptionView, hourView, dateView;
    private Button delete, goBack;
    private CheckBox doneButton;

    private String id, title, description, hour, date, done;

    Intent intent;
    
    boolean alreadyDone;

    ///get the current logged user
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


        //recuperation de l'intent
        intent = getIntent();

        //link to the database
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance(QUEST_CALENDAR_LINK).getReference(TaskManager.USERS).child(mUser.getUid());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    id = dataSnapshot.child("currentTask").getValue().toString();

                    for (DataSnapshot child : dataSnapshot.child(TaskManager.TASKS).getChildren()) {

                        if (id.equals(child.child(TaskManager.ID).getValue().toString())) {
                            title = child.child(TaskManager.TITLE).getValue(String.class);
                            titleView.setText(title);
                            hour = child.child(TaskManager.HOUR).getValue(String.class) + "H";
                            hourView.setText(hour);

                            int day = Integer.parseInt(child.child(TaskManager.DAY).getValue(String.class));
                            int month = Integer.parseInt(child.child(TaskManager.MONTH).getValue(String.class));
                            int year = Integer.parseInt(child.child(TaskManager.YEAR).getValue(String.class));
                            Date today = new Date(day, month, year);

                            int frequency = Integer.parseInt(child.child(TaskManager.FREQUENCY).getValue(String.class));
                            if (frequency == Task.DAILY) {
                                date = "DAILY";
                            } else if (frequency == Task.WEEKLY) {
                                date = "EVERY " + today.getNameDayOfWeek();
                            } else if (frequency == Task.MONTHLY) {
                                date = "MONTHLY : " + child.child(TaskManager.DAY).getValue(String.class);
                            } else if (frequency == Task.YEARLY) {
                                date = child.child(TaskManager.DAY).getValue(String.class) + Date.MONTHS[Integer.parseInt(child.child(TaskManager.DAY).getValue(String.class)) -1];
                            } else {
                                date = today.toString();
                            }
                            dateView.setText(date);


                            if (child.child(TaskManager.DESCRIPTION).exists()) {
                                description = child.child(TaskManager.DESCRIPTION).getValue(String.class);
                            } else {
                                description = TaskManager.DEFAULT_DESCRIPTION;
                            }
                            descriptionView.setText(description);

                            done = child.child(TaskManager.DONE).getValue(String.class);

                            alreadyDone = done.equals(Task.DONE);


                            doneButton.setChecked(alreadyDone);



                        }

                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        //title of the task
        titleView = findViewById(R.id.title_task);
        //title = intent.getStringExtra("title");


        //description of the task
        descriptionView = findViewById(R.id.description_task);
        //description = intent.getStringExtra("description");


        //hour of the task
        hourView = findViewById(R.id.hour_task);
        //hour = intent.getStringExtra("hour");


        //date of the day
        dateView = findViewById(R.id.date_task);
        //date = intent.getStringExtra("date");


        //checkbox
        doneButton = (CheckBox) findViewById(R.id.done_check_task);



        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doneButton.isChecked()){

                    if ( !alreadyDone ) {
                        Toast.makeText(getApplicationContext(), "You received 10 exp!", Toast.LENGTH_LONG).show();
                        alreadyDone = true;
                        reference.child(TaskManager.TASKS).child(id).child(TaskManager.DONE).setValue(Task.DONE);
                    }


                }
            }
        });


        //deleting the task
        delete = findViewById(R.id.delete_button_task);
        //id = intent.getStringExtra("id");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleting the task
                reference.child(TaskManager.TASKS).child(id).removeValue();
                Toast.makeText(getApplicationContext(), "Task deleted successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        //leaving the task
        goBack = findViewById(R.id.back_button_task);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //reference.child("toAdd").setValue("toDelete");
    }

    /*
    public static final String EXTRA_MESSAGE = "com.example.exo2.MESSAGE";
    EditText editText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // necessaires
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // trouver la fenetre de texte modifiable
        this.editText = (EditText) findViewById(R.id.editTextTextPersonName);



        //recuperation de l'intent
        Intent intent = getIntent();

        //recuperation du nom du contact
        String nom = intent.getStringExtra("nom");
        //recuperation de la position dans le tableau car je veux la renvoyer
        this.position = intent.getIntExtra("position", 0);

        //afficher le nom du contact
        this.editText.setText(nom);

    }

    public void sendMessage(View view) {
        //creation of the intent
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);


        // recuperer le nom modifier
        String message = editText.getText().toString();

        //sending le nouveau nom to the 2nd activity
        intent.putExtra("new_nom", message);

        //sending position to the 2nd activity
        intent.putExtra("position", this.position);

        // repondre a startActivityForResult
        setResult(RESULT_OK, intent);

        // finir cette activite
        finish();



    }
     */
}