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

import com.example.questcalendar.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthlyViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthlyViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private CalendarView monthlyView;
    private TextView selectedDay;
    private String[] tasks = {"prout at 8", "poop at 8", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "A2", "B2", "C2", "D2"};
    Intent intent;
    ListView listView;


    public MonthlyViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthlyViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthlyViewFragment newInstance(String param1, String param2) {
        MonthlyViewFragment fragment = new MonthlyViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly_view, container, false);


        // creer un adaptateur a partir d'un array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, tasks);
        // trouver la ListView
        listView = (ListView) view.findViewById(R.id.list_view);
        // afficher la liste des donnees dans la ListView
        listView.setAdapter(adapter);

        selectedDay = (TextView) view.findViewById(R.id.selected_day);
        monthlyView = (CalendarView) view.findViewById(R.id.monthly_view);
        monthlyView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String date = Integer.toString(dayOfMonth) + "." +  Integer.toString(month +1) + "." +  Integer.toString(year);
                selectedDay.setText(date);
            }
        });

        return view;
    }


}