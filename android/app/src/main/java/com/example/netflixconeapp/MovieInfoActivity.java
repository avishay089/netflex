package com.example.netflixconeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MovieInfoActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtDescription;
    private ImageView imgThumbnail;
    private Button btnWatchMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        // Initialize the UI components
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        imgThumbnail = findViewById(R.id.imgThumbnail);
        btnWatchMovie = findViewById(R.id.btnWatch);

        // Get data passed through the Intent
        Intent intent = getIntent();
        String movieTitle = intent.getStringExtra("MOVIE_TITLE");
        String movieDescription = intent.getStringExtra("MOVIE_DESCRIPTION");
        int movieThumbnail = intent.getIntExtra("MOVIE_THUMBNAIL", 0);

        // Set the movie information
        txtTitle.setText(movieTitle);
        txtDescription.setText(movieDescription);
        imgThumbnail.setImageResource(movieThumbnail);

        // Set click listener for "Watch" button
        btnWatchMovie.setOnClickListener(v -> {
            // Simulate opening a movie player
            Intent watchIntent = new Intent(MovieInfoActivity.this, MoviePlayerActivity.class);
            watchIntent.putExtra("MOVIE_TITLE", movieTitle);
            startActivity(watchIntent);
        });
    }
}
