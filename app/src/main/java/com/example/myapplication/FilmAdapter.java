package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {
    private Context context;
    private List<FilmModel> filmList;
    private SharedPreferences sharedPreferences;
    private static final String FAVORITE_KEY = "MyPrefs";  // Key untuk SharedPreferences

    public FilmAdapter(Context context, List<FilmModel> filmList) {
        this.context = context;
        this.filmList = filmList;
        this.sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        FilmModel film = filmList.get(position);
        holder.textTitle.setText(film.getTitle());

        // Memuat gambar ke ImageView
        holder.imageFilm.setImageResource(film.getImageId());  // Menggunakan imageId yang ada di FilmModel

        // Periksa apakah film ini favorit
        Set<String> favorites = sharedPreferences.getStringSet(FAVORITE_KEY, new HashSet<>());
        boolean isFavorite = favorites.contains(film.getTitle());

        // Perbarui UI dengan status favorit
        updateFavoriteUI(holder, isFavorite);

        // Tombol "Hapus" untuk menghapus item
        holder.buttonDeleteFavorite.setVisibility(View.VISIBLE);

        // Set click listener untuk tombol hapus
        holder.buttonDeleteFavorite.setOnClickListener(v -> {
            removeFromFavorites(film.getTitle(), position);  // Menghapus film dari favorit
        });
    }


    @Override
    public int getItemCount() {
        return filmList.size();
    }

    // Fungsi untuk memperbarui status favorit UI
    private void updateFavoriteUI(FilmViewHolder holder, boolean isFavorite) {
        if (isFavorite) {
            holder.textFavoriteStatus.setText("Favorit");
            holder.textFavoriteStatus.setTextColor(context.getResources().getColor(R.color.favorite_color));
        } else {
            holder.textFavoriteStatus.setText("Bukan Favorit");
            holder.textFavoriteStatus.setTextColor(context.getResources().getColor(R.color.not_favorite_color));
        }
    }

    // Fungsi untuk menghapus film dari favorit
    private void removeFromFavorites(String filmTitle, int position) {
        Set<String> favorites = sharedPreferences.getStringSet(FAVORITE_KEY, new HashSet<>());

        // Buat salinan baru untuk dimodifikasi
        Set<String> updatedFavorites = new HashSet<>(favorites);
        updatedFavorites.remove(filmTitle);

        // Simpan kembali ke SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(FAVORITE_KEY); // Hindari bug caching
        editor.putStringSet(FAVORITE_KEY, updatedFavorites);
        editor.apply();

        // Cari posisi yang valid berdasarkan title
        for (int i = 0; i < filmList.size(); i++) {
            if (filmList.get(i).getTitle().equals(filmTitle)) {
                filmList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    static class FilmViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textFavoriteStatus;
        TextView buttonDeleteFavorite;  // Tombol "Hapus"
        ImageView imageFilm;  // Menambahkan ImageView untuk gambar film

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textFavoriteStatus = itemView.findViewById(R.id.text_favorite_status);
            buttonDeleteFavorite = itemView.findViewById(R.id.button_delete_favorite);  // Tombol "Hapus"
            imageFilm = itemView.findViewById(R.id.image_film);  // Menghubungkan dengan ImageView
        }
    }

}
