package com.example.questcalendar.ui.dashboard;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.questcalendar.MainActivity;
import com.example.questcalendar.UserHelperClass;
import com.example.questcalendar.calendar.Date;
import com.example.questcalendar.calendar.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    private List<Task> tasksOfTheDay;

    //the way to find the user
    //username for now
    private final static String CURRENT_USERNAME = "aaaa";

    //link to the database
    private final static String QUEST_CALENDAR_LINK = "https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/";

    //to go to the users database
    private final static String USERS = "users";

    //to go to the tasks database
    private final static String TASKS = "task";

    //reference to the database
    DatabaseReference reference;

    TaskManager(Date day) {
        //set the date
        this.currentDay = day;
        this.daily = new ArrayList<Task>();
        this.monthly = new ArrayList<Task>();
        this.punctual = new ArrayList<Task>();
        this.tasksOfTheDay = new ArrayList<Task>();
        this.maxTaskId = 1;
        this.reference = FirebaseDatabase.getInstance(QUEST_CALENDAR_LINK).getReference(USERS);


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
        this.maxTaskId++;
        String idNewTask = Integer.toString(this.maxTaskId);

        TaskHelper newTask = new TaskHelper(idNewTask, titleNewTask, descriptionNewTask, hourNewTask, frequencyNewTask, dayNewTask, monthNewTask, yearNewTask);


        //gets the username as a identifier
        reference.child(CURRENT_USERNAME).child(TASKS).child(idNewTask).setValue(newTask);


    }

    //delete the task t from the database
    public void deleteTask(Task t) {

    }

    //return the sorted list by hour of the tasks of the currentDay
    public PriorityQueue<Task> getTaskOfTheDay() {
        return new PriorityQueue<Task>(this.tasksOfTheDay); //add a comparator for task
    }

    public void setDay(Date newDay) {
        this.currentDay = newDay;
    }


}
