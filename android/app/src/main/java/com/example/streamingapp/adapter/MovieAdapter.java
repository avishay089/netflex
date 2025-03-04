package com.example.streamingapp.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.streamingapp.BuildConfig;
import com.example.streamingapp.R;
import com.example.streamingapp.model.Movie;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private final OnMovieClickListener listener;

    public MovieAdapter(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvMovieName.setText(movie.getName());
        holder.tvMovieCategory.setText(movie.getCategory());
        holder.tvDescription.setText(movie.getDescription());
        holder.imgEdit.setOnClickListener(v -> listener.onEditClicked(movie));
        holder.imgDelete.setOnClickListener(v -> listener.onDeleteClicked(movie));
        String fullImageUrl = BuildConfig.BASE_URL + movie.getImageUrl() + ".png";

        Glide.with(holder.itemView.getContext())
                .load(fullImageUrl)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.background)
                        .error(R.drawable.background))
                .into(holder.ivMovieBackground);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public interface OnMovieClickListener {
        void onEditClicked(Movie movie);
        void onDeleteClicked(Movie movie);
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutMovieItem;
        TextView tvMovieName, tvMovieCategory, tvDescription;
        ImageView imgEdit, imgDelete, ivMovieBackground;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutMovieItem = itemView.findViewById(R.id.layoutMovieItem);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvMovieCategory = itemView.findViewById(R.id.tvMovieCategory);
            tvDescription = itemView.findViewById(R.id.tvMovieDescription);
            imgEdit = itemView.findViewById(R.id.imgEditMovie);
            imgDelete = itemView.findViewById(R.id.imgDeleteMovie);
            ivMovieBackground = itemView.findViewById(R.id.ivMovieBackground);
        }
    }
}
