package com.example.MovieCatalogue.Model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.MovieCatalogue.db.FavoriteHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieViewModel extends ViewModel {

    FavoriteHelper favoriteHelper;

    public ArrayList<Favorite> listFavorite;
    public ArrayList<MovieItems> listMovie;

    private MutableLiveData<ArrayList<MovieItems>> listMovies = new MutableLiveData<>();

    public void setMovie(String url, final String name, Context context) {

        AsyncHttpClient client = new AsyncHttpClient();

        favoriteHelper = new FavoriteHelper(context);
        favoriteHelper.open();
        listFavorite = favoriteHelper.getAllFavorite();

        final ArrayList<MovieItems> listItems = new ArrayList<>();

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    JSONArray list;


                    if (name == "0") {
                        list = new JSONArray("["+result+"]");
                    } else {
                        list = responseObject.getJSONArray(name);
                    }


                    for (int i = 0; i < list.length(); i++) {

                        JSONObject movie = list.getJSONObject(i);

                        MovieItems movieItems = new MovieItems(movie);

                        if (favoriteHelper.isFavorite(movieItems.getId())){
                            movieItems.setFavorite(true);
                        } else {
                            movieItems.setFavorite(false);
                        }

                        listItems.add(movieItems);
                    }

                    listMovie = listItems;
                    listMovies.postValue(listItems);

//                    favoriteHelper.close();
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MovieItems>> getMovies() {
        return listMovies;
    }

}
