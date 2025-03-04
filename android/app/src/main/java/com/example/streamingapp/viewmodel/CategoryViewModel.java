package com.example.streamingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.streamingapp.model.Category;
import com.example.streamingapp.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private final CategoryRepository categoryRepository;
    public CategoryViewModel(@NonNull Application application, CategoryRepository categoryRepository) {
        super(application);
        this.categoryRepository = categoryRepository;
    }

    public LiveData<List<Category>> getCategories() {
        return categoryRepository.getCategories();
    }

    public void addCategory(Category category, Runnable onSuccess, Runnable onFailure) {
        categoryRepository.addCategory(category, onSuccess, onFailure);
    }

    public void updateCategory(String id, Category category, Runnable onSuccess, Runnable onFailure) {
        categoryRepository.updateCategory(id, category, onSuccess, onFailure);
    }

    public void deleteCategory(String id, Runnable onSuccess, Runnable onFailure) {
        categoryRepository.deleteCategory(id, onSuccess, onFailure);
    }
}
