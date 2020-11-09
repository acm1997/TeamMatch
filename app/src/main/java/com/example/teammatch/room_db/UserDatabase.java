package com.example.teammatch.room_db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teammatch.objects.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;

    public static UserDatabase getInstance(Context context) {
        if(instance == null)
            instance = Room.databaseBuilder(context, UserDatabase.class, "user.db").build();
        return instance;
    }

    public abstract UserDAO getDao();
}
