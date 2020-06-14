package com.example.MovieCatalogue.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.MovieCatalogue.db.FavoriteHelper;

import static com.example.MovieCatalogue.db.DatabaseContract.AUTHORITY;
import static com.example.MovieCatalogue.db.DatabaseContract.CONTENT_URI_FAVORITE;
import static com.example.MovieCatalogue.db.DatabaseContract.TABLE_FAVORITE;

public class FavoriteProvider extends ContentProvider {


    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private FavoriteHelper favoriteHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.example.mysubmission5/movie
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, FAVORITE);
        // content://com.example.mysubmission5/movie/id
        sUriMatcher.addURI(AUTHORITY,
                TABLE_FAVORITE + "/#",
                FAVORITE_ID);
    }

    public FavoriteProvider() {
    }

    @Override
    public boolean onCreate() {
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE:
                cursor = favoriteHelper.queryAll();
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE:
                added = favoriteHelper.insert(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_FAVORITE, null);
        return Uri.parse(CONTENT_URI_FAVORITE + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID:
                deleted = favoriteHelper.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_FAVORITE, null);
        return deleted;
    }
}
