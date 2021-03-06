package com.example.MovieCatalogue.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContentValues;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.MovieCatalogue.Model.TvShowItems;
import com.example.MovieCatalogue.Model.TvShowViewModel;
import com.example.MovieCatalogue.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.MovieCatalogue.db.DatabaseContract.CONTENT_URI_FAVORITE;
import static com.example.MovieCatalogue.db.DatabaseContract.FavoriteColumns.ID;
import static com.example.MovieCatalogue.db.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.MovieCatalogue.db.DatabaseContract.FavoriteColumns.TYPE;

public class TvShowDetailActivity extends AppCompatActivity {
        ConstraintLayout csLayout;
        ImageView imgBackdrop, imgPoster, imgFavorite, imgFavoriteOff;
        TextView tvVote, tvOverview, tvTitle, tvRelease;
        RatingBar rbVote;
        Toolbar toolbar;
        ProgressBar progressBar;


        TvShowViewModel tvShowViewModel;

        public static final String EXTRA_NAME = "id_tvshow";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tv_show_detail);

            imgBackdrop = findViewById(R.id.img_backdrop);
            imgPoster = findViewById(R.id.img_poster);
            tvTitle = findViewById(R.id.tv_title);
            tvVote = findViewById(R.id.tv_vote);
            tvOverview = findViewById(R.id.tv_overview);
            tvRelease = findViewById(R.id.tv_release);
            rbVote = findViewById(R.id.rb_score);
            imgFavorite = findViewById(R.id.img_favorite);
            imgFavoriteOff = findViewById(R.id.img_favorite_off);


            toolbar = findViewById(R.id.toolbar_detail);

            progressBar = findViewById(R.id.progressBar);
            csLayout = findViewById(R.id.cs_isi_layout);
            showLoading(true);

            Bundle bundle = this.getIntent().getExtras();
            int id = bundle.getInt(EXTRA_NAME);

            tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
            tvShowViewModel.getTvShows().observe(this, getTvShow);

            String url = "https://api.themoviedb.org/3/tv/"+id+"?api_key=e23aa7d3680ad3a4c4332c8a51d7eec9&language=en-US";
            String name = "0";

            tvShowViewModel.setTvShow(url,name, this);

            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

                ViewGroup.LayoutParams layoutParams = tvTitle.getLayoutParams();
                layoutParams.width = convertDpToPx(223);
                tvTitle.setLayoutParams(layoutParams);

                layoutParams = tvOverview.getLayoutParams();
                layoutParams.width = convertDpToPx(371);
                tvOverview.setLayoutParams(layoutParams);

            }
            else{
                ViewGroup.LayoutParams layoutParams = tvTitle.getLayoutParams();
                layoutParams.width = convertDpToPx(373);
                tvTitle.setLayoutParams(layoutParams);

                layoutParams = tvOverview.getLayoutParams();
                layoutParams.width = convertDpToPx(643);
                tvOverview.setLayoutParams(layoutParams);
            }
        }

        public int convertDpToPx(int dp) {
            return (int) (dp * this.getResources().getDisplayMetrics().density);
        }

        private void setDetail(ArrayList<TvShowItems> tvShowItems){

            final TvShowItems tvShow = tvShowItems.get(0);


            String title = tvShow.getTitle()+" ("+tvShow.getRelease().substring(0,4)+")";
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
            tvTitle.setText(title);
            tvVote.setText(tvShow.getVote());
            tvOverview.setText(tvShow.getOverview());

            imgFavoriteOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ContentValues values = new ContentValues();
                    values.put(ID,tvShow.getId());
                    values.put(POSTER,tvShow.getPoster());
                    values.put(TYPE,"tvshow");
                    getContentResolver().insert(CONTENT_URI_FAVORITE, values);

                    imgFavorite.setVisibility(View.VISIBLE);

                    String text = tvShow.getTitle()+" "+getString(R.string.add_favorite);
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

            imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri uriWithId = Uri.parse(CONTENT_URI_FAVORITE + "/" + tvShow.getId());
                    getContentResolver().delete(uriWithId, null, null);
                    imgFavorite.setVisibility(View.INVISIBLE);

                    String text = tvShow.getTitle()+" "+getString(R.string.removed_favorite);
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

            if (tvShow.getFavorite()) {
                imgFavorite.setVisibility(View.VISIBLE);
            } else {
                imgFavorite.setVisibility(View.INVISIBLE);
            }

            Date date= null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(tvShow.getRelease());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, yyyy");
            String tgl = dateFormatter.format(date);

            tvRelease.setText(tgl);

            float f = Float.valueOf(tvShow.getVote().trim())/2;
            rbVote.setRating(f);

            Glide.with(this)
                    .load("http://image.tmdb.org/t/p/w500"+tvShow.getBackdrop())
                    .into(imgBackdrop);
            Glide.with(this)
                    .load("http://image.tmdb.org/t/p/w500"+tvShow.getPoster())
                    .into(imgPoster);
        }


        private Observer<ArrayList<TvShowItems>> getTvShow = new Observer<ArrayList<TvShowItems>>() {
            @Override
            public void onChanged(ArrayList<TvShowItems> tvShowItems) {
                if (tvShowItems != null) {
                    setDetail(tvShowItems);

                    showLoading(false);
                }
            }
        };

        private void showLoading(Boolean state) {

            if (state) {
                progressBar.setVisibility(View.VISIBLE);
                csLayout.setVisibility(View.INVISIBLE);

            } else {
                csLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }

}