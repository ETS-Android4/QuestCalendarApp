package com.example.questcalendar.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Calendar;


public class MonthlyViewFragment extends Fragment {




    private CalendarView monthlyView;
    private TextView selectedDay;
    //private TextView debug;
    Intent intent;
    TextView taskNbView;
    private int taskNb;
    private ArrayList<Task> tasks;
    private Date selectedDate;
    private TaskManager taskManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly_view, container, false);



        taskNbView = (TextView) view.findViewById(R.id.task_nb);
        //debug = (TextView) view.findViewById(R.id.debug);

        selectedDate = new Date();
        taskNb = 0;
        taskManager = new TaskManager(selectedDate, 0, new ArrayList<Task>(), new ArrayList<Task>(), new ArrayList<Task>(), new ArrayList<Task>());


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
                selectedDate = new Date(dayOfMonth, month +1, year, (dayOfWeek -1));
                //debug.setText(date + " " + selectedDate.toString());
                taskManager.setDay(selectedDate);
                displayTaskNb(view);
            }
        });


        displayTaskNb(view);

        return view;
    }

    private void displayTaskNb(View view) {

        ArrayList<Task> tasksOfTheDay = taskManager.getTaskOfTheDay();
        taskNb = tasksOfTheDay.size();


        taskNbView.setText("Number of tasks for today: " + taskNb);
    }


}