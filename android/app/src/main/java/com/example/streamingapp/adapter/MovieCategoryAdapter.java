package com.example.streamingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streamingapp.R;
import com.example.streamingapp.model.GetMoviesResponse;
import com.example.streamingapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieCategoryAdapter extends RecyclerView.Adapter<MovieCategoryAdapter.MovieCategoryViewHolder> {
    private List<GetMoviesResponse> responseList;
    private final OnMovieCategoryClickListener listener;

    public MovieCategoryAdapter(List<GetMoviesResponse> responseList, OnMovieCategoryClickListener listener) {
        this.responseList = responseList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public MovieCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_group, parent, false);
        return new MovieCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCategoryViewHolder holder, int position) {
        holder.bind(responseList.get(position));
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public interface OnMovieCategoryClickListener  {
        void onMovieCategoryClick(GetMoviesResponse response, Movie movie);
    }

    class MovieCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        RecyclerView movieItemRecyclerView;
        OnMovieCategoryClickListener categoryListener;

        public MovieCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            movieItemRecyclerView = itemView.findViewById(R.id.movieItemRecyclerView);
            this.categoryListener = listener;
        }

        public void bind(GetMoviesResponse response) {
            tvCategoryName.setText(response.getCategory_name());
            movieItemRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            movieItemRecyclerView.setAdapter(new MovieItemAdapter(response.getMovies(), movie -> {
                if (categoryListener != null) {
                    categoryListener.onMovieCategoryClick(response, movie);
                }
            }));
        }
    }
}