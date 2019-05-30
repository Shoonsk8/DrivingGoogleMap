package com.shoon.drivinggooglemap.SQL;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;


public class TripLogSQLHelper extends SQLiteOpenHelper {
    private static final int    DATABASE_VERSION = 0;
    private static final String DATABASE_NAME    = "FavoriteMovieSQL.db";

    public TripLogSQLHelper(Context context) {
        super( context, DATABASE_NAME,null,DATABASE_VERSION );

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( TripLogSQLContract.TripLogSQL.SQL_CREATE_TABLE );
    }


    public TripLogSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super( context, name, factory, version );
    }

    public TripLogSQLHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super( context, name, factory, version, errorHandler );
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public TripLogSQLHelper(Context context, String name, int version, SQLiteDatabase.OpenParams openParams) {
        super( context, name, version, openParams );
    }



    /*    @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL( FavoriteMovieSQLContract.MovieFavorite.SQL_CREATE_TABLE );
        }
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( TripLogSQLContract.TripLogSQL.SQL_DELETE_TABLE );
        onCreate( db );
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade( db, oldVersion, newVersion );

    }

}
