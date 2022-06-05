package com.example.androidclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddContact extends AppCompatActivity {
      private AppDB db;
     private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        AppDB db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactsDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        contactDao = db.contactDao();

        Button add_contact_btn = findViewById(R.id.add_contact_button);
        add_contact_btn.setOnClickListener(view -> {
            if(Validate() == 0) {
                EditText et_username = findViewById(R.id.add_contact_username);
                EditText et_display = findViewById(R.id.add_contact_display_name);
                EditText et_server = findViewById(R.id.add_contact_server);
                Contact contact = new Contact(et_username.getText().toString(), et_display.getText().toString(), et_server.getText().toString());
                contactDao.insert(contact);
                finish();
            }
        });
    }

    public int Validate() {
        int flag = 0;
        EditText tvContact_username = findViewById(R.id.add_contact_username);
        if(tvContact_username.getText().toString().length() == 0) {
            tvContact_username.setError("Should not be empty");
            flag = 1;
        }
        EditText tv_displayname = findViewById(R.id.add_contact_display_name);
        if(tv_displayname.getText().toString().length() == 0) {
            tv_displayname.setError("Should not be empty");
            flag = 1;
        }
        EditText tv_server = findViewById(R.id.add_contact_server);
        if(tv_server.getText().toString().length() == 0) {
            tv_server.setError("Should not be empty");
            flag = 1;
        }
        return flag;
    }
}