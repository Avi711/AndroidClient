package com.example.androidclient.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidclient.entities.Contact;

import java.util.List;

@Dao
public interface ContactDao {


    @Query("SELECT * FROM contact")
    List<Contact> index();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact get(int id);

    @Insert
    void insert(Contact... contact);

    @Insert
    void insert(List<Contact> list);


    @Update
    void update(Contact... contact);


    @Delete
    void delete(Contact... contact);

    @Query("DELETE FROM contact")
    void clear();
}
