package com.example.MovieCatalogue.Model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {


    public Favorite(Parcel in) {
        _id = in.readInt();
        id = in.readInt();
        poster = in.readString();
        type = in.readString();
    }

    public Favorite(Context applicationContext) {

    }

    public Favorite() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(id);
        dest.writeString(poster);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    int _id;
    int id;
    String poster;
    String type;
}
