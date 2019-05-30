package com.shoon.drivinggooglemap;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.shoon.drivinggooglemap.PositionLog;
import com.shoon.drivinggooglemap.SQL.TripLogSQLDAO;

public class TripLogRepository {
    TripLogSQLDAO tripLogSQLDAO;

    public TripLogRepository(Context context) {
        this.tripLogSQLDAO = new TripLogSQLDAO( context );
    }

    public static MutableLiveData<PositionLog> getPosition(final int id, int i) {
        Log.i("Repository", "Retreiving Data");
        final MutableLiveData<PositionLog> positionLiveData = new MutableLiveData<>();

        PositionLog position = TripLogSQLDAO.readAPositionLogs( id,i);
        positionLiveData.setValue(position);

        return positionLiveData;
    }
}
