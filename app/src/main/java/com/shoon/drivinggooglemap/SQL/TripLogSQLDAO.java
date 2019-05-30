package com.shoon.drivinggooglemap.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shoon.drivinggooglemap.PositionLog;

import java.util.ArrayList;


public class TripLogSQLDAO {
    private static SQLiteDatabase db;

    public static void initializeInstance(Context context) {
        if (db == null) {
            TripLogSQLHelper helper = new TripLogSQLHelper( context);
            db = helper.getWritableDatabase();
        }
    }

    public TripLogSQLDAO(Context context) {
        TripLogSQLHelper dbHelper=new TripLogSQLHelper( context );
        db=dbHelper.getWritableDatabase();

    }
    public TripLogSQLDAO(SQLiteDatabase db) {
        this.db = db;
    }
    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public ArrayList<PositionLog> getAllSQL(){
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TripLogSQLContract.TripLogSQL.TABLE_NAME,
                new String[]{});
        ArrayList<PositionLog>  ml=new ArrayList<PositionLog> (  );

        while (cursor.moveToNext()) {
            ml.add(getPositionLogFromCursor( cursor));
        }
        cursor.close();
        return ml;
    }

    public static ArrayList<PositionLog>  readAllPositionLogs(){
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TripLogSQLContract.TripLogSQL.TABLE_NAME,
                new String[]{});
        ArrayList<PositionLog>  ml=new ArrayList<PositionLog> (  );

        while (cursor.moveToNext()) {
            ml.add(getPositionLogFromCursor( cursor));
        }
        cursor.close();
        return ml;
    }

    public static void add(PositionLog PositionLog){
        ContentValues values = getContentValues(PositionLog);


        final long insert = db.insert(TripLogSQLContract.TripLogSQL.TABLE_NAME, null, values);

        System.out.printf( String.valueOf( insert ) );
        return;
    }

    public void delete(PositionLog PositionLog){

        int affectedRows = db.delete(TripLogSQLContract.TripLogSQL.TABLE_NAME,
                TripLogSQLContract.TripLogSQL._ID + "=?",
                new String[]{Integer.toString(PositionLog.getiTripID())});

        System.out.printf( String.valueOf( affectedRows ) );
        return;
    }

    public static void update(PositionLog PositionLog){
        int affectedRows = db.update(
                TripLogSQLContract.TripLogSQL.TABLE_NAME,
                getContentValues(PositionLog),
                TripLogSQLContract.TripLogSQL._ID + "=?",  //id=1
                new String[]{Integer.toString(PositionLog.getiTripID())});

    }

    private static ContentValues getContentValues(PositionLog PositionLog) {
        ContentValues values = new ContentValues();
        values.put(TripLogSQLContract.TripLogSQL._ID, PositionLog.getiTripID());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_SERIALNUMBER, PositionLog.getiSerialNumber());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_LATITUDE, PositionLog.getdLatitude());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_LOGITUDE, PositionLog.getdLongitude());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_BEARING, PositionLog.getfBearing());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_TILT, PositionLog.getfTilt());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_TIMESTAMP, PositionLog.getdTimeStamp());
        return values;
    }

    private static PositionLog getPositionLogFromCursor(Cursor cursor) {
        int index = cursor.getColumnIndexOrThrow( TripLogSQLContract.TripLogSQL._ID);
        int id    = cursor.getInt(index);

        index = cursor.getColumnIndexOrThrow(TripLogSQLContract.TripLogSQL.COLUMN_NAME_SERIALNUMBER);
        int iserial    = cursor.getInt(index);
        String strTitle = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(TripLogSQLContract.TripLogSQL.COLUMN_NAME_LATITUDE);
        double dLatitude = cursor.getDouble( index);

        index = cursor.getColumnIndexOrThrow(TripLogSQLContract.TripLogSQL.COLUMN_NAME_LOGITUDE);
        double dLongitude = cursor.getDouble( index);


        index = cursor.getColumnIndexOrThrow(TripLogSQLContract.TripLogSQL.COLUMN_NAME_BEARING);
        float fBearing = cursor.getFloat(  index);

        index = cursor.getColumnIndexOrThrow(TripLogSQLContract.TripLogSQL.COLUMN_NAME_TILT);
        float fTilt = cursor.getFloat(  index);

        index = cursor.getColumnIndexOrThrow(TripLogSQLContract.TripLogSQL.COLUMN_NAME_TIMESTAMP);
        double dTimeStamp = cursor.getDouble( index);


        return new PositionLog( id, iserial,dLatitude,dLongitude,fBearing, fTilt, dTimeStamp);
    }


    public static ArrayList<PositionLog>  updateCache(ArrayList<PositionLog>  logs) {
        // read all notes
        final ArrayList<PositionLog>  cacheNotes = readAllPositionLogs();

        // check each note
        for(PositionLog positionLog: logs) {
            boolean noteFound = false;
            for(PositionLog cacheNote: logs) {
                if(positionLog.getiTripID()==cacheNote.getiTripID()) {
                        update(positionLog);
                }
            }
            if(!noteFound) {
                // if note doesn't exist in cache, add it
                add(positionLog);
            }
        }

        return readAllPositionLogs();
    }
}
