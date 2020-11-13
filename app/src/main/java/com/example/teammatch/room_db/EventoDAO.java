package com.example.teammatch.room_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teammatch.objects.Evento;

import java.util.List;

@Dao
public interface EventoDAO {

    @Query("SELECT * FROM evento")
    public List<Evento> getAll();

    @Insert
    public long insert(Evento evento);

    @Update
    public int update(Evento evento);

    @Delete
    public void delete(Evento evento);

    @Query("DELETE FROM evento")
    public void deleteAll();

    @Query("SELECT * FROM evento where nombre= :nombre")
    public List<Evento> SearchByName(String nombre);
}