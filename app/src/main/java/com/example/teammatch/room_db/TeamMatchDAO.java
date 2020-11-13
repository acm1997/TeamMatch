package com.example.teammatch.room_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.teammatch.objects.Equipo;
import com.example.teammatch.objects.Evento;
import com.example.teammatch.objects.User;

import java.util.List;

@Dao
public interface TeamMatchDAO {

    //  EVENTOS
    @Query("SELECT * FROM evento")
    public List<Evento> getAllEventos();

    @Insert
    public long insertEvento(Evento evento);

    @Update
    public int updateEvento(Evento evento);

    @Delete
    public void deleteEvento(Evento evento);

    @Query("DELETE FROM evento")
    public void deleteAllEventos();

    @Query("SELECT * FROM evento WHERE userCreatorId = :userCreatorId")
    public List<Evento> getAllEventosByUserId(long userCreatorId);

    /*
    @Query("SELECT * FROM evento WHERE userCreatorId = :idUser")
    public List<Evento> getAllEventosByUser(long idUser);
    */


    //  EQUIPOS
    @Query("SELECT * FROM equipo")
    public List<Equipo> getAllEquipos();

    @Insert
    public long insertEquipo(Equipo equipo);

    @Update
    public int updateEquipo(Equipo equipo);

    @Delete
    public void deleteEquipo(Equipo equipo);

    @Query("DELETE FROM equipo")
    public void deleteAllEquipos();


    //  USUARIOS
    @Query("SELECT * FROM usuarios")
    public List<User> getAllUsers();

    @Insert
    public long insertUser(User usuario);

    @Update
    public int update(User usuario);

    @Query("DELETE FROM usuarios")
    public void deleteAllUsers();

    @Delete
    public void deleteUser(User usuario);

    @Query("SELECT * FROM usuarios where username=(:username) and password=(:password)")
    User login(String username, String password);

}