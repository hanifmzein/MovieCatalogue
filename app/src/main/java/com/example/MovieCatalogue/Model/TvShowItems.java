package com.example.MovieCatalogue.Model;

import org.json.JSONObject;

public class TvShowItems {

    Integer id;
    String vote;
    String title;
    String poster;
    String backdrop;

    String release;
    String overview;

    Boolean favorite;

    public TvShowItems(JSONObject object) {

        try {


            int id = object.getInt("id");
            String name = object.getString("name");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");
            String vote = object.getString("vote_average");
            String release = object.getString("first_air_date");
            String overview = object.getString("overview");
            this.id = id;
            this.title = name;
            this.vote = vote;
            this.poster = poster;
            this.backdrop = backdrop;
            this.release = release;
            this.overview = overview;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}
