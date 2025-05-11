package com.example.myapplication;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailFilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);
        WebView webView = findViewById(R.id.webview_trailer);
        String trailerUrl = getIntent().getStringExtra("trailer_url");

        // Sembunyikan ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Inisialisasi UI
//        ImageView imageView = findViewById(R.id.image_detail_film);
        TextView titleView = findViewById(R.id.text_detail_title);
        TextView descView = findViewById(R.id.text_description);

        // Ambil data dari Intent
        String title = getIntent().getStringExtra("judul");
        String desc = getIntent().getStringExtra("deskripsi");
        String imageUrl = getIntent().getStringExtra("gambar");

        // Set judul & deskripsi
        titleView.setText(title);
        descView.setText(desc);

        // Tampilkan gambar dengan Glide
//        if (imageUrl != null && !imageUrl.isEmpty()) {
//            Glide.with(this)
//                    .load(imageUrl)
//                    .placeholder(R.drawable.poster_film_toy_story_1)
//                    .into(imageView);
//        }

        if (trailerUrl != null && !trailerUrl.isEmpty()) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(trailerUrl);
        }

    }
}
