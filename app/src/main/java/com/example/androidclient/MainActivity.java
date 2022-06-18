package com.example.androidclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidclient.api.UserAPI;
import com.example.androidclient.entities.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    UserAPI userAPI;
    EditText tvContact_username;
    EditText tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userAPI = new UserAPI();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContact_username = findViewById(R.id.login_editTextUserName);
        tv_password = findViewById(R.id.login_editTextPassword);

        Button btnMoveToRegister = findViewById(R.id.buttonMoveToRegister);
        btnMoveToRegister.setOnClickListener(p -> {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        });


        Button loginBtn = findViewById(R.id.login_Button);
        loginBtn.setOnClickListener(p -> {
            if (Validate() == 0) {
                Intent intent = new Intent(this, ContactList.class);
                User user = new User(tvContact_username.getText().toString(), tv_password.getText().toString());
                userAPI.login(user);
                while (user.getToken() == null) {
                }
                if (user.getToken() == "error") {
                    Toast toast = Toast.makeText(MyApplication.context, "Wrong user name or password", Toast.LENGTH_LONG);
                    toast.show();
                } else if (user.getToken() == "error2") {
                    Toast toast = Toast.makeText(MyApplication.context, "Can't reach server", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    intent.putExtra("userName", user.getUsername());
                    intent.putExtra("token", user.getToken());
                    startActivity(intent);
                }
            }
        });
    }

    public int Validate() {
        int flag = 0;
        if (tvContact_username.getText().toString().length() == 0) {
            tvContact_username.setError("Should not be empty");
            flag = 1;
        }
        if (tv_password.getText().toString().length() == 0) {
            tv_password.setError("Should not be empty");
            flag = 1;
        }
        return flag;
    }
}