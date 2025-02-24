package com.example.netflixconeapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.netflixconeapp.repository.UserRepository;

public class SignupViewModelFactory implements ViewModelProvider.Factory {

    private final UserRepository repository;

    public SignupViewModelFactory(UserRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignupViewModel.class)) {
            return (T) new SignupViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
