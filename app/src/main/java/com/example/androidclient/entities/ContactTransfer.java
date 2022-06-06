package com.example.androidclient.entities;

public class ContactTransfer {
    private String from;
    private String to;
    private String server;

    public ContactTransfer(String from, String to, String server) {
        this.from = from;
        this.to = to;
        this.server = server;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}


