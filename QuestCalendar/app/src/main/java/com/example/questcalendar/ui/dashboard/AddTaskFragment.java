package com.example.questcalendar.ui.dashboard;

import static com.example.questcalendar.ui.dashboard.TaskManager.QUEST_CALENDAR_LINK;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
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

    ///get the current logged user
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;

    private static final String ADD_TASK_ID = "0";

    private TaskHelper newTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //to manage the fragement
        addTaskViewModel =
                new ViewModelProvider(this).get(AddTaskViewModel.class);

        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //link to the database
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance(QUEST_CALENDAR_LINK).getReference(TaskManager.USERS).child(mUser.getUid());

        //to add a task, using the right ID
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }


            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String maxID = dataSnapshot.getValue(String.class);
                newTask.setId(maxID);
                reference.child(TaskManager.TASKS).child(maxID).setValue(newTask);
                int newMaxID = Integer.parseInt(maxID) +1;
                reference.child("maxID").setValue(Integer.toString(newMaxID));

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });






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
                        hourButton.setText(Integer.toString(hourOfDay) + "h");
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


                //to validate
                String titleNewTask = title.getEditText().getText().toString();

                String descriptionNewTask = description.getEditText().getText().toString();



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
                } else if (titleNewTask.length() < 3) {
                    title.setError("Title cannot be less than 3 characters");
                    taskValidated = 0;
                } else if (titleNewTask.length() > 25) {
                    title.setError("Title cannot be more than 25 characters");
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

                    newTask = new TaskHelper(ADD_TASK_ID, titleNewTask, descriptionNewTask,
                            Integer.toString(hour), Integer.toString(frequencyNewTask),
                            Integer.toString(pickedDate.getDayOfMonth()), Integer.toString(pickedDate.getMonth()),
                            Integer.toString(pickedDate.getYear()), "0");






                    reference.child("maxID").removeValue();

                    title.getEditText().setText("");
                    description.getEditText().setText("");

                    Toast.makeText(getContext().getApplicationContext(), "Task added successfully", Toast.LENGTH_LONG).show();

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
             pickedDate = new Date(dayOfMonth, month, year);
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