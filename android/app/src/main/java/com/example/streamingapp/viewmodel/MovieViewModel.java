package com.example.streamingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.streamingapp.model.GetMoviesResponse;
import com.example.streamingapp.model.Movie;
import com.example.streamingapp.repository.MovieRepository;

import java.util.List;

import retrofit2.Call;

public class MovieViewModel extends AndroidViewModel {
    private final MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application, MovieRepository movieRepository) {
        super(application);
        this.movieRepository = movieRepository;
    }

    public LiveData<List<GetMoviesResponse>> getMovies() {
        return movieRepository.getMovies();
    }
    public LiveData<List<Movie>> searchMovies(String query) {
        return movieRepository.searchMovies(query);
    }

    public void addMovie(Movie movie, Runnable onSuccess, Runnable onFailure) {
        movieRepository.addMovie(movie, onSuccess, onFailure);
    }

    public void updateMovie(String id, Movie movie, Runnable onSuccess, Runnable onFailure) {
        movieRepository.updateMovie(id, movie, onSuccess, onFailure);
    }

    public void deleteMovie(String id, Runnable onSuccess, Runnable onFailure) {
        movieRepository.deleteMovie(id, onSuccess, onFailure);
    }
}