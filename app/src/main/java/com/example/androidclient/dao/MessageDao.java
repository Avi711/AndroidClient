package com.example.androidclient.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidclient.entities.Contact;
import com.example.androidclient.entities.Message;

import java.util.List;

@Dao
public interface MessageDao {


    @Query("SELECT * FROM message")
    List<Message> index();

    @Query("SELECT * FROM message WHERE id = :id")
    Message get(int id);

    @Insert
    void insert(Message... messages);

    @Insert
    void insert(List<Message> messages);

    @Update
    void update(Message... messages);


    @Delete
    void delete(Message... messages);

    @Query("DELETE FROM message")
    void clear();
}
