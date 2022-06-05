package com.example.androidclient;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 5)
public abstract class AppDB extends  RoomDatabase{
    public abstract ContactDao contactDao();
    //public abstract MessageDao messageDao();
}
