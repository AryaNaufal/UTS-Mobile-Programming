package com.example.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.FilmAdapter;
import com.example.myapplication.FilmModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FilmAdapter filmAdapter;
    private List<FilmModel> favoriteFilmList = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Mengakses SharedPreferences untuk mengambil film favorit
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Mengambil film favorit
        loadFavoriteFilms();

        // Menyediakan adapter untuk recycler view
        filmAdapter = new FilmAdapter(getActivity(), favoriteFilmList);
        recyclerView.setAdapter(filmAdapter);

        if (favoriteFilmList.isEmpty()) {
            Toast.makeText(getActivity(), "Tidak ada film favorit", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadFavoriteFilms() {
        favoriteFilmList.clear();

        Set<String> favoriteTitles = sharedPreferences.getStringSet("MyPrefs", new HashSet<>());

        // Daftar film yang ada beserta ID gambar
        String[] filmTitles = {"Avenger Infinity War", "The Avengers", "Avengers: Doomsday"};
        int[] filmImages = {
                R.drawable.poster_film_avengers_infinity_war,
                R.drawable.poster_film_the_avengers,
                R.drawable.poster_film_avengers_doomsday
        };

        for (int i = 0; i < filmTitles.length; i++) {
            String title = filmTitles[i];
            if (favoriteTitles.contains(title)) {
                favoriteFilmList.add(new FilmModel(title, filmImages[i])); // Menyimpan title dan imageId
            }
        }
    }
}
