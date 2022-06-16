package com.example.androidclient.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String created;
    boolean sent;
    String userID;
    String contactID;



    public Message(String content, String created, boolean sent) {
        this.content = content;
        this.created = created;
        this.sent = sent;
    }

    @Ignore
    public Message(String content, String created, boolean sent, String userID, String contactID) {
        this.content = content;
        this.created = created;
        this.sent = sent;
        this.userID = userID;
        this.contactID = contactID;
    }

    public int getId() { return id; }

    public String getContent() { return content; }

    public String getCreated() { return created; }

    public boolean isSent() { return sent; }

    public void setId(int id) { this.id = id; }

    public void setContent(String content) { this.content = content; }

    public void setCreated(String created) { this.created = created; }

    public void setSent(boolean sent) { this.sent = sent; }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }
}


