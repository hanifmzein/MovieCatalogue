package com.example.MovieCatalogue.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "videocatalogueAPP.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format(
            "CREATE TABLE "+DatabaseContract.TABLE_FAVORITE+" ("+
                    DatabaseContract.FavoriteColumns._ID +     " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseContract.FavoriteColumns.ID +      " INTEGER NOT NULL," +
                    DatabaseContract.FavoriteColumns.POSTER +  " TEXT," +
                    DatabaseContract.FavoriteColumns.TYPE +    " TEXT)"

    );


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE);
        onCreate(db);

    }

}