package com.example.androidclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

public class Contact extends AppCompatActivity {
    private String userName;
    private int image;
    private String lastMessage;
    private String lastMessageTime;

    public Contact(String userName, int image, String lastMessage, String lastMessageTime) {
        this.userName = userName;
        this.image = image;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        //ListView listView = findViewById();

    }

    public String getContactName() {
        return userName;
    }

    public int getImage() {
        return image;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }


}