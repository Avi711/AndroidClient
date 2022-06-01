package com.example.androidclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnMoveToLogin = findViewById(R.id.buttonMoveToLogin);
        btnMoveToLogin.setOnClickListener(p -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }
}