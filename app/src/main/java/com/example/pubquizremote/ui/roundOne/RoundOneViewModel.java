package com.example.pubquizremote.ui.roundOne;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoundOneViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RoundOneViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the round one fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}