package com.example.MovieCatalogue.Widget;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.MovieCatalogue.Activity.FavoriteWidget;
import com.example.MovieCatalogue.R;
import com.example.MovieCatalogue.db.FavoriteHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.MovieCatalogue.db.DatabaseContract.CONTENT_URI_FAVORITE;
import static com.example.MovieCatalogue.db.DatabaseContract.FavoriteColumns.POSTER;


public class StackRemoteViewsFactory extends Application implements RemoteViewsService.RemoteViewsFactory  {

    FavoriteHelper favoriteHelper;

    //inisiasi listLinkImageFavorite

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDataSetChanged() {

        final long identityToken = Binder.clearCallingIdentity();

        Cursor cursor = mContext .getContentResolver()
                .query(CONTENT_URI_FAVORITE, null, null, null, null, null);

        cursor.moveToFirst();

        String url;

        for (int i=0; i< cursor.getCount(); i++){

            url = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            System.out.println("URL : "+url);
            cursor.moveToNext();

            mWidgetItems.add(getBitmap("https://image.tmdb.org/t/p/w500"+url));
        }

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private static Bitmap getBitmap(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}
