package com.example.MovieCatalogue.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieCatalogue.Model.TvShowItems;
import com.example.MovieCatalogue.Model.TvShowViewModel;
import com.example.MovieCatalogue.R;
import com.example.MovieCatalogue.adapter.TvShowFavoriteListAdapter;

import java.util.ArrayList;

public class TvShowFavoriteListFragment extends Fragment {

    RecyclerView rvTvShow;
    ArrayList<TvShowItems> list = new ArrayList<>();
    TvShowViewModel tvShowViewModel;
    ProgressBar progressBar;
    TvShowFavoriteListAdapter tvShowListAdapter;

    public TvShowFavoriteListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);


        rvTvShow = view.findViewById(R.id.rv_tv_show);
        rvTvShow.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShows().observe(this, getTvShow);


        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rvTvShow.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        else{
            rvTvShow.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        tvShowListAdapter = new TvShowFavoriteListAdapter(list);
        tvShowListAdapter.notifyDataSetChanged();
        rvTvShow.setAdapter(tvShowListAdapter);

        String url = "https://api.themoviedb.org/3/tv/popular?api_key=e23aa7d3680ad3a4c4332c8a51d7eec9&language=en-US&page=1";
        String name = "results";
        tvShowViewModel.setTvShow(url,name, getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Observer<ArrayList<TvShowItems>> getTvShow = new Observer<ArrayList<TvShowItems>>() {
        @Override
        public void onChanged(ArrayList<TvShowItems> tvShowItems) {
            if (tvShowItems != null) {
                tvShowListAdapter.setData(tvShowItems);
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
