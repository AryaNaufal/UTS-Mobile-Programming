package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class FilmModel implements Parcelable {
    private String title;
    private String imageUrl;  // <-- dari TMDB
    private String description;
    private String trailerUrl;
    private boolean favorite;

    // Constructor
    public FilmModel(String title, String imageUrl, String description, String trailerUrl, boolean favorite) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.trailerUrl = trailerUrl;
        this.favorite = favorite;
    }

    public static FilmModel fromJson(String json) {
        try {
            return new Gson().fromJson(json, FilmModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    // Parcelable implementation
    protected FilmModel(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        trailerUrl = in.readString();
        favorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(imageUrl);
        parcel.writeString(description);
        parcel.writeString(trailerUrl);
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilmModel> CREATOR = new Creator<FilmModel>() {
        @Override
        public FilmModel createFromParcel(Parcel in) {
            return new FilmModel(in);
        }

        @Override
        public FilmModel[] newArray(int size) {
            return new FilmModel[size];
        }
    };
}
