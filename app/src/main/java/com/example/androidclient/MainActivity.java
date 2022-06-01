package com.example.androidclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMoveToRegister = findViewById(R.id.buttonMoveToRegister);
        btnMoveToRegister.setOnClickListener(p -> {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        });
    }
}