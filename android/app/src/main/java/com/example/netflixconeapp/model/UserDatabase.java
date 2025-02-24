package com.example.netflixconeapp.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static volatile UserDatabase INSTANCE;

    public abstract UserDao userDao();

    public static UserDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    UserDatabase.class,
                                    "user_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}