package com.shoon.drivinggooglemap;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TripLogViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<PositionLog> userLiveData = new MutableLiveData<>();

    public LiveData<PositionLog> getUser() {
        return userLiveData;
    }

    public TripLogViewModel() {
        // trigger user load.
    }

    void doAction() {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
    }
}




