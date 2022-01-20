package com.example.questcalendar;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserHelperClass {

    String username, email, password;
    int level, experience, profilePic;


    public UserHelperClass(String username, String email, String password, int level, int experience, int profilePic) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.level = level;
        this.experience = experience;
        this.profilePic = profilePic;
    }


    public UserHelperClass() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public  void levelUp(){
        int oldLevel = getLevel();
        setLevel(oldLevel +1);
    }

    public void gainExperience(int experience){
        int oldExp = getExperience();
        int total = oldExp + experience;
        if(total >= 50){
            int rest = total - 50;
            levelUp();
            setExperience(rest);
        }else{
            setExperience(total);
        }


    }

}
