package com.example.androidclient;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidclient.dao.ContactDao;
import com.example.androidclient.dao.MessageDao;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.entities.Message;

@Database(entities = {Contact.class, Message.class}, version = 27)
public abstract class AppDB extends  RoomDatabase{


    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();
    private static AppDB db;
    public static AppDB getInstance() {
        if (db == null) {
            db = Room.databaseBuilder(MyApplication.context, AppDB.class, "ContactsDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }
}