package com.example.androidclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidclient.dao.ContactDao;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.viewmodels.ContactsViewModel;

public class AddContact extends AppCompatActivity {
     private AppDB db;
     private ContactDao contactDao;
    ContactsViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
//        AppDB db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactsDB")
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build();
//        contactDao = db.contactDao();
        viewModel = new ViewModelProvider(this).get(ContactsViewModel.class);


        Button add_contact_btn = findViewById(R.id.add_contact_button);
        add_contact_btn.setOnClickListener(view -> {
            if(Validate() == 0) {
                EditText et_username = findViewById(R.id.add_contact_username);
                EditText et_display = findViewById(R.id.add_contact_display_name);
                EditText et_server = findViewById(R.id.add_contact_server);
                Contact contact = new Contact(et_username.getText().toString(), et_display.getText().toString(), et_server.getText().toString());
                //contactDao.insert(contact);
                int res = viewModel.add(contact);
                if(res == 400) {
                    et_username.setError("Username doesn't exists in the server you entered");
                }
                else if(res == -1) {
                    et_username.setError("Contact already exists, if you can't see please reload your contacts.");
                }
                else if(res != 201) {
                    et_username.setError("Can't access server");
                }
                else {
                    finish();
                }
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