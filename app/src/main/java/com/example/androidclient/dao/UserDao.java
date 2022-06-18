package com.example.androidclient.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidclient.entities.Message;
import com.example.androidclient.entities.User;

import java.util.List;

@Dao
public interface UserDao {


    @Query("SELECT * FROM user")
    List<User> index();


    @Insert
    void insert(User... user);

    @Query("SELECT * FROM user WHERE Username = :id")
    User get(String id);

    @Delete
    void delete(User... users);

    @Query("DELETE FROM user")
    void clear();

}
