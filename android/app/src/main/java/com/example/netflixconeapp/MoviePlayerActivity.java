package com.example.netflixconeapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MoviePlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_player);

        // Get the movie title passed from MovieInfoActivity
        String movieTitle = getIntent().getStringExtra("MOVIE_TITLE");

        // Get a reference to the TextView
        TextView txtMoviePlayerTitle = findViewById(R.id.txtMoviePlayerTitle);

        // Display the movie's title in the player (or implement video playback logic here)
        txtMoviePlayerTitle.setText("Playing: " + movieTitle);
    }
}
