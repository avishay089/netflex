package com.example.streamingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.streamingapp.model.User;
import com.example.streamingapp.repository.UserRepository;

public class UserViewModelFactory implements ViewModelProvider.Factory{
    private final Application application;
    private final UserRepository userRepository;

    public UserViewModelFactory(Application application, UserRepository userRepository) {
        this.application = application;
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(application, userRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
