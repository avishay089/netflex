package com.example.streamingapp.ui.movies;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streamingapp.R;
import com.example.streamingapp.adapter.MovieAdapter;
import com.example.streamingapp.model.Category;
import com.example.streamingapp.model.Movie;
import com.example.streamingapp.repository.CategoryRepository;
import com.example.streamingapp.repository.MovieRepository;
import com.example.streamingapp.ui.SideBaseActivity;
import com.example.streamingapp.viewmodel.CategoryViewModel;
import com.example.streamingapp.viewmodel.CategoryViewModelFactory;
import com.example.streamingapp.viewmodel.MovieViewModel;
import com.example.streamingapp.viewmodel.MovieViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ManageMoviesActivity extends SideBaseActivity {

    private MovieViewModel viewModel;
    private MovieAdapter adapter;
    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_manage_movies);
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new MovieAdapter(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onEditClicked(Movie movie) {
                showAddUpdateDialog(movie);
            }

            @Override
            public void onDeleteClicked(Movie movie) {
                deleteSelectedMovie(movie);
            }
        });

        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fabAddMovie);
        fabAdd.setOnClickListener(v -> showAddUpdateDialog(null));

        MovieRepository movieRepository = new MovieRepository(getApplication());
        MovieViewModelFactory movieViewModelFactory = new MovieViewModelFactory(getApplication(), movieRepository);
        viewModel = new ViewModelProvider(this, movieViewModelFactory).get(MovieViewModel.class);

        CategoryRepository categoryRepository = new CategoryRepository(getApplication());
        CategoryViewModelFactory categoryViewModelFactory = new CategoryViewModelFactory(getApplication(), categoryRepository);
        categoryViewModel = new ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel.class);

        observeMovies();
    }

    private void observeMovies() {
        viewModel.searchMovies("0").observe(this, movies -> {
            if (movies != null) {
                adapter.setMovies(movies);
            } else {
                adapter.setMovies(Collections.emptyList());
                Toast.makeText(ManageMoviesActivity.this, "Failed to load movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddUpdateDialog(Movie movie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.movie_edit, null);
        builder.setView(dialogView);

        EditText etMovieName = dialogView.findViewById(R.id.etMovieName);
        Spinner spMovieCategory = dialogView.findViewById(R.id.spMovieCategory);
        EditText etDescription = dialogView.findViewById(R.id.etMovieDescription);
        EditText etMovieFileName = dialogView.findViewById(R.id.etMovieFileName);
        EditText etDuration = dialogView.findViewById(R.id.etMovieDuration);
        EditText etRating = dialogView.findViewById(R.id.etMovieRating);

        categoryViewModel.getCategories().observe(this, categories -> {
            if (categories != null && !categories.isEmpty()) {
                List<String> categoryNames = new ArrayList<>();
                for (Category category : categories) {
                    categoryNames.add(category.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMovieCategory.setAdapter(adapter);
                if (movie != null) {
                    int pos = categoryNames.indexOf(movie.getCategory());
                    if (pos >= 0) {
                        spMovieCategory.setSelection(pos);
                    }
                } else {
                    spMovieCategory.setSelection(0);
                }
            }
        });

        if (movie != null) {
            etMovieName.setText(movie.getName());
            etDescription.setText(movie.getDescription());
            etMovieFileName.setText(movie.getVideoUrl());
            etDuration.setText(String.valueOf(movie.getDuration()));
            etRating.setText(String.valueOf(movie.getRating()));
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            String movieName = etMovieName.getText().toString();
            String movieCategory = spMovieCategory.getSelectedItem().toString();
            String description = etDescription.getText().toString();
            String fileName = etMovieFileName.getText().toString();
            int duration = Integer.parseInt(etDuration.getText().toString());
            int rating = Integer.parseInt(etRating.getText().toString());


            if (TextUtils.isEmpty(movieName) || TextUtils.isEmpty(movieCategory) || TextUtils.isEmpty(description) || TextUtils.isEmpty(fileName)) {
                Toast.makeText( this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (movie == null) {
                Movie newMovie = new Movie(movieName, movieCategory, description, fileName);
                newMovie.setDuration(duration);
                newMovie.setRating(rating);
                viewModel.addMovie(newMovie, new Runnable() {
                    @Override
                    public void run() {
                        observeMovies();
                    }
                }, null);
            } else {
                movie.setName(movieName);
                movie.setCategory(movieCategory);
                movie.setDescription(description);
                movie.setVideoUrl(fileName);
                movie.setImageUrl(fileName);
                movie.setDuration(Integer.parseInt(etDuration.getText().toString()));
                movie.setRating(Integer.parseInt(etRating.getText().toString()));
                viewModel.updateMovie(movie.get_id(), movie, new Runnable() {
                    @Override
                    public void run() {
                        observeMovies();
                    }
                }, null);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    public void deleteSelectedMovie(Movie movie) {
        viewModel.deleteMovie(movie.get_id(), new Runnable() {
            @Override
            public void run() {
                observeMovies();
            }
        }, null);
    }
}