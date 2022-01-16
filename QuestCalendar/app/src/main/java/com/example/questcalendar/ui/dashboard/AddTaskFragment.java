package com.example.questcalendar.ui.dashboard;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.questcalendar.MainActivity;
import com.example.questcalendar.R;
import com.example.questcalendar.databinding.FragmentAddTaskBinding;

import java.sql.Time;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    private Button dateButton, hourButton;
    int hour, minutes;
    private AddTaskViewModel addTaskViewModel;
    private FragmentAddTaskBinding binding;
    private Button daily, monthly, addTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addTaskViewModel =
                new ViewModelProvider(this).get(AddTaskViewModel.class);

        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
                        hourButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
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