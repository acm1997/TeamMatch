package com.example.teammatch.room_db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teammatch.objects.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM usuarios")
    public List<User> getAll();

    @Insert
    public long insert(User usuario);

    @Insert
    void registerUser(User usuario);

    @Update
    public int update(User usuario);

    @Query("DELETE FROM usuarios")
    public void deleteAll();

    @Delete
    public void deleteUser(User usuario);

    @Query("SELECT * FROM usuarios where username=(:username) and password=(:password)")
    User login(String username, String password);
}
