package com.example.androidclient;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Chat extends AppCompatActivity {

    ImageView profilePictureView;

    TextView userNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView messageList = findViewById(R.id.messageList);


        profilePictureView = findViewById(R.id.user_image_profile_image);
        userNameView = findViewById(R.id.user_text_user_name);
        Intent intent = getIntent();
        if(intent != null) {
            String userName = intent.getStringExtra("userName");
           // int profilePicture = intent.getIntExtra("profilePicture", R.drawable.round_button_main_color);
            profilePictureView.setImageResource(R.drawable.profile2);
            userNameView.setText(userName);
        }



    }
}