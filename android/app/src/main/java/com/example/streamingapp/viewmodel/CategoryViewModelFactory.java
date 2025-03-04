package com.example.streamingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.streamingapp.repository.CategoryRepository;

public class CategoryViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final CategoryRepository categoryRepository;

    public CategoryViewModelFactory(Application application, CategoryRepository categoryRepository) {
        this.application = application;
        this.categoryRepository = categoryRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(application, categoryRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
