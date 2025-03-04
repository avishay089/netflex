package com.example.streamingapp.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.streamingapp.data.local.AppDatabase;
import com.example.streamingapp.data.local.CategoryDao;
import com.example.streamingapp.data.local.TokenManager;
import com.example.streamingapp.data.remote.CategoryApiService;
import com.example.streamingapp.data.remote.RetroInstance;
import com.example.streamingapp.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private CategoryApiService categoryApiService;
    private CategoryDao categoryDao;
    private final Context context;
    private TokenManager tokenManager;
    private String userId;

    public CategoryRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.categoryDao = db.categoryDao();
        this.context = application;
        this.categoryApiService = RetroInstance.getCategoryApi();
        tokenManager = new TokenManager(this.context);
    }

    public LiveData<List<Category>> getCategories() {
        userId = tokenManager.getUserInfo().getUserId();
        MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
        categoryApiService.getCategories(userId).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoriesLiveData.setValue(response.body());
                } else {
                    Toast.makeText(context, "Failed to get categories", Toast.LENGTH_SHORT).show();
                    categoriesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                categoriesLiveData.setValue(null);
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return categoriesLiveData;
    }

    public void addCategory(Category category, Runnable onSuccess, Runnable onFailure) {
        userId = tokenManager.getUserInfo().getUserId();
        categoryApiService.addCategory(userId, category).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Category added successfully", Toast.LENGTH_SHORT).show();
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                } else {
                    Toast.makeText(context, "Failed to add category", Toast.LENGTH_SHORT).show();
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

    public void updateCategory(String id, Category category, Runnable onSuccess, Runnable onFailure) {
        userId = tokenManager.getUserInfo().getUserId();
        categoryApiService.updateCategory(userId, id, category).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Category updated successfully", Toast.LENGTH_SHORT).show();
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                } else {
                    Toast.makeText(context, "Failed to update category", Toast.LENGTH_SHORT).show();
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

    public void deleteCategory(String id, Runnable onSuccess, Runnable onFailure) {
        userId = tokenManager.getUserInfo().getUserId();
        categoryApiService.deleteCategory(userId, id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                    if (onSuccess != null) {
                        onSuccess.run();
                    }
                } else {
                    Toast.makeText(context, "Failed to delete category", Toast.LENGTH_SHORT).show();
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
