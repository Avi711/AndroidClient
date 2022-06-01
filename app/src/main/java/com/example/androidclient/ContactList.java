package com.example.androidclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    final private int[] profilePictures = {
            R.drawable.profile2, R.drawable.profile3, R.drawable.profile4,
            R.drawable.profile2, R.drawable.profile3, R.drawable.profile4
    };

    final private String[] userNames = {
            "Blue User", "Golden User", "Green User", "Red User", "Lightblue User", "Pink User"
    };

    final private String[] lastMassages = {
            "Hi, how are you?", "24K Magic", "I'm GREEN!", "Red is my name", "wasap :)", "Yo!"
    };

    final private String[] times = {
            "12:00", "00:30", "3:23", "8:59", "14:52", "12:23"
    };

    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ArrayList<Contact> users = new ArrayList<>();

        for (int i = 0; i < profilePictures.length; i++) {
            Contact aUser = new Contact(
                    userNames[i], profilePictures[i],
                    lastMassages[i], times[i]
            );

            users.add(aUser);
        }

        listView = findViewById(R.id.contact_list);
        adapter = new CustomListAdapter(getApplicationContext(), users);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), Chat.class);

            intent.putExtra("userName", userNames[i]);
            intent.putExtra("profilePicture", profilePictures[i]);
            intent.putExtra("lastMassage", lastMassages[i]);
            intent.putExtra("time", times[i]);

            startActivity(intent);
        });
    }
}