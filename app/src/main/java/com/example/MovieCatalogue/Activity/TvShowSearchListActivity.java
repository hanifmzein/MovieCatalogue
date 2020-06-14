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

import com.example.MovieCatalogue.Model.TvShowItems;
import com.example.MovieCatalogue.Model.TvShowViewModel;
import com.example.MovieCatalogue.R;
import com.example.MovieCatalogue.adapter.TvShowListAdapter;

import java.util.ArrayList;

public class TvShowSearchListActivity extends AppCompatActivity {
    RecyclerView rvTvShow;
    ArrayList<TvShowItems> list = new ArrayList<>();
    TvShowViewModel tvShowViewModel;
    ProgressBar progressBar;
    TvShowListAdapter tvShowAdapter;

    SearchView svTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_search_list);


        svTvShow = findViewById(R.id.sv_tv_show);
        rvTvShow = findViewById(R.id.rv_tv_show);


        //jika oreintasi berubah
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rvTvShow.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
        else{
            rvTvShow.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShows().observe(this, getTvShow);

        svTvShow.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // onChanged

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                rvTvShow = findViewById(R.id.rv_tv_show);
                rvTvShow.setHasFixedSize(true);

                progressBar = findViewById(R.id.progressBar);
                showLoading(true);



                tvShowAdapter = new TvShowListAdapter(list);
                tvShowAdapter.notifyDataSetChanged();
                rvTvShow.setAdapter(tvShowAdapter);

                String url = "https://api.themoviedb.org/3/search/tv?api_key=e23aa7d3680ad3a4c4332c8a51d7eec9&language=en-US&query="+query;
                String name = "results";

                tvShowViewModel.setTvShow(url,name, getApplicationContext());

                return true;
            }
        });

    }


    private Observer<ArrayList<TvShowItems>> getTvShow = new Observer<ArrayList<TvShowItems>>() {
        @Override
        public void onChanged(ArrayList<TvShowItems> tvShowItems) {
            if (tvShowItems != null) {
                tvShowAdapter.setData(tvShowItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            rvTvShow.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            rvTvShow.setVisibility(View.VISIBLE);
        }
    }
}
