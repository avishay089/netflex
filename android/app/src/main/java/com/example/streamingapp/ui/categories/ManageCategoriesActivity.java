package com.example.streamingapp.ui.categories;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streamingapp.R;
import com.example.streamingapp.adapter.CategoryAdapter;
import com.example.streamingapp.model.Category;
import com.example.streamingapp.repository.CategoryRepository;
import com.example.streamingapp.ui.SideBaseActivity;
import com.example.streamingapp.viewmodel.CategoryViewModel;
import com.example.streamingapp.viewmodel.CategoryViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Collections;

public class ManageCategoriesActivity extends SideBaseActivity {

    private CategoryViewModel viewModel;
    private CategoryAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_manage_categories);
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.recyclerViewCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CategoryAdapter(new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onEditClicked(Category category) {
                showAddUpdateDialog(category);
            }

            @Override
            public void onDeleteClicked(Category category) {
                deleteCategory(category);
            }
        });

        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fabAddCategory);
        fabAdd.setOnClickListener(v -> showAddUpdateDialog(null));

        CategoryRepository categoryRepository = new CategoryRepository(getApplication());
        CategoryViewModelFactory categoryViewModelFactory = new CategoryViewModelFactory(getApplication(), categoryRepository);
        viewModel = new ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel.class);

        observeCategories();
    }

    private void observeCategories() {
        viewModel.getCategories().observe(this, categories -> {
            if (categories != null) {
                adapter.setCategories(categories);
            } else {
                adapter.setCategories(Collections.emptyList());
                Toast.makeText(ManageCategoriesActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddUpdateDialog(Category category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.category_edit, null);
        builder.setView(dialogView);

        EditText etTitle = dialogView.findViewById(R.id.etCategoryName);
        SwitchMaterial swPromoted = dialogView.findViewById(R.id.swPromoted);
        if (category != null) {
            etTitle.setText(category.getName());
            swPromoted.setChecked(category.isPromoted());
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = etTitle.getText().toString();
            Boolean promoted = swPromoted.isChecked();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText( this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (category == null) {
                Category newCategory = new Category(title, promoted);
                viewModel.addCategory(newCategory, new Runnable() {
                    @Override
                    public void run() {
                        observeCategories();
                    }
                }, null);

            } else {
                category.setName(title);
                category.setPromoted(promoted);
                viewModel.updateCategory(category.get_id(), category, new Runnable() {
                    @Override
                    public void run() {
                        observeCategories();
                    }
                }, null);

            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    public void deleteCategory(Category category) {
        viewModel.deleteCategory(category.get_id(), new Runnable() {
            @Override
            public void run() {
                observeCategories();
            }
        }, null);
    }
}