package com.example.androidclient;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMoveToRegister = findViewById(R.id.buttonMoveToRegister);
        btnMoveToRegister.setOnClickListener(p -> {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
            finish();
        });

        Button loginBtn = findViewById(R.id.login_Button);
        loginBtn.setOnClickListener(p -> {
            if(Validate() == 0) {
                Intent I = new Intent(this, ContactList.class);
                startActivity(I);
            }
        });
    }
    public int Validate() {
        int flag = 0;
        EditText tvContact_username = findViewById(R.id.login_editTextUserName);
        if(tvContact_username.getText().toString().length() == 0) {
            tvContact_username.setError("Should not be empty");
            flag = 1;
        }
        EditText tv_password1 = findViewById(R.id.login_editTextPassword);
        if(tv_password1.getText().toString().length() == 0) {
            tv_password1.setError("Should not be empty");
            flag = 1;
        }
        return flag;
    }
}