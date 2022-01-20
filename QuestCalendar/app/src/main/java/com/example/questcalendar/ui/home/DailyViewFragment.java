package com.example.questcalendar.ui.home;

import static com.example.questcalendar.ui.dashboard.TaskManager.QUEST_CALENDAR_LINK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.questcalendar.R;
import com.example.questcalendar.calendar.Date;
import com.example.questcalendar.calendar.Task;
import com.example.questcalendar.calendar.exceptions.MyException;
import com.example.questcalendar.ui.dashboard.TaskManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class DailyViewFragment extends Fragment {

    private Button previousDayButton;
    private Button nextDayButton;
    private Date selectedDate;
    private TextView selectedDay;
    Intent intent;
    ListView listView;
    private ArrayList<Task> tasks;
    private ArrayList<String> tasksDisplay;


    private TaskManager taskManager;

    ///get the current logged user
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;


    public DailyViewFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        //date of the selected day
        int dayOfMonth = requireArguments().getInt("day_of_month");
        int month = requireArguments().getInt("month");
        int year = requireArguments().getInt("year");
        char d = requireArguments().getChar("day_of_weak");
        selectedDate = new Date();

        taskManager = new TaskManager(new Date());

        //link to the database
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance(QUEST_CALENDAR_LINK).getReference(TaskManager.USERS).child(mUser.getUid()).child(TaskManager.TASKS);



        //to add a task, using the right ID
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        //building the task from the database

                        //getting the frequency
                        int frequency = Integer.parseInt(child.child(TaskManager.FREQUENCY).getValue(String.class));


                        //getting the ID
                        int id = Integer.parseInt(child.child(TaskManager.ID).getValue(String.class));


                        //getting the title
                        String title = child.child(TaskManager.TITLE).getValue(String.class);


                        //getting the hour
                        int hour = Integer.parseInt(child.child(TaskManager.HOUR).getValue(String.class));


                        //getting the description
                        //description can be null
                        String description;
                        if (child.child(TaskManager.DESCRIPTION).exists()) {
                            description = child.child(TaskManager.DESCRIPTION).getValue(String.class);
                        } else {
                            description = TaskManager.DEFAULT_DESCRIPTION;
                        }

                        //getting the date
                        int day = Integer.parseInt(child.child(TaskManager.DAY).getValue(String.class));
                        int month = Integer.parseInt(child.child(TaskManager.MONTH).getValue(String.class));
                        int year = Integer.parseInt(child.child(TaskManager.YEAR).getValue(String.class));
                        Date date = new Date(day, month, year, 0);

                        Task currentTask = new Task(id, title, description, date, hour, frequency);
                        taskManager.addTask(currentTask);



                    }

                    ArrayList<Task> tasks = taskManager.getTaskOfTheDay();

                    View view = inflater.inflate(R.layout.fragment_daily_view, container, false);
                    displayTasks(view);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_view, container, false);

        // trouver la ListView
        listView = (ListView) view.findViewById(R.id.list_view);

        selectedDay = (TextView) view.findViewById(R.id.selected_day);

        //going to the previous day
        previousDayButton = (Button) view.findViewById(R.id.previous_day_button);
        previousDayButton.setOnClickListener(new View.OnClickListener() {

                                                 @Override
                                                 public void onClick(View arg0) {
                                                     selectedDate = selectedDate.getPreviousDay();
                                                     selectedDay.setText(selectedDate.toString());
                                                     taskManager.setDay(selectedDate);

                                                     displayTasks(arg0);


                                                 }
                                             });

        //going to the next day
        nextDayButton = (Button) view.findViewById(R.id.next_day_button);
        nextDayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                selectedDate = selectedDate.getNextDay();
                selectedDay.setText(selectedDate.toString());
                taskManager.setDay(selectedDate);

                displayTasks(arg0);


            }
        });



        //Display the tasks
        //displayTasks(view);



        selectedDay.setText(selectedDate.toString());
        return view;
    }

    private void displayTasks(View view) {
        tasks = taskManager.getTaskOfTheDay();
        tasksDisplay = new ArrayList<String>();



        for (Task t : tasks) {
                tasksDisplay.add(t.toString());
        }

        // creer un adaptateur a partir d'un array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, (String[]) tasksDisplay.toArray(new String[tasksDisplay.size()]));

        // afficher la liste des donnees dans la ListView
        listView.setAdapter(adapter);
    }


}