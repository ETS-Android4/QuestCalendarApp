package com.example.questcalendar.ui.home;

import android.content.Intent;
import android.os.Bundle;
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

    private CalendarViewModel homeViewModel;
    private FragmentCalendarBinding binding;
    Button dailyQuest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Go to daily quest
        dailyQuest = (Button) root.findViewById(R.id.daily_quest_button);
        dailyQuest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onDailyQuest();
            }
        });

        //to use a fragment in a fragment
        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frequency_view_fragment, MonthlyViewFragment.class, null)
                .commit();

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