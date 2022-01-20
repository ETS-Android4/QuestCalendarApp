package com.example.questcalendar.ui.home;

import static com.example.questcalendar.ui.dashboard.TaskManager.QUEST_CALENDAR_LINK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class MonthlyViewFragment extends Fragment {




    private CalendarView monthlyView;
    private TextView selectedDay;
    //private TextView debug;
    Intent intent;
    TextView taskNbView;
    private ArrayList<Task> tasks;
    private Date selectedDate;
    private TaskManager taskManager;

    ///get the current logged user
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly_view, container, false);

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
                        Date date = new Date(day, month, year);

                        Task currentTask = new Task(id, title, description, date, hour, frequency);
                        taskManager.addTask(currentTask);



                    }

                    ArrayList<Task> tasks = taskManager.getTaskOfTheDay();
                    displayTaskNb(tasks.size());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        taskNbView = (TextView) view.findViewById(R.id.task_nb);
        taskNbView.setText("Hello");
        //debug = (TextView) view.findViewById(R.id.debug);

        selectedDate = new Date();
        //taskManager = new TaskManager(selectedDate, 0, new ArrayList<Task>(), new ArrayList<Task>(), new ArrayList<Task>(), new ArrayList<Task>());
        taskManager = new TaskManager(new Date());

        //selectedDay = (TextView) view.findViewById(R.id.selected_day);
        monthlyView = (CalendarView) view.findViewById(R.id.monthly_view);
        monthlyView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                //String date = Integer.toString(dayOfMonth) + "." +  Integer.toString(month) + "." +  Integer.toString(year);
                Calendar calendar = Calendar.getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                //selectedDay.setText(date);
                selectedDate = new Date(dayOfMonth, month +1, year);
                //debug.setText(date + " " + selectedDate.toString());
                taskManager.setDay(selectedDate);
                ArrayList<Task> tasks = taskManager.getTaskOfTheDay();
                displayTaskNb(tasks.size());
                //reference.child("toAccess").setValue("toDelete");
                //reference.child("toAccess").removeValue();
            }
        });



        return view;
    }

    private void displayTaskNb(int taskNb) {

        //ArrayList<Task> tasksOfTheDay = taskManager.getTaskOfTheDay();
        //taskNb = tasksOfTheDay.size();

        taskNbView.setText("Number of tasks for today: " + taskNb);
    }


}