package com.example.androidclient;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Chat extends AppCompatActivity {

    FloatingActionButton btn_mic, btn_pic, btn_video, PlusButton;
    Animation rotate_open_anim, rotate_close_anim, from_bottom_anim, to_bottom_anim;
    boolean isOpen = false;

    ImageView profilePictureView;

    TextView userNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btn_mic = (FloatingActionButton) findViewById(R.id.btn_mic);
        btn_pic = (FloatingActionButton) findViewById(R.id.btn_pic);
        btn_video = (FloatingActionButton) findViewById(R.id.btn_video);
        PlusButton = (FloatingActionButton) findViewById(R.id.PlusButton);
        rotate_close_anim = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        rotate_open_anim = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        from_bottom_anim = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        to_bottom_anim = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        PlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationPlus();
            }
        });
        btn_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Chat.this ,"mic clicked", Toast.LENGTH_SHORT).show();
            }
        });
        btn_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Chat.this, "pic clicked", Toast.LENGTH_SHORT).show();
            }
        });
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Chat.this, "video clicked", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView messageList = findViewById(R.id.messageList);


        profilePictureView = findViewById(R.id.user_image_profile_image);
        userNameView = findViewById(R.id.user_text_user_name);
        Intent intent = getIntent();
        if (intent != null) {
            String userName = intent.getStringExtra("userName");
            // int profilePicture = intent.getIntExtra("profilePicture", R.drawable.round_button_main_color);
            profilePictureView.setImageResource(R.drawable.profile2);
            userNameView.setText(userName);
        }


    }

    private void animationPlus() {
        if (isOpen) {
            PlusButton.startAnimation(rotate_close_anim);
            btn_mic.startAnimation(to_bottom_anim);
            btn_pic.startAnimation(to_bottom_anim);
            btn_video.startAnimation(to_bottom_anim);
            btn_mic.setClickable(false);
            btn_pic.setClickable(false);
            btn_video.setClickable(false);
            isOpen = false;
        } else {
            PlusButton.startAnimation(rotate_open_anim);
            btn_mic.startAnimation(from_bottom_anim);
            btn_pic.startAnimation(from_bottom_anim);
            btn_video.startAnimation(from_bottom_anim);
            btn_mic.setClickable(true);
            btn_pic.setClickable(true);
            btn_video.setClickable(true);
            isOpen = true;
        }
    }
}