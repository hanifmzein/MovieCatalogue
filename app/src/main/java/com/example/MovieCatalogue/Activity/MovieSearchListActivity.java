package com.example.MovieCatalogue.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.MovieCatalogue.Model.MovieItems;
import com.example.MovieCatalogue.Model.MovieViewModel;
import com.example.MovieCatalogue.R;
import com.example.MovieCatalogue.adapter.MovieListAdapter;

import java.util.ArrayList;

public class MovieSearchListActivity extends AppCompatActivity {
    RecyclerView rvMovie;
    ArrayList<MovieItems> list = new ArrayList<>();
    MovieViewModel movieViewModel;
    ProgressBar progressBar;
    MovieListAdapter movieAdapter;

    SearchView svMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_list);

        svMovie = findViewById(R.id.sv_tv_show);
        rvMovie = findViewById(R.id.rv_tv_show);

        //jika oreintasi berubah
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rvMovie.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
        else{
            rvMovie.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovie);

        svMovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // onChanged

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                rvMovie = findViewById(R.id.rv_tv_show);
                rvMovie.setHasFixedSize(true);

                progressBar = findViewById(R.id.progressBar);
                showLoading(true);



                movieAdapter = new MovieListAdapter(list);
                movieAdapter.notifyDataSetChanged();
                rvMovie.setAdapter(movieAdapter);

                String url = "https://api.themoviedb.org/3/search/movie?api_key=e23aa7d3680ad3a4c4332c8a51d7eec9&language=en-US&query="+query;
                String name = "results";

                movieViewModel.setMovie(url,name, getApplicationContext());

                return true;
            }
        });
    }


    private Observer<ArrayList<MovieItems>> getMovie = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(ArrayList<MovieItems> movieItems) {
            if (movieItems != null) {
                movieAdapter.setData(movieItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            rvMovie.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            rvMovie.setVisibility(View.VISIBLE);
        }
    }
}
