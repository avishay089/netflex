package com.example.streamingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.streamingapp.repository.MovieRepository;

public class MovieViewModelFactory implements ViewModelProvider.Factory{
    private final Application application;
    private final MovieRepository movieRepository;

    public MovieViewModelFactory(Application application, MovieRepository movieRepository) {
        this.application = application;
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(application, movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
