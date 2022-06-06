package com.example.androidclient.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class User {
    @NonNull
    @PrimaryKey
    private String Username;
    private String Password;
    private String DisplayName;
    private String Image;
    int isLogged;
    String token;

    public User(@NonNull String username, String password, String displayName, String image) {
        Username = username;
        Password = password;
        DisplayName = displayName;
        Image = image;
        isLogged = 0;
    }

    public int getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(int isLogged) {
        this.isLogged = isLogged;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
