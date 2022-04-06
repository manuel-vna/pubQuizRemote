package com.example.pubquizremote.ui.roundTwo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoundTwoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RoundTwoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the round two fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}