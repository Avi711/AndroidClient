package com.example.androidclient;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.androidclient.adapters.CustomListAdapter;
import com.example.androidclient.dao.ContactDao;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.viewmodels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity {

    final private int[] profilePictures = {
            R.drawable.profile2, R.drawable.profile3, R.drawable.profile4,
            R.drawable.profile2, R.drawable.profile3, R.drawable.profile4
    };

    final private String[] userNames = {
            "Blue User", "Golden User", "Green User", "Red User", "Lightblue User", "Pink User"
    };

    final private String[] lastMassages = {
            "Hi, how are you?", "24K Magic", "I'm GREEN!", "Red is my name", "wasap :)", "Yo!"
    };

    final private String[] times = {
            "12:00", "00:30", "3:23", "8:59", "14:52", "12:23"
    };

    ListView listView;
    CustomListAdapter adapter;
    List<Contact> contacts;
    private AppDB db;
    private ContactDao contactDao;
    private ContactsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        contacts = new ArrayList<Contact>();
        viewModel = new ViewModelProvider(this).get(ContactsViewModel.class);


//        AppDB db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ContactsDB")
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build();
//        contactDao = db.contactDao();
//        contacts.addAll(contactDao.index());

        //ContactAPI contactAPI = new ContactAPI(null,contactDao);
       // contactAPI.get();

        //contacts.addAll(viewModel.get());

        FloatingActionButton go_to_add_contact_btn = findViewById(R.id.go_to_add_contact_btn);
        go_to_add_contact_btn.setOnClickListener(view -> {
            Intent i = new Intent(this,AddContact.class);
            startActivity(i);
        });


        for (int i = 0; i < profilePictures.length; i++) {
            Contact aUser = new Contact(
                    userNames[i],
                    lastMassages[i], times[i]
            );
           // contacts.add(aUser);
        }

        listView = findViewById(R.id.contact_list);
        adapter = new CustomListAdapter(getApplicationContext(), contacts);
        listView.setAdapter(adapter);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.contact_refresh);

        swipeRefreshLayout.setOnRefreshListener(() -> { viewModel.reload(); });

        viewModel.get().observe(this, contacts -> {
            adapter.clear();
            adapter.addAll(contacts);
            swipeRefreshLayout.setRefreshing(false);
        });




        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), Chat.class);

            Contact contact = contacts.get(i);
            intent.putExtra("userName", contact.getId());
            //intent.putExtra("profilePicture", R.drawable.profile2);
            intent.putExtra("lastMassage", contact.getLast());
            intent.putExtra("time", contact.getLastdate());

            startActivity(intent);
        });

        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            //Contact contact = contacts.remove(i);
            //viewModel.delete(contact);
            //contactDao.delete(contact);
            //adapter.notifyDataSetChanged();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        //contacts.clear();
        //contacts.addAll(contactDao.index());
       // adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}