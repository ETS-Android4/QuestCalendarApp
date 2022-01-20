package com.example.questcalendar.ui.profile;

import static java.lang.Math.pow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.questcalendar.EditAvatarActivity;
import com.example.questcalendar.EditProfileActivity;
import com.example.questcalendar.NotificationsActivity;
import com.example.questcalendar.R;
import com.example.questcalendar.WelcomeActivity;
import com.example.questcalendar.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    public Button logout, notifications, achievements, editProfile, changeAvatar;
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    public TextView profileusername, profilelevel, profileExperience;
    public CircleImageView profilePic;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //getting the user uid
        String uid= mAuth.getCurrentUser().getUid();
        profileusername = root.findViewById(R.id.usernameProfile);
        profilelevel = root.findViewById(R.id.usernameLevel);
        profileExperience = root.findViewById(R.id.usernameExperience);
        profilePic = root.findViewById(R.id.profilePic);

        setUsername(uid, profileusername);
        setLevel(uid, profilelevel);
        setExperience(uid, profileExperience);
        setProfilePic(uid, profilePic);



        //edit
        editProfile = (Button) root.findViewById(R.id.edit_button);
        editProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                onEdit();

            }
        });


        //change avatar
        changeAvatar = root.findViewById(R.id.avatar_button);
        changeAvatar.setOnClickListener(v -> onEditAvatar());

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


    public void setUsername(String uid, TextView profileusername){

        DatabaseReference reference = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot user = dataSnapshot.child(uid);
                String usernameDB = user.child("username").getValue().toString();
                profileusername.setText(usernameDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    public void setLevel(String uid, TextView profilelevel){

        DatabaseReference reference = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot user = dataSnapshot.child(uid);
                String levelDB = user.child("level").getValue().toString();
                profilelevel.setText("lvl " +levelDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void setExperience(String uid, TextView profileExperience){

        DatabaseReference reference = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot user = dataSnapshot.child(uid);
                String levelDB = user.child("level").getValue().toString();
                int level = Integer.parseInt(levelDB);
                String experienceDB = user.child("experience").getValue().toString();
                int systemLevel = 50 + (int) pow(level, 2);
                profileExperience.setText("exp: "+ experienceDB + " / " + systemLevel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void onEdit(){
        Intent i = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(i);
    }

    private void onEditAvatar(){
        Intent i = new Intent(getActivity(), EditAvatarActivity.class);
        startActivity(i);
    }

    public void setProfilePic(String uid, CircleImageView profilePic){
        DatabaseReference reference = FirebaseDatabase.getInstance("https://questcalendar-c41e3-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot user = dataSnapshot.child(uid);
                String ppDB = user.child("profilePic").getValue().toString();
                int ppNumber = Integer.parseInt(ppDB);
                if(ppNumber == 1){
                    profilePic.setImageResource(R.mipmap.qc_icon_template_round);
                }else if(ppNumber==2){
                    profilePic.setImageResource(R.mipmap.qc_icon_2_round);
                }else if(ppNumber == 3){
                    profilePic.setImageResource(R.mipmap.qc_icon_3_round);
                }else if(ppNumber == 4){
                    profilePic.setImageResource(R.mipmap.qc_icon_4_round);
                }else{
                    profilePic.setImageResource(R.mipmap.qc_icon_template_round);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


}