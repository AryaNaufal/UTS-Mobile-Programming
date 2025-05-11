package com.example.myapplication.service;

import com.example.myapplication.model.TMDBResponse;
import com.example.myapplication.model.TMDBVideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBApiService {
    @GET("discover/movie")
    Call<TMDBResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/{movie_id}/videos")
    Call<TMDBVideoResponse> getMovieVideos(
            @retrofit2.http.Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );


}
