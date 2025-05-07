package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class FilmModel implements Parcelable {
    private String title;
    private int imageId;

    public FilmModel(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    // Getter untuk title
    public String getTitle() {
        return title;
    }

    // Getter untuk imageId
    public int getImageId() {
        return imageId;
    }

    // Konstruktor Parcelable
    protected FilmModel(Parcel in) {
        title = in.readString();
        imageId = in.readInt();
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

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeInt(imageId);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}