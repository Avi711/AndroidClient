package com.example.androidclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

import android.os.Bundle;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private int image;
    private String lastMessage;
    private String displayName;
    private String server;
    private String lastMessageTime;


    @Ignore
    public Contact(String userName, int image, String lastMessage, String lastMessageTime) {
        this.userName = userName;
        this.image = image;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public Contact(String userName,String displayName, int image, String server) {
        this.userName = userName;
        this.displayName = displayName;
        this.image = image;
        this.lastMessage = "";
        this.lastMessageTime = "";
        this.server = server;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getServer() {
        return server;
    }

    public int getImage() { return image; }

    public String getLastMessage() { return lastMessage; }

    public String getLastMessageTime() { return lastMessageTime; }

    public int getId() { return id; }

    public void setId(int id) {this.id = id; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}