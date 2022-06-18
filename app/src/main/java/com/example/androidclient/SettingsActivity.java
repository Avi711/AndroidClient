package com.example.androidclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidclient.entities.User;
import com.example.androidclient.viewmodels.UsersViewModel;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {
    User user;
    EditText editText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent curIntent = getIntent();
        String userID = curIntent.getStringExtra("userName");
        UsersViewModel usersViewModel = UsersViewModel.getInstance();
        user = usersViewModel.get(userID);


        TextView tv_current_server = findViewById(R.id.tv_current_server);
        tv_current_server.setText("Current server:\n" + user.getServer());

        editText = findViewById(R.id.et_new_server);


        Button changeButton = findViewById(R.id.change_server_btn);
        changeButton.setOnClickListener(v -> {
            if (validate() == -1) {
                editText.setError("Cannot be empty");
            }
            else {
                user.setServer(editText.getText().toString());
                finish();
            }
        });

    }

    private int validate() {
        String name = editText.getText().toString();
        if(name.length() == 0) {
            return -1;
        }
        return 0;
    }
}