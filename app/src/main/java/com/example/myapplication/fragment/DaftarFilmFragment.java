package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.DetailFilmActivity;
import com.example.myapplication.R;

import java.util.HashSet;
import java.util.Set;

public class DaftarFilmFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private static final String FAVORITE_PREFS_FILE = "MyPrefs";  // SharedPreferences file name
    private static final String FAVORITE_KEY = "MyPrefs";         // Key to access favorites

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daftar_film, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireContext().getSharedPreferences(FAVORITE_PREFS_FILE, Context.MODE_PRIVATE);

        // Film 1
        view.findViewById(R.id.detail_movie_1).setOnClickListener(v -> openDetail(
                "Avenger Infinity War",
                "Ketika Thanos datang untuk mengumpulkan semua Infinity Stones...",
                R.drawable.poster_film_avengers_infinity_war,
                "https://www.youtube.com/embed/6ZfuNTqbHE8"
        ));

        view.findViewById(R.id.button_favorite_1).setOnClickListener(v ->
                addToFavorites("Avenger Infinity War"));

        // Film 2
        view.findViewById(R.id.detail_movie_2).setOnClickListener(v -> openDetail(
                "The Avengers",
                "Tim pahlawan super bersatu menghadapi ancaman global pertama mereka...",
                R.drawable.poster_film_the_avengers,
                "https://youtu.be/sXT4uBpGxNY"
        ));

        view.findViewById(R.id.button_favorite_2).setOnClickListener(v ->
                addToFavorites("The Avengers"));

        // Film 3
        view.findViewById(R.id.detail_movie_3).setOnClickListener(v -> openDetail(
                "Avengers: Doomsday",
                "Ancaman baru muncul dari masa depan membawa kehancuran...",
                R.drawable.poster_film_avengers_doomsday,
                "https://www.youtube.com/embed/XLuL_TXbK1g"
        ));

        view.findViewById(R.id.button_favorite_3).setOnClickListener(v ->
                addToFavorites("Avengers: Doomsday"));
    }

    private void openDetail(String title, String description, int imageResId, String trailerUrl) {
        Intent intent = new Intent(getActivity(), DetailFilmActivity.class);
        intent.putExtra("judul", title);
        intent.putExtra("deskripsi", description);
        intent.putExtra("gambar", imageResId);
        intent.putExtra("trailer_url", trailerUrl);
        startActivity(intent);
    }

    private void addToFavorites(String filmTitle) {
        Set<String> favorites = sharedPreferences.getStringSet(FAVORITE_KEY, new HashSet<>());
        Set<String> newFavorites = new HashSet<>(favorites);
        if (newFavorites.add(filmTitle)) {
            sharedPreferences.edit().putStringSet(FAVORITE_KEY, newFavorites).apply();
            Toast.makeText(getContext(), filmTitle + " ditambahkan ke Favorit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), filmTitle + " sudah ada di Favorit", Toast.LENGTH_SHORT).show();
        }
    }
}