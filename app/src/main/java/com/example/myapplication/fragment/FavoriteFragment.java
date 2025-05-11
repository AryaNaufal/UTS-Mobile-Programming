package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DetailFilmActivity;
import com.example.myapplication.adapter.FilmAdapter;
import com.example.myapplication.model.FilmModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteFragment extends Fragment implements FilmAdapter.OnFilmActionListener {

    private RecyclerView recyclerView;
    private FilmAdapter filmAdapter;
    private final List<FilmModel> filmList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private TextView textNoFavorites;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String FAVORITES_KEY = "favorites";

    private static final String[] FILM_TITLES = {"Toy Story 1", "Toy Story 3", "Toy Story 4"};
    private static final int[] FILM_IMAGES = {
            R.drawable.poster_film_toy_story_1,
            R.drawable.poster_film_toy_story_3,
            R.drawable.poster_film_toy_story_4
    };
    private static final String[] FILM_DESCRIPTIONS = {
            "Deskripsi film 1...",
            "Deskripsi film 2...",
            "Deskripsi film 3..."
    };
    private static final String[] TRAILER_URLS = {
            "https://www.youtube.com/embed/v-PjgYDrg70",
            "https://www.youtube.com/embed/2BlMNH1QTeE",
            "https://www.youtube.com/embed/wmiIUN-7qhE"
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recycler_view_favorite); // Correct ID
        textNoFavorites = view.findViewById(R.id.text_no_favorites);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFavoriteFilms();

        filmAdapter = new FilmAdapter(getContext(), filmList, this, true);


        recyclerView.setAdapter(filmAdapter);
    }

    private void loadFavoriteFilms() {
        filmList.clear();
        Set<String> favoriteTitles = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());

        if (favoriteTitles != null) {
            for (int i = 0; i < FILM_TITLES.length; i++) {
                String title = FILM_TITLES[i];
                if (favoriteTitles.contains(title)) {
                    filmList.add(new FilmModel(title, FILM_IMAGES[i], FILM_DESCRIPTIONS[i], TRAILER_URLS[i], true));
                }
            }
        }

        if (filmList.isEmpty()) {
            textNoFavorites.setVisibility(View.VISIBLE);
        } else {
            textNoFavorites.setVisibility(View.GONE);
        }
    }

    private void removeFromFavorites(FilmModel film) {
        Set<String> favorites = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());
        Set<String> newFavorites = new HashSet<>(favorites);
        if (newFavorites.remove(film.getTitle())) {
            sharedPreferences.edit().putStringSet(FAVORITES_KEY, newFavorites).apply();
            Toast.makeText(getContext(), film.getTitle() + " dihapus dari Favorit", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetail(FilmModel film) {
        Intent intent = new Intent(getActivity(), DetailFilmActivity.class);
        intent.putExtra("judul", film.getTitle());
        intent.putExtra("deskripsi", film.getDescription());
        intent.putExtra("gambar", film.getImageId());
        intent.putExtra("trailer_url", film.getTrailerUrl());
        startActivity(intent);
    }

    @Override
    public void onFavorite(FilmModel film) {}

    @Override
    public void onRemoveFromFavorites(FilmModel film) {
        removeFromFavorites(film);
        loadFavoriteFilms();
        filmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddToFavorites(FilmModel film) {}
}
