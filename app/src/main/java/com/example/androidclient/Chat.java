package com.example.androidclient;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidclient.adapters.MessageListAdapter;
import com.example.androidclient.entities.Message;
import com.example.androidclient.entities.User;
import com.example.androidclient.viewmodels.MessagesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    FloatingActionButton btn_mic, btn_pic, btn_video, PlusButton, sendMessageButton;
    Animation rotate_open_anim, rotate_close_anim, from_bottom_anim, to_bottom_anim;
    boolean isOpen = false;
    ImageView profilePictureView;
    TextView userNameView;
    MessageListAdapter adapter;
    MessagesViewModel viewModel;
    String contactUserName;
    User user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        List<Message> messageList = new ArrayList<>();
        //viewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        Intent curIntent = getIntent();
        user = new User(curIntent.getStringExtra("userName"));
        user.setToken(curIntent.getStringExtra("token"));
        Intent intent = getIntent();
        contactUserName = intent.getStringExtra("contactUserName");
        viewModel = new MessagesViewModel(user, contactUserName);

        EditText editText = findViewById(R.id.editMsg);

        btn_mic = findViewById(R.id.btn_mic);
        btn_pic = findViewById(R.id.btn_pic);
        btn_video = findViewById(R.id.btn_video);
        PlusButton = findViewById(R.id.PlusButton);
        sendMessageButton = findViewById(R.id.send_message_button);
        rotate_close_anim = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        rotate_open_anim = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        from_bottom_anim = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        to_bottom_anim = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        menuButtons();





        profilePictureView = findViewById(R.id.user_image_profile_image);
        userNameView = findViewById(R.id.user_text_user_name);
        //Intent intent = getIntent();
        if (intent != null) {
            //contactUserName = intent.getStringExtra("contactUserName");
            // int profilePicture = intent.getIntExtra("profilePicture", R.drawable.round_button_main_color);
            profilePictureView.setImageResource(R.drawable.profile2);
            userNameView.setText(contactUserName);
        }

//        messageList.add(new Message("helloooooooodfsdgd", "45:45", true));
//        messageList.add(new Message("hiiiiiiiiiiii", "45:45", false));
//        messageList.add(new Message("popopofpgodfpgodfpgohdfpohgpopo", "45:45", true));
//        messageList.add(new Message("n,mbn,mnb,mnb,mnb,mnb,mvnvhjgvjh", "45:45", false));


        RecyclerView messageListView = findViewById(R.id.messageList);
        adapter = new MessageListAdapter(this);
        messageListView.setAdapter(adapter);
        messageListView.setLayoutManager(new LinearLayoutManager(this));

        //messageList = viewModel.get(userName);
        //adapter.setMessages(messageList);
        //messageListView.scrollToPosition(adapter.getItemCount() - 1);

        viewModel.get(contactUserName).observe(this, messages -> {
            adapter.setMessages(messages);
            messageListView.scrollToPosition(adapter.getItemCount() - 1);

            // swipeRefreshLayout.setRefreshing(false);
        });

        sendMessageButton.setOnClickListener(v -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            String s = LocalDateTime.now().toString();
            Message message = new Message(editText.getText().toString(), s,true);
            editText.getText().clear();
            viewModel.add(contactUserName,message);
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    sendMessageButton.setVisibility(View.INVISIBLE);
                    PlusButton.setVisibility(View.VISIBLE);
                }
                else {
                    sendMessageButton.setVisibility(View.VISIBLE);
                    PlusButton.setVisibility(View.INVISIBLE);
                }
            }
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void menuButtons() {
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