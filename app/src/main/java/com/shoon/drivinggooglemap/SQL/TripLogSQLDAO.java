package com.shoon.drivinggooglemap.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.shoon.drivinggooglemap.PositionLog;

import java.util.ArrayList;


public class TripLogSQLDAO {
    private static SQLiteDatabase db;
    static PositionLog positionLogTemp;
    private static final LatLng INDY = new LatLng(39.85183, -86.106064);

    public static void initializeInstance(Context context) {
        if (db == null) {
            TripLogSQLHelper helper = new TripLogSQLHelper( context);
            db = helper.getWritableDatabase();
            positionLogTemp=new PositionLog(0,0, INDY );
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

    public static PositionLog  readAPositionLogs(int iID, int iSequene){
        if (iID==-1) return positionLogTemp;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TripLogSQLContract.TripLogSQL.TABLE_NAME +
                        "WHERE "+TripLogSQLContract.TripLogSQL.COLUMN_NAME_TRIPID+"="+iID+" AND "+
                        TripLogSQLContract.TripLogSQL.COLUMN_NAME_SERIALNUMBER+"="+iSequene,
                new String[]{});
        if (cursor==null)return positionLogTemp;
        PositionLog  pl=getPositionLogFromCursor( cursor);
        cursor.close();
        return pl;
    }

    public static void add(PositionLog positionLog){
        if (positionLog.getiTripID() <= 0) {
            positionLogTemp=positionLog;
            return;

        }
        ContentValues values = getContentValues(positionLog);


        final long insert = db.insert(TripLogSQLContract.TripLogSQL.TABLE_NAME, null, values);

        System.out.printf( String.valueOf( insert ) );
        return;
    }

    public void delete(PositionLog positionLog){

        int affectedRows = db.delete(TripLogSQLContract.TripLogSQL.TABLE_NAME,
                TripLogSQLContract.TripLogSQL._ID + "=?",
                new String[]{Integer.toString(positionLog.getiTripID())});

        System.out.printf( String.valueOf( affectedRows ) );
        return;
    }

    public static void update(PositionLog positionLog){
        int affectedRows = db.update(
                TripLogSQLContract.TripLogSQL.TABLE_NAME,
                getContentValues(positionLog),
                TripLogSQLContract.TripLogSQL._ID + "=?",  //id=1
                new String[]{Integer.toString(positionLog.getiTripID())});

    }

    private static ContentValues getContentValues(PositionLog positionLog) {
        ContentValues values = new ContentValues();
        values.put(TripLogSQLContract.TripLogSQL._ID, positionLog.getiTripID());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_TRIPID, positionLog.getiTripID());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_SERIALNUMBER, positionLog.getiSerialNumber());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_LATITUDE, positionLog.getdLatitude());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_LOGITUDE, positionLog.getdLongitude());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_BEARING, positionLog.getfBearing());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_TILT, positionLog.getfTilt());
        values.put(TripLogSQLContract.TripLogSQL.COLUMN_NAME_TIMESTAMP, positionLog.getdTimeStamp());
        return values;
    }

    private static PositionLog getPositionLogFromCursor(Cursor cursor) {
        int index = cursor.getColumnIndexOrThrow( TripLogSQLContract.TripLogSQL._ID);
        int id    = cursor.getInt(index);

        index = cursor.getColumnIndexOrThrow(TripLogSQLContract.TripLogSQL.COLUMN_NAME_TRIPID);
        int iID    = cursor.getInt(index);

        index = cursor.getColumnIndexOrThrow(TripLogSQLContract.TripLogSQL.COLUMN_NAME_SERIALNUMBER);
        int iserial    = cursor.getInt(index);

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


        return new PositionLog( iID, iserial,dLatitude,dLongitude,fBearing, fTilt, dTimeStamp);
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
