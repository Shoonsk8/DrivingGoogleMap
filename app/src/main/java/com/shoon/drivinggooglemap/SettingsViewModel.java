package com.shoon.drivinggooglemap;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<Settings> settingsLiveData;



    public MutableLiveData<Settings> getSettings() {
        settingsLiveData =SettingRepository.getSettings();
        if(settingsLiveData==null){
            loadList();
        }
        return settingsLiveData;
    }


    public void set( MutableLiveData<Settings>  settingsMutableLiveData) {
        SettingRepository.set(settingsMutableLiveData);
    }

    public SettingsViewModel() {
        if(settingsLiveData==null){
            loadList();
        }
        // trigger user load.
    }

    void doAction() {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
    }
    private void loadList() {
        settingsLiveData = SettingRepository.getSettings();
    }
}
