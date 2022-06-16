package com.example.androidclient;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidclient.api.UserAPI;
import com.example.androidclient.entities.User;

public class Register extends AppCompatActivity {
    UserAPI userAPI;
    EditText tvContact_username;
    EditText tv_password1;
    EditText tv_password2;
    EditText tv_displayName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userAPI = new UserAPI();
        tvContact_username = findViewById(R.id.editTextUserName);
        tv_password1 = findViewById(R.id.editTextPassword1);
        tv_password2 = findViewById(R.id.editTextPassword2);
        tv_displayName = findViewById(R.id.editTextDisplayName);

        Button btnMoveToLogin = findViewById(R.id.buttonMoveToLogin);
        btnMoveToLogin.setOnClickListener(p -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });

        Button registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(p -> {
            if(Validate() == 0) {

                User user = new User(tvContact_username.getText().toString(), tv_password1.getText().toString(), tv_displayName.getText().toString(), "prfile2.png");
                userAPI.register(user);
                while(user.getToken() == null) {
                }
                if(user.getToken() == "error") {
                    Toast.makeText(MyApplication.context, "User name already exists.", Toast.LENGTH_LONG).show();
                }
                else if(user.getToken() == "error2") {
                    Toast.makeText(MyApplication.context, "Can't reach server", Toast.LENGTH_LONG).show();
                }
                else {
                    finish();
                    Toast.makeText(MyApplication.context, "User has been created - Please Login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public int Validate() {
        int flag = 0;
        if(tvContact_username.getText().toString().length() == 0) {
            tvContact_username.setError("Should not be empty");
            flag = 1;
        }
        if(tv_password1.getText().toString().length() == 0) {
            tv_password1.setError("Should not be empty");
            flag = 1;
        }
        if(tv_password2.getText().toString().length() == 0) {
            tv_password2.setError("Should not be empty");
            flag = 1;
        }
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