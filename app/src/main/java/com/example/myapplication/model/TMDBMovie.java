package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
public class TMDBMovie {
    private int id;
    private String title;
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getOverview() { return overview; }
    public String getPosterPath() { return posterPath; }
}
