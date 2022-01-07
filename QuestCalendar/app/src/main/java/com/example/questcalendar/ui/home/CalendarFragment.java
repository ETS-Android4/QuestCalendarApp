package com.example.questcalendar.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.questcalendar.DailyQuestActivity;
import com.example.questcalendar.R;
import com.example.questcalendar.WelcomeActivity;
import com.example.questcalendar.databinding.FragmentCalendarBinding;

public class CalendarFragment extends Fragment {

    public final static int MONTHLY_VIEW_STATE = 0;
    public final static int DAILY_VIEW_STATE = 1;

    private int viewState;
    private Button monthly;
    private Button daily;
    private CalendarViewModel homeViewModel;
    private FragmentCalendarBinding binding;
    Button dailyQuest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Display the monthly view
        viewState = MONTHLY_VIEW_STATE;
        //to use a fragment in a fragment
        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frequency_view_fragment, MonthlyViewFragment.class, null)
                .commit();

        //Go to daily view

        daily = (Button) root.findViewById(R.id.daily_view_button);
        daily.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (viewState==MONTHLY_VIEW_STATE) {

                    //if (savedInstanceState == null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("day_of_month", 6);
                    bundle.putInt("month", 1);
                    bundle.putInt("year", 2022);
                    bundle.putChar("day_of_weak", 'T');

                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frequency_view_fragment, DailyViewFragment.class, bundle)
                            .commit();
                    //}
                }
            }
        });

        monthly = (Button) root.findViewById(R.id.monthly_view_button);
        monthly.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {



                    getChildFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.frequency_view_fragment, MonthlyViewFragment.class, null)
                            .commit();


            }
        });


        //Go to daily quest
        dailyQuest = (Button) root.findViewById(R.id.daily_quest_button);
        dailyQuest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onDailyQuest();
                dailyQuest.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        dailyQuest.setEnabled(true);

                    }
                },86400000);// set time as per your requirement
            }
        });

        /*
        //Monthly


         */


        //Daily
        //to use a fragment in a fragment


        return root;






    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void onDailyQuest () {
        Intent i = new Intent(getActivity(), DailyQuestActivity.class);
        startActivity(i);

    }
}