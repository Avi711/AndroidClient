package com.example.androidclient.entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Contact {
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String server;
    private String last;
    private String lastdate;



    @RequiresApi(api = Build.VERSION_CODES.O)
    public Contact(@NonNull String id, String name, String server, String last, String lastdate) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
    }

    @Ignore
    public Contact(String id,String name, String last, String lastdate) {
        this.id = id;
        this.last = last;
        this.name = name;
        this.lastdate = lastdate;
    }

    @Ignore
    public Contact(String id,String name, String server) {
        this.id = id;
        this.name = name;
        this.server = server;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getServer() { return server; }

    public String getLast() { return last; }

    public String getLastdate() { return lastdate; }

    public void setId(String id) {this.id = id; }

    public void setLast(String last) { this.last = last; }

    public void setName(String name) { this.name = name; }

    public void setServer(String server) { this.server = server; }

    public void setlastdate(String lastdate) { this.lastdate = lastdate;}
}