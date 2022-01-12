package com.example.questcalendar.calendar;

import com.example.questcalendar.calendar.exceptions.MyException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {
    
    private final static String MONTHS[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final static String DAYS_OF_WEEK[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final static int MAX_DAY_OF_MONTH[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    /* */
    private int dayOfMonth;
    private int month;
    private int year;
    private int dayOfWeek;

    //set and get??
    //do the months' string ??

    public Date(int day, int month, int year, int d) throws MyException {

        //exception ??
        dayOfMonth = day;
        this.month = month;
        this.year = year;
        dayOfWeek = d;

        checkDayOfMonth(day, month, year);
        checkMonth(month);
        checkYear(year);
        checkDayOfWeek(d);
    }

    public Date() throws MyException {
        /*
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        SimpleDateFormat dayOfMonthSDF = new SimpleDateFormat("dd");
        String dayOfMonthString = dayOfMonthSDF.format(c.getTime());
        SimpleDateFormat dayOfWeekSDF = new SimpleDateFormat("EEE");
        String dayOfWeekString = dayOfWeekSDF.format(c.getTime());
        SimpleDateFormat monthSDF = new SimpleDateFormat("MM");
        String monthString = monthSDF.format(c.getTime());
        SimpleDateFormat yearSDF = new SimpleDateFormat("yyyy");
        String yearString = yearSDF.format(c.getTime());


        int nDayOfMonth = Integer.parseInt(dayOfMonthString);
        char nDayOfWeek = dayOfWeekString.charAt(0);
        int nMonth = Integer.parseInt(monthString);
        int nYear = Integer.parseInt(yearString);


        dayOfMonth = nDayOfMonth;
        this.month = nMonth;
        this.year = nYear;
        dayOfWeek = getDayOfWeekFromString(dayOfWeekString);

        checkDayOfMonth(nDayOfMonth, nMonth, nYear);
        checkMonth(nMonth);
        checkYear(nYear);
        checkDayOfWeek(getDayOfWeekFromString(dayOfWeekString));

         */

        Calendar c = Calendar.getInstance();

        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        this.month = c.get(Calendar.MONTH) +1;
        this.year = c.get(Calendar.YEAR);
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK) -1;

        checkDayOfMonth(dayOfMonth, month, year);
        checkMonth(month);
        checkYear(year);
        checkDayOfWeek(dayOfWeek);

    }

    public String toString() {
        return Integer.toString(dayOfMonth) + " " +  MONTHS[month-1] + " " +  Integer.toString(year);
        //return DAYS_OF_WEEK[dayOfWeek] + " " + Integer.toString(dayOfMonth) + " " +  MONTHS[month-1] + " " +  Integer.toString(year);
    }

    public boolean isEqual(Date d) {
        return ( ( this.dayOfMonth == d.getDayOfMonth() )
                && ( this.month == d.getMonth() )
                && (this.year == d.getYear() )
        );
    }

    public int getDayOfWeek() {
        return dayOfWeek;
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

    private static int getDayOfWeekFromString(String d) {
        if (d.equals("Mon")) {
            return 0;
        } else if (d.equals("Tue")) {
            return 1;
        } else if (d.equals("Wed")) {
            return 2;
        } else if (d.equals("Thu")) {
            return 3;
        } else if (d.equals("Fri")) {
            return 4;
        } else if (d.equals("Sat")) {
            return 5;
        } else if (d.equals("Sun")) {
            return 6;
        } else {
            throw new MyException("Pb with the date from Calendar");
        }
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

        int nextDayOfWeek = ((dayOfWeek +1) % 7);

        //the 28th of February in a leap year
        if (month == 2 && dayOfMonth == MAX_DAY_OF_MONTH[month- 1] && IsLeap(this.year)) {
            return new Date(29, 2, year, nextDayOfWeek);
        }

        //last day of the month
        if (dayOfMonth == MAX_DAY_OF_MONTH[month -1]) {

            //last day of the year
            if (month==12) {
                return new Date(1, 1, year+1, nextDayOfWeek);

            } else {
                return new Date(1, month +1, year, nextDayOfWeek);
            }

        } else {
            return new Date(dayOfMonth +1, month, year, nextDayOfWeek);
        }

    }

    public Date getPreviousDay() throws MyException {

        int previousDayOfWeek = ((dayOfWeek +6) % 7);

        //the 1st of March in a leap year
        if (month == 3 && dayOfMonth == 1 && IsLeap(this.year)) {
            return new Date(29, 2, year, previousDayOfWeek);
        }

        //first day of the month
        if (dayOfMonth == 1) {

            //first day of the year
            if (month==1) {
                return new Date(31, 12, year -1, previousDayOfWeek);

            } else {
                return new Date(MAX_DAY_OF_MONTH[month -2], month -1, year, previousDayOfWeek);
            }

        } else {
            return new Date(dayOfMonth -1, month, year, previousDayOfWeek);
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

    private static void checkDayOfWeek(int day) throws MyException {
        if (day < 0 || day > 6) {
            throw new MyException("That's not the name of a day of the week: " + day);
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

