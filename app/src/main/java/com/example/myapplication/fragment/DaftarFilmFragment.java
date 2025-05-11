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

public class DaftarFilmFragment extends Fragment implements FilmAdapter.OnFilmActionListener {

    private RecyclerView recyclerView;
    private FilmAdapter filmAdapter;
    private List<FilmModel> filmList = new ArrayList<>();
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String FAVORITES_KEY = "favorites";

    // Static film data
    private static final String[] FILM_TITLES = {"Toy Story 1", "Toy Story 3", "Toy Story 4"};
    private static final int[] FILM_IMAGES = {
            R.drawable.poster_film_toy_story_1,
            R.drawable.poster_film_toy_story_3,
            R.drawable.poster_film_toy_story_4
    };
    private static final String[] FILM_DESCRIPTIONS = {
            "Film ini memperkenalkan kita pada dunia mainan yang hidup ketika manusia tidak ada di sekitar. Woody, seorang koboi mainan yang merupakan favorit anak bernama Andy, merasa terancam dengan kedatangan mainan baru yang lebih modern, Buzz Lightyear, seorang astronot mainan yang percaya dirinya adalah seorang pahlawan luar angkasa. Konflik muncul ketika Buzz merasa dirinya benar-benar berada di luar angkasa, sementara Woody merasa cemburu. Ketika Andy sekeluarga pindah, Buzz dan Woody harus bekerja sama untuk kembali ke Andy sebelum dia pergi ke rumah baru. Film ini mengangkat tema persahabatan dan penerimaan diri.\n" +
                    "\n",
            "Setelah bertahun-tahun disimpan di kotak penyimpanan, mainan-mainan ini menghadapi kenyataan bahwa Andy akan pergi ke perguruan tinggi, dan mereka tidak lagi diperlukan. Mereka akhirnya terbuang ke sebuah pusat penitipan anak bernama Sunnyside. Di sana, mereka bertemu dengan mainan lainnya yang dipimpin oleh Lotso, beruang yang tampaknya ramah namun memiliki sisi gelap. Buzz, Woody, dan teman-temannya harus bekerja sama untuk melarikan diri dari tempat tersebut, sambil merenungkan masa depan mereka sebagai mainan. Film ini penuh dengan momen emosional yang mengangkat tema perpisahan dan perubahan.\n" +
                    "\n",
            "Woody kini berada bersama pemilik baru, Bonnie, setelah Andy tumbuh dan pergi. Saat Bonnie membuat mainan baru bernama Forky, yang sebenarnya adalah benda daur ulang, Forky merasa bingung dengan perannya sebagai mainan. Woody berusaha untuk melindungi Forky dan membantunya menemukan tujuan hidupnya. Dalam perjalanan mereka, Woody bertemu kembali dengan Bo Peep, yang telah menjalani kehidupan yang bebas dan mandiri jauh dari dunia mainan yang semula. Dalam perjalanan ini, Woody mulai mempertanyakan tujuan hidupnya dan memilih antara melanjutkan hidup sebagai mainan yang setia pada pemiliknya atau mengejar kebebasan dan menjalani kehidupan yang berbeda.\n" +
                    "\n"
    };
    private static final String[] TRAILER_URLS = {
            "https://www.youtube.com/embed/v-PjgYDrg70",
            "https://www.youtube.com/embed/2BlMNH1QTeE",
            "https://www.youtube.com/embed/wmiIUN-7qhE"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daftar_film, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recycler_view_films);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadAllFilms();

        filmAdapter = new FilmAdapter(getContext(), filmList, this, false);

        recyclerView.setAdapter(filmAdapter);
    }

    private void loadAllFilms() {
        filmList.clear();

        Set<String> favorites = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());

        for (int i = 0; i < FILM_TITLES.length; i++) {
            boolean isFavorite = favorites.contains(FILM_TITLES[i]);
            filmList.add(new FilmModel(FILM_TITLES[i], FILM_IMAGES[i], FILM_DESCRIPTIONS[i], TRAILER_URLS[i], isFavorite));
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
    public void onRemoveFromFavorites(FilmModel film) {}

    @Override
    public void onAddToFavorites(FilmModel film) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Ambil daftar favorit yang sudah ada, jika tidak ada maka buat Set kosong
        Set<String> favorites = new HashSet<>(sharedPreferences.getStringSet("favorites", new HashSet<>()));

        // Cek apakah film sudah ada di daftar favorit
        if (favorites.contains(film.getTitle())) {
            Toast.makeText(getContext(), film.getTitle() + " sudah ada di favorit", Toast.LENGTH_SHORT).show();
        } else {
            favorites.add(film.getTitle());

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("favorites", favorites);
            editor.apply();

            Toast.makeText(getContext(), film.getTitle() + " ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
        }
    }
}
