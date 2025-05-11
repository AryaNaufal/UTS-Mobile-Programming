package com.example.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.model.FilmModel;
import com.example.myapplication.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private final Context context;
    private final List<FilmModel> filmList;
    private final SharedPreferences sharedPreferences;
    private final OnFilmActionListener listener;
    private boolean isFavoriteFragment;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String FAVORITE_KEY = "favorites";

    public interface OnFilmActionListener {
        void onDetail(FilmModel film);
        void onFavorite(FilmModel film);
        void onRemoveFromFavorites(FilmModel film);
        void onAddToFavorites(FilmModel film);
    }

    public FilmAdapter(Context context, List<FilmModel> filmList, OnFilmActionListener listener, boolean isFavoriteFragment) {
        this.context = context;
        this.filmList = filmList;
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.listener = listener;
        this.isFavoriteFragment = isFavoriteFragment;
    }

    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isFavoriteFragment) {
            // Jika ini fragment Favorite, gunakan item_favorite
            view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        } else {
            // Jika ini fragment Daftar Film, gunakan item_film
            view = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
        }
        return new FilmViewHolder(view);
    }


//    @Override
//    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
//        FilmModel film = filmList.get(position);
//
//        holder.textTitle.setText(film.getTitle());
//        holder.imageFilm.setImageResource(film.getImageId());
//
//        // Cek apakah film favorit
//        boolean isFavorite = isFilmFavorite(film);
//
//        // Mengubah status favorit
//        if (isFavoriteFragment) {
//            holder.textFavoriteStatus.setText(film.isFavorite() ? "Favorit" : "Bukan Favorit");
//
//            if (film.isFavorite()) {
//                holder.textFavoriteStatus.setTextColor(context.getResources().getColor(R.color.favorite_color));
//            }
//
//            if (holder.buttonDeleteFavorite != null) {
//                holder.buttonDeleteFavorite.setVisibility(View.VISIBLE);
//                holder.buttonDeleteFavorite.setOnClickListener(v -> listener.onRemoveFromFavorites(film));
//                holder.textFavoriteStatus.setTypeface(null, Typeface.BOLD);
//            }
//
//        } else {
//            holder.textTitle.setText(film.getTitle());
//            holder.textFavoriteStatus.setText(film.isFavorite() ? "Favorit" : "");
//            if (film.isFavorite()) {
//                holder.textFavoriteStatus.setTextColor(context.getResources().getColor(R.color.favorite_color));
//                holder.textFavoriteStatus.setTypeface(null, Typeface.BOLD);
//            }
//            holder.buttonFavorite.setOnClickListener(v -> {
//                    listener.onAddToFavorites(film);
//                notifyItemChanged(position);
//            });
//            holder.buttonDetail.setOnClickListener(v -> listener.onDetail(film));
//        }
//
//        holder.itemView.setOnClickListener(v -> listener.onDetail(film));
//    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        FilmModel film = filmList.get(position);

        holder.textTitle.setText(film.getTitle());

        // Load image dari URL pakai Glide
        Glide.with(context)
                .load(film.getImageUrl())
                .placeholder(R.drawable.poster_film_toy_story_1) // ganti sesuai kebutuhan
                .into(holder.imageFilm);

        boolean isFavorite = isFilmFavorite(film);

        if (isFavoriteFragment) {
            holder.textFavoriteStatus.setText(film.isFavorite() ? "Favorit" : "Bukan Favorit");

            if (film.isFavorite()) {
                holder.textFavoriteStatus.setTextColor(context.getResources().getColor(R.color.favorite_color));
            }

            if (holder.buttonDeleteFavorite != null) {
                holder.buttonDeleteFavorite.setVisibility(View.VISIBLE);
                holder.buttonDeleteFavorite.setOnClickListener(v -> listener.onRemoveFromFavorites(film));
                holder.textFavoriteStatus.setTypeface(null, Typeface.BOLD);
            }

        } else {
            holder.textFavoriteStatus.setText(film.isFavorite() ? "Favorit" : "");
            if (film.isFavorite()) {
                holder.textFavoriteStatus.setTextColor(context.getResources().getColor(R.color.favorite_color));
                holder.textFavoriteStatus.setTypeface(null, Typeface.BOLD);
            }

            holder.buttonFavorite.setOnClickListener(v -> {
                listener.onAddToFavorites(film);
                notifyItemChanged(position);
            });

            holder.buttonDetail.setOnClickListener(v -> listener.onDetail(film));
        }

        holder.itemView.setOnClickListener(v -> listener.onDetail(film));
    }


    @Override
    public int getItemCount() {
        return filmList.size();
    }

    private boolean isFilmFavorite(FilmModel film) {
        Set<String> favorites = sharedPreferences.getStringSet(FAVORITE_KEY, new HashSet<>());
        return favorites != null && favorites.contains(film.getTitle());
    }

    static class FilmViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textFavoriteStatus;
        ImageView imageFilm;
        Button buttonFavorite, buttonDetail, buttonDeleteFavorite;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textFavoriteStatus = itemView.findViewById(R.id.text_favorite_status);
            imageFilm = itemView.findViewById(R.id.image_film);
            buttonFavorite = itemView.findViewById(R.id.button_favorite);
            buttonDetail = itemView.findViewById(R.id.detail_movie);
            buttonDeleteFavorite = itemView.findViewById(R.id.button_delete_favorite);
        }
    }
}
