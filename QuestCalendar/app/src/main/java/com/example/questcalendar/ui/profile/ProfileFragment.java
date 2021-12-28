package com.example.questcalendar.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.questcalendar.NotificationsActivity;
import com.example.questcalendar.R;
import com.example.questcalendar.WelcomeActivity;
import com.example.questcalendar.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    public Button logout, notifications, achievements, editProfile, changeAvatar;
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //logout
        logout = (Button) root.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                onLogout();

            }
        });


        //notifications
        notifications = (Button) root.findViewById(R.id.notifications_button);
        notifications.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                onNotifications();

            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void onLogout () {
        Intent i = new Intent(getActivity(), WelcomeActivity.class);
        startActivity(i);

    }

    private void onNotifications() {
        Intent i = new Intent(getActivity(), NotificationsActivity.class);
        startActivity(i);

    }





}