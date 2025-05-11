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
import com.example.myapplication.R;
import com.example.myapplication.adapter.FilmAdapter;
import com.example.myapplication.model.FilmModel;

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
    private static final String FAVORITES_KEY = "favorites_data"; // simpan data JSON, bukan hanya judul

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recycler_view_favorite);
        textNoFavorites = view.findViewById(R.id.text_no_favorites);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFavoriteFilms();

        filmAdapter = new FilmAdapter(getContext(), filmList, this, true);
        recyclerView.setAdapter(filmAdapter);
    }

    private void loadFavoriteFilms() {
        filmList.clear();

        Set<String> favoritesJson = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());

        if (favoritesJson != null) {
            for (String json : favoritesJson) {
                FilmModel film = FilmModel.fromJson(json);
                if (film != null) {
                    film.setFavorite(true);
                    filmList.add(film);
                }
            }
        }

        textNoFavorites.setVisibility(filmList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void removeFromFavorites(FilmModel film) {
        Set<String> favoritesJson = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());
        Set<String> updatedFavorites = new HashSet<>();

        for (String json : favoritesJson) {
            FilmModel storedFilm = FilmModel.fromJson(json);
            if (storedFilm != null && !storedFilm.getTitle().equals(film.getTitle())) {
                updatedFavorites.add(json);
            }
        }

        sharedPreferences.edit().putStringSet(FAVORITES_KEY, updatedFavorites).apply();
        Toast.makeText(getContext(), film.getTitle() + " dihapus dari Favorit", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetail(FilmModel film) {
        Intent intent = new Intent(getActivity(), DetailFilmActivity.class);
        intent.putExtra("judul", film.getTitle());
        intent.putExtra("deskripsi", film.getDescription());
        intent.putExtra("gambar", film.getImageUrl());
        intent.putExtra("trailer_url", film.getTrailerUrl());
        startActivity(intent);
    }

    @Override public void onFavorite(FilmModel film) {}
    @Override public void onAddToFavorites(FilmModel film) {}

    @Override
    public void onRemoveFromFavorites(FilmModel film) {
        removeFromFavorites(film);
        loadFavoriteFilms();
        filmAdapter.notifyDataSetChanged();
    }
}
