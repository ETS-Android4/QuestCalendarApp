package com.example.questcalendar.ui.dashboard;

import com.example.questcalendar.calendar.Date;

/*
Helper class to register tasks in the database
 */
public class TaskHelper {

    String id, title, description, hour, frequency, day, month, year, done;

    public TaskHelper() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public TaskHelper(String id, String title, String description, String hour, String frequency, String day, String month, String year, String done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.hour = hour;
        this.frequency = frequency;
        this.day = day;
        this.month = month;
        this.year = year;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getHour() {
        return hour;
    }

    public String getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getDone() {
        return done;
    }
}