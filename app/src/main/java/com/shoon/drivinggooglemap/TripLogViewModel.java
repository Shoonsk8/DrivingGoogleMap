package com.shoon.drivinggooglemap;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TripLogViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<PositionLog> positionLiveData;
    private static PositionLog position;

    public MutableLiveData<PositionLog> getPosition(int iID,int iS) {
        positionLiveData = TripLogRepository.getPosition( iID,iS);
        if(positionLiveData==null){
            loadList();
        }
        return positionLiveData;
    }

    public MutableLiveData<PositionLog> getCurrentPosition() {
        positionLiveData = TripLogRepository.getCurrentPosition();
        if(positionLiveData==null){
            loadList();
        }
        return positionLiveData;
    }
    public void setPostion(MutableLiveData<PositionLog> positionLiveData){


        TripLogRepository.setPosition( positionLiveData);
    }

    public TripLogViewModel() {
         if(positionLiveData==null){
            loadList();

        }

        // trigger user load.
    }

    void doAction() {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
    }
    private void loadList() {
        positionLiveData = TripLogRepository.getPosition( -1,0);//-1 for dummy mode
    }
}




