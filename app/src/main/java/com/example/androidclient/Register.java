package com.example.androidclient;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnMoveToLogin = findViewById(R.id.buttonMoveToLogin);
        btnMoveToLogin.setOnClickListener(p -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });

        Button registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(p -> {
            if(Validate() == 0) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    public int Validate() {
        int flag = 0;
        EditText tvContact_username = findViewById(R.id.editTextUserName);
        if(tvContact_username.getText().toString().length() == 0) {
            tvContact_username.setError("Should not be empty");
            flag = 1;
        }
        EditText tv_password1 = findViewById(R.id.editTextPassword1);
        if(tv_password1.getText().toString().length() == 0) {
            tv_password1.setError("Should not be empty");
            flag = 1;
        }
        EditText tv_password2 = findViewById(R.id.editTextPassword2);
        if(tv_password2.getText().toString().length() == 0) {
            tv_password2.setError("Should not be empty");
            flag = 1;
        }
        EditText tv_displayName = findViewById(R.id.editTextDisplayName);
        if(tv_displayName.getText().toString().length() == 0) {
            tv_displayName.setError("Should not be empty");
            flag = 1;
        }
        if(!tv_password1.getText().toString().equals(tv_password2.getText().toString())) {
            tv_password2.setError("Passwords Doesn't match");
            flag = 1;
        }
        if(!tv_password1.getText().toString().matches("^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$")) {
            tv_password1.setError("Passwords should contain at least one digit and letter");
        }
        if(tv_password1.getText().toString().length() < 8) {
            tv_password1.setError("password should contain at least 8 characters");
        }
        return flag;
    }
}