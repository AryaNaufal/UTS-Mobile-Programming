package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FilmModel implements Parcelable {
    private String title;
    private int imageId;
    private String description;
    private String trailerUrl;
    private boolean favorite;

    // Constructor
    public FilmModel(String title, int imageId, String description, String trailerUrl, boolean favorite) {
        this.title = title;
        this.imageId = imageId;
        this.description = description;
        this.trailerUrl = trailerUrl;
        this.favorite = favorite;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
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
        imageId = in.readInt();
        description = in.readString();
        trailerUrl = in.readString();
        favorite = in.readByte() != 0;  // Read the favorite boolean value
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeInt(imageId);
        parcel.writeString(description);
        parcel.writeString(trailerUrl);
        parcel.writeByte((byte) (favorite ? 1 : 0));  // Write the favorite boolean value
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
