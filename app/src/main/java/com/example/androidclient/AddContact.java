package com.example.androidclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidclient.api.CallBackListener;
import com.example.androidclient.dao.ContactDao;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.entities.User;
import com.example.androidclient.viewmodels.ContactsViewModel;

public class AddContact extends AppCompatActivity {
    private AppDB db;
    private ContactDao contactDao;
    ContactsViewModel viewModel;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Intent intent = getIntent();
        user = new User(intent.getStringExtra("userName"));
        user.setToken(intent.getStringExtra("token"));
        //viewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        viewModel = new ContactsViewModel(user);


        Button add_contact_btn = findViewById(R.id.add_contact_button);
        add_contact_btn.setOnClickListener(view -> {
            if (Validate() == 0) {
                EditText et_username = findViewById(R.id.add_contact_username);
                EditText et_display = findViewById(R.id.add_contact_display_name);
                EditText et_server = findViewById(R.id.add_contact_server);
                Contact contact = new Contact(et_username.getText().toString(), et_display.getText().toString(), et_server.getText().toString());
                //contactDao.insert(contact);
                viewModel.add(contact, getListener());
            }
        });
    }

    public int Validate() {
        int flag = 0;
        EditText tvContact_username = findViewById(R.id.add_contact_username);
        if (tvContact_username.getText().toString().length() == 0) {
            tvContact_username.setError("Should not be empty");
            flag = 1;
        }
        EditText tv_displayname = findViewById(R.id.add_contact_display_name);
        if (tv_displayname.getText().toString().length() == 0) {
            tv_displayname.setError("Should not be empty");
            flag = 1;
        }
        EditText tv_server = findViewById(R.id.add_contact_server);
        if (tv_server.getText().toString().length() == 0) {
            tv_server.setError("Should not be empty");
            flag = 1;
        }
        return flag;
    }

    private CallBackListener getListener() {
        return new CallBackListener() {
            @Override
            public void onResponse(int code) {
                if(code == 201) {
                    finish();
                    runOnUiThread(() -> Toast.makeText(MyApplication.context, "Contact added successfully", Toast.LENGTH_LONG).show());
                }
                else if(code == 405)
                    runOnUiThread(() -> Toast.makeText(AddContact.this, "Contact already exists", Toast.LENGTH_LONG).show());
                else if(code == 406)
                    runOnUiThread(() -> Toast.makeText(AddContact.this, "Can't add on your contact server", Toast.LENGTH_LONG).show());
            }
            @Override
            public void onFailure() {
                runOnUiThread(() -> Toast.makeText(AddContact.this, "Can't reach server", Toast.LENGTH_LONG).show());
            }
        };
    }
}