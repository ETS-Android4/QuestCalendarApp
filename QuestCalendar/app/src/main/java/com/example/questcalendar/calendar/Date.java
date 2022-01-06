package com.example.questcalendar.calendar;

public class Date {

    private int dayOfMonth;
    private int month;
    private int year;
    private char dayOfWeek;

    //set and get??
    //do the months' string ??

    public Date(int day, int month, int year, char d) {

        //exception ??
        dayOfMonth = day;
        this.month = month;
        this.year = year;
        dayOfWeek = d;
    }

    public String toString() {
        return dayOfWeek + " " + Integer.toString(dayOfMonth) + "." +  Integer.toString(month +1) + "." +  Integer.toString(year);
    }
}
