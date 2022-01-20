package com.example.questcalendar.calendar;

import java.util.Comparator;

//comparing two tasks by using the hour
public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task t0, Task t1) {
        int h0 = t0.getHour();
        int h1 = t1.getHour();
        if (h0 < h1) {
            return -1;
        } else if (h0 == h1) {
            return 0;
        } else {
            return 1;
        }
    }
}
