package com.example.MovieCatalogue.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.mysubmission5";
    private static final String SCHEME = "content";
    
    
    public static String TABLE_FAVORITE = "favorite";
    public static final class FavoriteColumns implements BaseColumns {
        public static String ID = "id";
        public static String POSTER = "poster";
        public static String TYPE = "type";
    }

    public static final Uri CONTENT_URI_FAVORITE = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

}