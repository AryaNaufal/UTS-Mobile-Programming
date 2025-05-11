package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TMDBResponse {
    @SerializedName("results")
    private List<TMDBMovie> results;

    public List<TMDBMovie> getResults() {
        return results;
    }
}