package com.example.questcalendar.ui.dashboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.questcalendar.MainActivity;
import com.example.questcalendar.R;
import com.example.questcalendar.calendar.Date;
import com.example.questcalendar.calendar.Task;
import com.example.questcalendar.databinding.FragmentAddTaskBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Time;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    private Button dateButton, hourButton;
    int hour, minutes;
    private AddTaskViewModel addTaskViewModel;
    private FragmentAddTaskBinding binding;
    private Button addTask;
    private MaterialButton daily, monthly;
    private TextInputLayout title, description;
    private Date pickedDate;
    private boolean hourPicked;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addTaskViewModel =
                new ViewModelProvider(this).get(AddTaskViewModel.class);

        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //select a title
        title = root.findViewById(R.id.task_title);

        //select a title
        description = root.findViewById(R.id.task_description);


        //select date
        dateButton = root.findViewById(R.id.date_picker_button);
        //init
        initDatePicker();
        //on change pick the hour of the user
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                openDatePicker(arg0);

            }
        });


        //select hour
        hourButton = root.findViewById(R.id.hour_picker_button);
        hourPicked = false;
        //init

        //onclick
        hourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minutes = minutes;
                        //hourButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                        hourButton.setText(Integer.toString(hourOfDay));
                        hourPicked = true;
                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener, hour, minutes, true);
                timePickerDialog.setTitle("Select time");
                timePickerDialog.show();
            }
        });


        //frequency
        daily = root.findViewById(R.id.toggle_button_daily);
        monthly = root.findViewById(R.id.toggle_button_monthly);

        //add task
        addTask = root.findViewById(R.id.add_task_button);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                int idNewTask = 0;

                //to validate
                String titleNewTask = title.getEditText().getText().toString();

                String descriptionNewTask = description.getEditText().getText().toString();

                //String hourNewTask = hourButton.getText().toString();

                int frequencyNewTask;
                if (daily.isChecked()) {
                    frequencyNewTask = Task.DAILY;
                } else if (monthly.isChecked()) {
                    frequencyNewTask = Task.MONTHLY;
                } else {
                    frequencyNewTask = Task.PUNCTUAL;
                }

                int taskValidated = 1;

                //verifying the title of the task
                if (titleNewTask.isEmpty()) {
                    title.setError("Title cannot be empty");
                    taskValidated = 0;
                }

                //verifying that a date has been picked
                if (pickedDate == null) {
                    dateButton.setError("Pick a date, please");
                    taskValidated = 0;
                }

                //verifying that an hour has been picked
                if (!hourPicked) {
                    hourButton.setError("Pick an hour, please");
                    taskValidated = 0;
                }

                if (taskValidated == 1) {

                    Task newTask = new Task(idNewTask, titleNewTask, descriptionNewTask, pickedDate, hour, frequencyNewTask);

                    TaskManager taskManager = new TaskManager(pickedDate, 0);

                    Toast.makeText(view.getContext().getApplicationContext(), "max id task" + taskManager.getMaxTaskId(), Toast.LENGTH_LONG).show();
                    taskManager.addTask(newTask);
                    Toast.makeText(view.getContext().getApplicationContext(), "max id task" + taskManager.getMaxTaskId(), Toast.LENGTH_LONG).show();
                    Toast.makeText(view.getContext().getApplicationContext(), "Task added successfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        //final TextView textView = binding.textDashboard;
        addTaskViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        return root;

    }

    //functions for the date pick
    private void initDatePicker(){
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
             month = month +1;
             String date = month + "/" + dayOfMonth + "/" + year;
             dateButton.setText(date);
             pickedDate = new Date(dayOfMonth, month, year, 0);
        }
    };


        //seting the calendar to todays date and styling
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener,year, month, day);



    }
    public void openDatePicker(View view){
        datePickerDialog.show();
    }



    //functions for hour pick


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }






}