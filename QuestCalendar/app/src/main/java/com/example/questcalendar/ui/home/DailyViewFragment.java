package com.example.questcalendar.ui.home;

import android.content.Intent;
import android.os.Bundle;

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

                displayTasks(arg0);


            }
        });



        //Display the tasks
        displayTasks(view);



        selectedDay.setText(selectedDate.toString());
        return view;
    }

    private void displayTasks(View view) {
        tasks = new ArrayList<Task>();
        tasksDisplay = new ArrayList<String>();
        try {
            Date date1 = new Date(7, 1, 2022, 4);
            Date date2 = new Date(8, 1, 2022, 6);
            Date date3 = new Date(5, 1, 2022, 3);
            Task t1 = new Task(1, "dentist appointment", "my tooth is really hurting me", date1, 8, 0);
            Task t2 = new Task(0, "Mobile Computing exam", "studying for the exam", date2, 10, 0);
            Task t3 = new Task(2, "fork my process", "I love SEC", date3, 10, 0);
            tasks.add(t1);
            tasks.add(t2);
            tasks.add(t3);
        } catch (MyException e) {
            Toast.makeText(getContext() , e.getMessage(), Toast.LENGTH_LONG).show();
        }


        for (Task t : tasks) {
            if (this.selectedDate.isEqual(t.getDay())) {
                tasksDisplay.add(t.toString());
            }
        }

        // creer un adaptateur a partir d'un array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, (String[]) tasksDisplay.toArray(new String[tasksDisplay.size()]));

        // afficher la liste des donnees dans la ListView
        listView.setAdapter(adapter);
    }


}