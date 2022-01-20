package com.example.questcalendar.calendar;

import com.example.questcalendar.calendar.exceptions.MyException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {
    
    private final static String MONTHS[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final static int MAX_DAY_OF_MONTH[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    /* */
    private int dayOfMonth;
    private int month;
    private int year;
    private int dayOfWeek;

    private Calendar c;

    public Date(int day, int month, int year) throws MyException {

        this.c = Calendar.getInstance();
        this.c.set(year, month -1, day);

        dayOfMonth = day;
        this.month = month;
        this.year = year;
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);





        checkDayOfMonth(day, month, year);
        checkMonth(month);
        checkYear(year);
    }

    public Date() throws MyException {

       this.c = Calendar.getInstance();

        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        this.month = c.get(Calendar.MONTH) +1;
        this.year = c.get(Calendar.YEAR);
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        checkDayOfMonth(dayOfMonth, month, year);
        checkMonth(month);
        checkYear(year);
    }

    public String toString() {
        return getNameDayOfWeek() + " " + Integer.toString(dayOfMonth) + " " +  MONTHS[month-1] + " " +  Integer.toString(year);
    }

    public boolean isEqual(Date d) {
        return ( ( this.dayOfMonth == d.getDayOfMonth() )
                && ( this.month == d.getMonth() )
                && (this.year == d.getYear() )
        );
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    private String getNameDayOfWeek() {
        c.set(year, month -1, dayOfMonth);
        SimpleDateFormat dayOfWeekSDF = new SimpleDateFormat("EEE");
        return dayOfWeekSDF.format(c.getTime());
    }

    public static boolean IsLeap(int year) {
        if ( (year % 400) == 0) {
            return true;
        } else if ((year % 100) != 0) {
                return ( (year % 4) == 0 );
        } else {
            return false;
        }
    }

    public Date getNextDay() throws MyException {

        //the 28th of February in a leap year
        if (month == 2 && dayOfMonth == MAX_DAY_OF_MONTH[month- 1] && IsLeap(this.year)) {
            return new Date(29, 2, year);
        }

        //last day of the month
        if (dayOfMonth == MAX_DAY_OF_MONTH[month -1]) {

            //last day of the year
            if (month==12) {
                return new Date(1, 1, year+1);

            } else {
                return new Date(1, month +1, year);
            }

        } else {
            return new Date(dayOfMonth +1, month, year);
        }

    }

    public Date getPreviousDay() throws MyException {

        //the 1st of March in a leap year
        if (month == 3 && dayOfMonth == 1 && IsLeap(this.year)) {
            return new Date(29, 2, year);
        }

        //first day of the month
        if (dayOfMonth == 1) {

            //first day of the year
            if (month==1) {
                return new Date(31, 12, year -1);

            } else {
                return new Date(MAX_DAY_OF_MONTH[month -2], month -1, year);
            }

        } else {
            return new Date(dayOfMonth -1, month, year);
        }
    }

    private static void checkDayOfMonth(int day, int month, int year) throws MyException {

        //the 29th of February
        if (IsLeap(year) && (month == 2) && (day == 29)) {

        } else {
            if (day < 0) {
                throw new MyException("A day can't be negative.");
            } else if (day > MAX_DAY_OF_MONTH[month- 1]) {
                throw new MyException("This day belong to another month.");
            }
        }
    }

    private static void checkMonth(int month) throws MyException {
        if (month < 1 || month > 12) {
            throw new MyException("That's not a month.");
        }
    }

    private static void checkYear(int year) throws MyException {

        if (year < 0) {
            throw new MyException("A year can't be negative.");
        }
    }
}

