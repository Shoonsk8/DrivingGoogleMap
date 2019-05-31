package com.shoon.drivinggooglemap;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

public class SettingRepository {
    private static Settings settings;
    private static Context context;

    public SettingRepository(Context context) {
        this.context=context;
        settings=new Settings();
        settings.lDulationAnimateTo=5;
    }

    public static MutableLiveData<Settings> getSettings() {
        Log.i("Repository", "Retreiving Setting Data");
        final MutableLiveData<Settings> settingsMutableLiveData = new MutableLiveData<>();

        settingsMutableLiveData.setValue(settings  );

        return settingsMutableLiveData;
    }
    public static void set(MutableLiveData<Settings> settingsMutableLiveData){
        settings= settingsMutableLiveData.getValue();

    }
}
