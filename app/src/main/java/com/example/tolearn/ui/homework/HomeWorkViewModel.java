package com.example.tolearn.ui.homework;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeWorkViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeWorkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is homework fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}