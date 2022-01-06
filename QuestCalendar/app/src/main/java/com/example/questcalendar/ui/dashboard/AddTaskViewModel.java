package com.example.questcalendar.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is add task fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}