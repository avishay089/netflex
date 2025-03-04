package com.example.streamingapp.data.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.streamingapp.model.Category;
import com.example.streamingapp.model.User;
import com.example.streamingapp.model.Movie;

@Database(entities = {User.class, Movie.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract MovieDao movieDao();
    public abstract CategoryDao categoryDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "streaming_app_database").build();
        }
        return instance;
    }
}
