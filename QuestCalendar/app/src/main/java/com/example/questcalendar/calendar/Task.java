package com.example.questcalendar.calendar;

import static android.graphics.Color.*;

import android.graphics.Color;
import android.graphics.ColorSpace;

import androidx.compose.ui.graphics.colorspace.ColorSpaces;

import com.example.questcalendar.calendar.exceptions.MyException;

/**
 *
 */
public class Task {

    //frequency of a task
    public final static int PUNCTUAL = 0;
    public final static int DAILY = 1;
    public final static int WEEKLY = 2;
    public final static int MONTHLY = 3;
    public final static int YEARLY = 4;

    public final static int MAX_FREQUENCY = 4;
    public final static int MIN_FREQUENCY = 0;

    private int id;
    private String title;
    private String description;
    private Date day;
    private int hour; //only round hour
    private int frequency;
    //private Color color;

    public Task(int nId, String nTitle, String nDescription, Date nDay, int nHour, int nFrequency) throws MyException {

        //to see later
        id = nId;

        title = nTitle;
        description = nDescription; //nothing to check ?

        //to check in Date
        day = nDay;
        hour = nHour;
        frequency = nFrequency;

        //color = new Color(WHITE);

        checkTitle(nTitle);
        checkHour(nHour);
        checkFrequency(nFrequency);

    }


    private static void checkTitle(String nTitle) throws MyException {
        if (nTitle.isEmpty()) {
            throw new MyException("The title of a task cannot be empty.");
        }
        //adding a max length ?

    }

    private static void checkHour(int nHour) throws MyException {
        if (nHour < 0 || nHour > 23) {
            throw new MyException("The hour of the task is wrong.");
        }
    }

    private static void checkFrequency(int nFrequency) throws MyException {
        if (nFrequency < MIN_FREQUENCY || nFrequency > MAX_FREQUENCY) {
            throw new MyException("This is not a frequency Igna :).");
        }
    }

    public int getFrequency() {
        return frequency;
    }

    public int getHour() {
        return hour;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Date getDay() {
        return day;
    }

    /*
    public Color getColor() {
        return color;
    }

     */

    public String toString() {
        return hour + "h: " + title;
    }
}
