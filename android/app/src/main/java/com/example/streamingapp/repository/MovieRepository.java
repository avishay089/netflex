package com.example.streamingapp.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.streamingapp.data.local.AppDatabase;
import com.example.streamingapp.data.local.MovieDao;
import com.example.streamingapp.data.local.TokenManager;
import com.example.streamingapp.data.remote.MovieApiService;
import com.example.streamingapp.data.remote.RetroInstance;
import com.example.streamingapp.model.Category;
import com.example.streamingapp.model.GetMoviesResponse;
import com.example.streamingapp.model.Movie;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private MovieApiService movieApiService;
    private MovieDao movieDao;
    private final Context context;
    private TokenManager tokenManager;
    private String userId;

    public MovieRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.movieDao = db.movieDao();
        this.context = application;
        this.movieApiService = RetroInstance.getMovieApi();
        tokenManager = new TokenManager(this.context);
    }

    public LiveData<List<GetMoviesResponse>> getMovies() {
        userId = tokenManager.getUserInfo().getUserId();
        MutableLiveData<List<GetMoviesResponse>> moviesLiveData = new MutableLiveData<>();
        movieApiService.getMovies(userId).enqueue(new Callback<List<GetMoviesResponse>>() {
            @Override
            public void onResponse(Call<List<GetMoviesResponse>> call, Response<List<GetMoviesResponse>> response) {
                if (response.isSuccessful()) {
                    moviesLiveData.setValue(response.body());
                } else {
                    Toast.makeText(context, "Failed to get movies", Toast.LENGTH_SHORT).show();
                    moviesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<GetMoviesResponse>> call, Throwable t) {
                moviesLiveData.setValue(null);
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return moviesLiveData;
    }

    public LiveData<List<Movie>> searchMovies(String query) {
        userId = tokenManager.getUserInfo().getUserId();
        MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
        movieApiService.searchMovies(userId, query).enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    moviesLiveData.setValue(response.body());
                } else {
                    Toast.makeText(context, "Failed to get movies", Toast.LENGTH_SHORT).show();
                    moviesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                moviesLiveData.setValue(null);
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return moviesLiveData;
    }

    public void addMovie(Movie movie, Runnable onSuccess, Runnable onFailure) {
        userId = tokenManager.getUserInfo().getUserId();
        movieApiService.addMovie(userId, movie).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Movie added successfully", Toast.LENGTH_SHORT).show();
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                } else {
                    Toast.makeText(context, "Failed to add movie", Toast.LENGTH_SHORT).show();
                    if (onFailure != null) {
                        onFailure.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                if (onFailure != null) {
                    onFailure.run();
                }
            }
        });
    }

    public void updateMovie(String id, Movie movie, Runnable onSuccess, Runnable onFailure) {
        userId = tokenManager.getUserInfo().getUserId();
        movieApiService.updateMovie(userId, id, movie).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Movie updated successfully", Toast.LENGTH_SHORT).show();
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                } else {
                    Toast.makeText(context, "Failed to update movie", Toast.LENGTH_SHORT).show();
                    if (onFailure != null) {
                        onFailure.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteMovie(String id, Runnable onSuccess, Runnable onFailure) {
        userId = tokenManager.getUserInfo().getUserId();
        movieApiService.deleteMovie(userId, id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Movie deleted successfully", Toast.LENGTH_SHORT).show();
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                } else {
                    Toast.makeText(context, "Failed to delete movie", Toast.LENGTH_SHORT).show();
                    if (onFailure != null) {
                        onFailure.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                if (onFailure != null) {
                    onFailure.run();
                }
            }
        });
    }
}
