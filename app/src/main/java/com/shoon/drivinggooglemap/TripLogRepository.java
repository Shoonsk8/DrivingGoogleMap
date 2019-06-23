package com.shoon.drivinggooglemap;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.shoon.drivinggooglemap.PositionLog;
import com.shoon.drivinggooglemap.SQL.TripLogSQLDAO;

public class TripLogRepository {
    static TripLogSQLDAO tripLogSQLDAO;
    static PositionLog position;

    public TripLogRepository(Context context) {
        this.tripLogSQLDAO = new TripLogSQLDAO( context );
        position=new PositionLog( 0,0,0,0 ,0,0,0);

    }


    public static MutableLiveData<PositionLog> getCurrentPosition() {
        Log.i("Repository", "Retreiving Data");
        final MutableLiveData<PositionLog> positionLiveData = new MutableLiveData<>();

        positionLiveData.setValue(position);

        return positionLiveData;
    }

    public static MutableLiveData<PositionLog> getPosition(final int id, int i) {
        Log.i("Repository", "Retreiving Data");
        final MutableLiveData<PositionLog> positionLiveData = new MutableLiveData<>();

        position = TripLogSQLDAO.readAPositionLogs( id,i);
        positionLiveData.setValue(position);

        return positionLiveData;
    }
    public static void setPosition(MutableLiveData<PositionLog> positionLogMutableLiveData){
        position=positionLogMutableLiveData.getValue();
        TripLogSQLDAO.add(position);
    }
}
