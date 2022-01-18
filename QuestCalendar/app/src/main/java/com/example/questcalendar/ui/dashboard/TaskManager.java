package com.example.questcalendar.ui.dashboard;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.questcalendar.MainActivity;
import com.example.questcalendar.UserHelperClass;
import com.example.questcalendar.calendar.Date;
import com.example.questcalendar.calendar.Task;
import com.example.questcalendar.calendar.TaskComparator;
import com.example.questcalendar.calendar.exceptions.MyException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class TaskManager {

    //the day for which we want a list of the tasks
    private Date currentDay;

    //the max ID of all of the tasks
    private int maxTaskId;

    //the list of the daily tasks
    private List<Task> daily;

    //the list of the monthly tasks
    private List<Task> monthly;

    //the list of the punctual tasks
    private List<Task> punctual;

    //the list of the current day tasks
    private ArrayList<Task> tasksOfTheDay;

    //the way to find the user
    //username for now
    private final static String CURRENT_USERNAME = "aaaa";

    //link to the database
    private final static String QUEST_CALENDAR_LINK = "https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/";

    //to go to the users database
    private final static String USERS = "users";

    //to go to the tasks database
    private final static String TASKS = "task";

    //to get the frequency of a task
    private final static String FREQUENCY = "frequency";

    //to get the id of a task
    private final static String ID = "id";

    //to get the title of a task
    private final static String TITLE = "title";

    //to get the hour of a task
    private final static String HOUR = "hour";

    //to get the description of a task
    private final static String DESCRIPTION = "description";

    //to get the day of a task
    private final static String DAY = "day";

    //to get the month of a task
    private final static String MONTH = "month";

    //to get the year of a task
    private final static String YEAR = "year";

    //when the description is empty
    private final static String DEFAULT_DESCRIPTION = "";

    //reference to the database
    DatabaseReference reference;

    public TaskManager(Date day) {
        //set the date
        this.currentDay = day;
        this.daily = new ArrayList<Task>();
        this.monthly = new ArrayList<Task>();
        this.punctual = new ArrayList<Task>();
        this.tasksOfTheDay = new ArrayList<Task>();
        this.maxTaskId = 0;
        this.reference = FirebaseDatabase.getInstance(QUEST_CALENDAR_LINK).getReference(USERS).child(CURRENT_USERNAME).child(TASKS);


        //get the tasks from the database

        this.reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        //building the task from the database

                            //getting the frequency
                        int frequency = Integer.parseInt(child.child(FREQUENCY).getValue(String.class));


                            //getting the ID
                        int id = Integer.parseInt(child.child(ID).getValue(String.class));


                            //getting the title
                        String title = child.child(TITLE).getValue(String.class);


                            //getting the hour
                        int hour = Integer.parseInt(child.child(HOUR).getValue(String.class));


                            //getting the description
                            //description can be null
                        String description;
                        if (child.child(DESCRIPTION).exists()) {
                            description = child.child(DESCRIPTION).getValue(String.class);
                        } else {
                            description = DEFAULT_DESCRIPTION;
                        }

                            //getting the date
                        int day = Integer.parseInt(child.child(DAY).getValue(String.class));
                        int month = Integer.parseInt(child.child(MONTH).getValue(String.class));
                        int year = Integer.parseInt(child.child(YEAR).getValue(String.class));
                        Date date = new Date(day, month, year, 0);

                        Task currentTask = new Task(id, title, description, date, hour, frequency);

                        //adding the task in the right list
                        if (frequency == Task.DAILY) {
                            daily.add(currentTask);
                        } else if (frequency == Task.MONTHLY) {
                            monthly.add(currentTask);
                        } else {
                            punctual.add(currentTask);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //finding the task of the current day

        for (Task d : daily) {
            tasksOfTheDay.add(d);

            if (d.getId() > this.maxTaskId) {
                this.maxTaskId = d.getId();
            }
        }

        for (Task m : monthly) {
            if (m.getDay().getDayOfMonth() == currentDay.getDayOfMonth()) {
                tasksOfTheDay.add(m);
            }

            if (m.getId() > this.maxTaskId) {
                this.maxTaskId = m.getId();
            }
        }

        for (Task p : punctual) {
            if (p.getDay().isEqual(currentDay)) {
                tasksOfTheDay.add(p);
            }

            if (p.getId() > this.maxTaskId) {
                this.maxTaskId = p.getId();
            }
        }



    }

    //add the task t in the database
    public void addTask(Task t) {

        //use an helper
        String titleNewTask = t.getTitle();
        String descriptionNewTask = t.getDescription();
        String hourNewTask = Integer.toString(t.getHour());
        String frequencyNewTask = Integer.toString(t.getFrequency());
        String dayNewTask = Integer.toString(t.getDay().getDayOfMonth());
        String monthNewTask = Integer.toString(t.getDay().getMonth());
        String yearNewTask = Integer.toString(t.getDay().getYear());


            //find the id
        this.maxTaskId = this.maxTaskId +1;
        String idNewTask = Integer.toString(this.maxTaskId);

        TaskHelper newTask = new TaskHelper(idNewTask, titleNewTask, descriptionNewTask, hourNewTask, frequencyNewTask, dayNewTask, monthNewTask, yearNewTask);


        //gets the username as a identifier
        reference.child(idNewTask).setValue(newTask);


    }

    //delete the task t from the database
    public void deleteTask(Task t) {

    }

    //return the sorted list by hour of the tasks of the currentDay
    public ArrayList<Task> getTaskOfTheDay() {
        TaskComparator c = new TaskComparator();
        Collections.sort(this.tasksOfTheDay, c); //add a comparator for task
        return this.tasksOfTheDay;
    }

    public void setDay(Date newDay) {
        this.currentDay = newDay;
        this.tasksOfTheDay = new ArrayList<Task>();

        //finding the task of the current day
        for (Task d : daily) {
            tasksOfTheDay.add(d);

            if (d.getId() > this.maxTaskId) {
                this.maxTaskId = d.getId();
            }
        }

        for (Task m : monthly) {
            if (m.getDay().getDayOfMonth() == currentDay.getDayOfMonth()) {
                tasksOfTheDay.add(m);
            }

            if (m.getId() > this.maxTaskId) {
                this.maxTaskId = m.getId();
            }
        }

        for (Task p : punctual) {
            if (p.getDay().isEqual(currentDay)) {
                tasksOfTheDay.add(p);
            }

            if (p.getId() > this.maxTaskId) {
                this.maxTaskId = p.getId();
            }
        }
    }




}
