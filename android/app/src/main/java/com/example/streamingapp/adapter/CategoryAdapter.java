package com.example.streamingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streamingapp.R;
import com.example.streamingapp.model.Category;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<Category> categories = new ArrayList<>();
    private final OnCategoryClickListener listener;

    public CategoryAdapter(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.tvTitle.setText(category.getName());
        holder.tvPromoted.setText(category.isPromoted() ? "Promoted" : "Unpromoted");
        holder.imgEdit.setOnClickListener(v -> listener.onEditClicked(category));
        holder.imgDelete.setOnClickListener(v -> listener.onDeleteClicked(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface OnCategoryClickListener {
        void onEditClicked(Category category);
        void onDeleteClicked(Category category);
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPromoted;
        ImageView imgEdit, imgDelete;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvCategoryName);
            tvPromoted = itemView.findViewById(R.id.tvCategoryPromoted);
            imgEdit = itemView.findViewById(R.id.imgEditCategory);
            imgDelete = itemView.findViewById(R.id.imgDeleteCategory);
        }
    }
}
