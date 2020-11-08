package com.example.teammatch.room_db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teammatch.objects.Evento;


@Database(entities = {Evento.class}, version = 1)
public abstract class EventoDataBase extends RoomDatabase {

    public static EventoDataBase instance;

    public static EventoDataBase getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context, EventoDataBase.class, "evento.db").build();
        return instance;
    }

    public abstract EventoDAO getDao();
}