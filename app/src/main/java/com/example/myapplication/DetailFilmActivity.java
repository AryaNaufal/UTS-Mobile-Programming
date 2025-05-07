package com.example.myapplication;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailFilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        // Sembunyikan ActionBar bawaan
        getSupportActionBar().hide();

        // Get the references to the UI components
        TextView titleView = findViewById(R.id.text_detail_title);
        TextView descView = findViewById(R.id.text_description);
        WebView webView = findViewById(R.id.webview_trailer);

        // Retrieve the data passed from the previous activity
        String title = getIntent().getStringExtra("judul");
        String desc = getIntent().getStringExtra("deskripsi");
        String trailerUrl = getIntent().getStringExtra("trailer_url");

        // Set the title and description
        titleView.setText(title);
        descView.setText(desc);

        // Check if a trailer URL is provided
        if (trailerUrl != null && !trailerUrl.isEmpty()) {
            // Set up the WebView to display the YouTube trailer
            webView.setWebViewClient(new WebViewClient());
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true); // Enable JavaScript

            // HTML to load the YouTube video in the WebView
            String html = "<html><body style=\"margin:0;padding:0;\"><iframe width=\"100%\" height=\"100%\" src=\""
                    + trailerUrl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
            webView.loadData(html, "text/html", "utf-8");

        } else {
            // If no trailer URL, show a toast or a default message
            Toast.makeText(this, "Trailer not available", Toast.LENGTH_SHORT).show();
        }
    }
}