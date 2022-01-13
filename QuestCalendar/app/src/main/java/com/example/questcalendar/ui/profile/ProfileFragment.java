package com.example.questcalendar.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.questcalendar.NotificationsActivity;
import com.example.questcalendar.R;
import com.example.questcalendar.WelcomeActivity;
import com.example.questcalendar.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    public Button logout, notifications, achievements, editProfile, changeAvatar;
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    public TextView profileusername;
    FirebaseAuth mAuth;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //receiving data of user
        //Bundle bundle = this.getArguments();
        //String username = bundle.getString("username");
        //String email = bundle.getString("email");
        //String password = bundle.getString("password");

        //set username as username
        //profileusername = root.findViewById(R.id.usernameProfile);
        //profileusername.setText(username);


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
        if(mAuth == null){
            getActivity().finish();
            onWelcome();
        }else{
            getActivity().finish();
            mAuth.signOut();
            onWelcome();
        }


    }

    private void onNotifications() {
        Intent i = new Intent(getActivity(), NotificationsActivity.class);
        startActivity(i);

    }

    private void onWelcome(){
        Intent i = new Intent(getActivity(), WelcomeActivity.class);
        startActivity(i);
    }





}