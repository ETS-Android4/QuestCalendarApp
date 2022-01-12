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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly_view, container, false);



        taskNbView = (TextView) view.findViewById(R.id.task_nb);
        //debug = (TextView) view.findViewById(R.id.debug);

        selectedDate = new Date();
        taskNb = 0;



        //selectedDay = (TextView) view.findViewById(R.id.selected_day);
        monthlyView = (CalendarView) view.findViewById(R.id.monthly_view);
        monthlyView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                //String date = Integer.toString(dayOfMonth) + "." +  Integer.toString(month) + "." +  Integer.toString(year);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                //selectedDay.setText(date);
                selectedDate = new Date(dayOfMonth, month +1, year, ((dayOfWeek +5) % 7));
                //debug.setText(date + " " + selectedDate.toString());
                displayTaskNb(view);
            }
        });

        displayTaskNb(view);

        return view;
    }

    private void displayTaskNb(View view) {

        taskNb = 0;
        tasks = new ArrayList<Task>();
        try {
            Date date1 = new Date(7, 1, 2022, 4);
            Date date2 = new Date(8, 1, 2022, 6);
            Date date3 = new Date(5, 1, 2022, 3);
            Task t1 = new Task(1, "dentist appointment", "my tooth is really hurting me", date1, 8, 0);
            Task t2 = new Task(0, "Mobile Computing exam", "studying for the exam", date2, 10, 0);
            Task t3 = new Task(2, "fork my process", "I love SEC", date2, 10, 0);
            tasks.add(t1);
            tasks.add(t2);
            tasks.add(t3);
        } catch (MyException e) {
            Toast.makeText(getContext() , e.getMessage(), Toast.LENGTH_LONG).show();
        }


        for (Task t : tasks) {
            if (this.selectedDate.isEqual(t.getDay())) {
                taskNb++;
            }
        }

        taskNbView.setText("Number of tasks for today: " + taskNb);
    }


}