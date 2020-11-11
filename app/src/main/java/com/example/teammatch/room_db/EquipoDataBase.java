package com.example.teammatch.room_db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teammatch.objects.Equipo;

@Database(entities = {Equipo.class}, version = 1)
public abstract class EquipoDataBase extends RoomDatabase {

    public static EquipoDataBase instance;

    public static EquipoDataBase getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context, EquipoDataBase.class, "equipo.db").build();
        return instance;
    }

    public abstract EquipoDAO getDao();
}
