package com.example.questcalendar.calendar.exceptions;

public class MyException extends RuntimeException {

    private String message;

    public MyException() {
        super();
    }

    public MyException(String msg) {
        super(msg);
        message = msg;
    }
}
