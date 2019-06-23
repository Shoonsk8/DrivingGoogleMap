package com.shoon.drivinggooglemap.SQL;

import android.provider.BaseColumns;

public class TripLogSQLContract {
    public static class TripLogSQL implements BaseColumns {
        public static final String  TABLE_NAME        = "triplog",
                COLUMN_NAME_TRIPID = "tripid",
                COLUMN_NAME_SERIALNUMBER = "serial",
                COLUMN_NAME_LATITUDE="latitude",
                COLUMN_NAME_LOGITUDE = "longitude",
                COLUMN_NAME_BEARING = "bearing",
                COLUMN_NAME_TILT = "tilt",
                COLUMN_NAME_TIMESTAMP = "timestamp"    ;


        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + " ( " +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_TRIPID+ " INTEGER," +
                COLUMN_NAME_SERIALNUMBER+ " INTEGER," +
                COLUMN_NAME_LATITUDE+ " DOUBLE," +
                COLUMN_NAME_LOGITUDE+ " DOUBLE," +
                COLUMN_NAME_BEARING+ " FLOAT," +
                COLUMN_NAME_TILT+ " FLOAT," +
                COLUMN_NAME_TIMESTAMP+ " DOUBLE"+
                "  );";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
