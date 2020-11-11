package com.example.teammatch.room_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teammatch.objects.Equipo;

import java.util.List;

@Dao
public interface EquipoDAO {

    @Query("SELECT * FROM equipo")
    public List<Equipo> getAll();

    @Insert
    public long insert(Equipo equipo);

    @Update
    public int update(Equipo equipo);

    @Delete
    public void delete(Equipo equipo);

    @Query("DELETE FROM equipo")
    public void deleteAll();
}
