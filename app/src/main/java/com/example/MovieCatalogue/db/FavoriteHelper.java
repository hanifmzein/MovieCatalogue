package com.example.MovieCatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.MovieCatalogue.Model.Favorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.MovieCatalogue.db.DatabaseContract.FavoriteColumns.ID;
import static com.example.MovieCatalogue.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.MovieCatalogue.db.DatabaseContract.FavoriteColumns.TYPE;
import static com.example.MovieCatalogue.db.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {

    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }


    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Favorite> getAllFavorite() {
        ArrayList<Favorite> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();

        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {
                favorite = new Favorite();
                favorite.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                favorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favorite.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                arrayList.add(favorite);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(ContentValues values){

        return database.insert(DATABASE_TABLE,null, values);
    }

    public int deleteById(String id){
        return database.delete(DATABASE_TABLE, ID + " = " + id , null);
    }

    public boolean isFavorite(int id){
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM "+DATABASE_TABLE+" WHERE "+ID+"="+ id, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return false;
        } else {
            return true;
        }
    }

    public Cursor queryAll() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

}
