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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    //the list of the daily tasks
    private List<Task> daily;

    //the list of the monthly tasks
    private List<Task> weekly;

    //the list of the daily tasks
    private List<Task> monthly;

    //the list of the monthly tasks
    private List<Task> yearly;

    //the list of the punctual tasks
    private List<Task> punctual;

    //the list of the current day tasks
    private ArrayList<Task> tasksOfTheDay;

    //the way to find the user
    //username for now
    private final static String CURRENT_USERNAME = "aaaa";

    //link to the database
    public final static String QUEST_CALENDAR_LINK = "https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/";

    //to go to the users database
    public final static String USERS = "users";

    //to go to the tasks database
    public final static String TASKS = "task";

    //to get the frequency of a task
    public final static String FREQUENCY = "frequency";

    //to get the id of a task
    public final static String ID = "id";

    //to get the title of a task
    public final static String TITLE = "title";

    //to get the hour of a task
    public final static String HOUR = "hour";

    //to get the description of a task
    public final static String DESCRIPTION = "description";

    //to get the day of a task
    public final static String DAY = "day";

    //to get the month of a task
    public final static String MONTH = "month";

    //to get the year of a task
    public final static String YEAR = "year";

    //to get the year of a task
    public final static String MAX_TASK_ID = "maximum";

    //to know if a task has been done
    public final static String DONE = "done";

    //when the description is empty
    public final static String DEFAULT_DESCRIPTION = "";

    //reference to the database
    DatabaseReference reference;

    //reference to the database
    DatabaseReference userReference;

    //get the current logged user
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public TaskManager(Date day) {
        //set the date
        this.currentDay = day;
        this.daily = new ArrayList<Task>();
        this.weekly = new ArrayList<Task>();
        this.monthly = new ArrayList<Task>();
        this.yearly = new ArrayList<Task>();
        this.punctual = new ArrayList<Task>();
        this.tasksOfTheDay = new ArrayList<Task>();

    }

    //add the task t in the database
    public void addTask(@NonNull Task t) {
        int frequency = t.getFrequency();
        //adding the task in the right list
        if (frequency == Task.DAILY) {
            daily.add(t);
        } else if (frequency == Task.WEEKLY) {
            weekly.add(t);
        } else if (frequency == Task.MONTHLY) {
            monthly.add(t);
        } else if (frequency == Task.YEARLY) {
            yearly.add(t);
        } else {
            punctual.add(t);
        }


    }


    //return the sorted list by hour of the tasks of the currentDay
    public ArrayList<Task> getTaskOfTheDay() {
        findTasksOfTheDay();
        TaskComparator c = new TaskComparator();
        Collections.sort(this.tasksOfTheDay, c); //add a comparator for task
        return this.tasksOfTheDay;
    }

    public void setDay(Date newDay) {
        this.currentDay = newDay;

        //finding the task of the current day
        findTasksOfTheDay();
    }

    private void findTasksOfTheDay() {
        this.tasksOfTheDay = new ArrayList<Task>();
        for (Task d : daily) {
            tasksOfTheDay.add(d);


        }

        for (Task w : weekly) {
            if (w.getDay().getDayOfWeek() == currentDay.getDayOfWeek()) {
                tasksOfTheDay.add(w);
            }
        }

        for (Task m : monthly) {
            if (m.getDay().getDayOfMonth() == currentDay.getDayOfMonth()) {
                tasksOfTheDay.add(m);
            }

        }

        for (Task y : yearly) {
            if (y.getDay().getDayOfMonth() == currentDay.getDayOfMonth() && y.getDay().getMonth() == currentDay.getMonth()) {
                tasksOfTheDay.add(y);
            }
        }
        for (Task p : punctual) {
            if (p.getDay().isEqual(currentDay)) {
                tasksOfTheDay.add(p);
            }

        }
    }

    public void empty() {
        this.daily = new ArrayList<Task>();
        this.weekly = new ArrayList<Task>();
        this.monthly = new ArrayList<Task>();
        this.yearly = new ArrayList<Task>();
        this.punctual = new ArrayList<Task>();
        this.tasksOfTheDay = new ArrayList<Task>();
    }
}
