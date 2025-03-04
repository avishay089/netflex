package com.example.streamingapp.ui.search;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.streamingapp.BuildConfig;
import com.example.streamingapp.R;
import com.example.streamingapp.adapter.MovieSearchAdapter;
import com.example.streamingapp.model.Movie;
import com.example.streamingapp.repository.MovieRepository;
import com.example.streamingapp.ui.SideBaseActivity;
import com.example.streamingapp.ui.videoplayer.VideoPlayerActivity;
import com.example.streamingapp.viewmodel.MovieViewModel;
import com.example.streamingapp.viewmodel.MovieViewModelFactory;

import java.util.Collections;

public class SearchActivity extends SideBaseActivity {

    private MovieViewModel viewModel;
    private MovieSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSearchMovies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new MovieSearchAdapter(new MovieSearchAdapter.OnMovieTapListener() {
            @Override
            public void onMovieItemTapListener(Movie movie) {
                showMovieDetail(movie);
            }
        });

        recyclerView.setAdapter(adapter);
        ImageView imgSearchButton = findViewById(R.id.imgSearchButton);
        imgSearchButton.setOnClickListener(v -> observeMovies());

        MovieRepository movieRepository = new MovieRepository(getApplication());
        MovieViewModelFactory movieViewModelFactory = new MovieViewModelFactory(getApplication(), movieRepository);
        viewModel = new ViewModelProvider(this, movieViewModelFactory).get(MovieViewModel.class);
    }

    private void observeMovies() {
        EditText etSearchQuery = findViewById(R.id.etSearchQuery);
        String query = etSearchQuery.getText().toString();
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(SearchActivity.this, "Input query field", Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.searchMovies(query).observe(this, movies -> {
            if (movies != null) {
                adapter.setMovies(movies);
            } else {
                adapter.setMovies(Collections.emptyList());
                Toast.makeText(SearchActivity.this, "Failed to load movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMovieDetail(Movie movie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.movie_item_view, null);
        builder.setView(dialogView);

        TextView tvMovieName = dialogView.findViewById(R.id.tvMovieName);
        TextView tvMovieCategory = dialogView.findViewById(R.id.tvMovieCategory);
        TextView tvMovieDescription = dialogView.findViewById(R.id.tvMovieDescription);
        TextView tvMovieFileName = dialogView.findViewById(R.id.tvMovieFileName);
        TextView tvMovieDuration = dialogView.findViewById(R.id.tvMovieDuration);
        TextView tvMovieRating = dialogView.findViewById(R.id.tvMovieRating);
        ImageView ivMovieBackground = dialogView.findViewById(R.id.ivMovieBackground);

        ImageView imgMoviePlay = dialogView.findViewById(R.id.imgMoviePlay);
        if (movie == null) {
            return;
        }
        tvMovieName.setText(movie.getName());
        tvMovieCategory.setText(movie.getCategory());
        tvMovieDescription.setText(movie.getDescription());
        tvMovieFileName.setText(movie.getVideoUrl());
        tvMovieDuration.setText(timeFormat(movie.getDuration()));
        tvMovieRating.setText("Rating: " + movie.getRating());

        imgMoviePlay.setOnClickListener(v -> playMovie(movie));

        String fullImageUrl = BuildConfig.BASE_URL + movie.getImageUrl() + ".png";

        Glide.with(this.getApplication())
                .load(fullImageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.background)
                        .error(R.drawable.background))
                .into(ivMovieBackground);
        builder.create().show();
    }

    private String timeFormat(int minutes) {
        String result;
        int h = minutes / 60;
        int m = minutes % 60;
        if (h < 10) result = "Duration: 0";
        else result = "Duration: ";
        result += h;
        if (m < 10) result += ":0";
        else result += ":";
        result += m;
        return result;
    }

    private void playMovie(Movie movie) {
        Intent intent = new Intent(SearchActivity.this, VideoPlayerActivity.class);
        String videoUrl = BuildConfig.BASE_URL + movie.getVideoUrl() + ".mp4";
        intent.putExtra("videoUrl", videoUrl);
        startActivity(intent);
    }
}