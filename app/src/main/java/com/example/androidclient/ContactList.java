package com.example.androidclient;

import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.androidclient.adapters.CustomListAdapter;
import com.example.androidclient.dao.ContactDao;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.entities.User;
import com.example.androidclient.viewmodels.ContactsViewModel;
import com.example.androidclient.viewmodels.UsersViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactList extends AppCompatActivity {

    ListView listView;
    CustomListAdapter adapter;
    List<Contact> contacts;
    private ContactsViewModel viewModel;
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        contacts = new ArrayList<Contact>();
        //viewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        Intent curIntent = getIntent();
        user = new User(curIntent.getStringExtra("userName"));
        user.setToken(curIntent.getStringExtra("token"));
        user.setServer(getResources().getString(R.string.BaseUrl));
        viewModel = new ContactsViewModel(user);


        UsersViewModel usersViewModel = UsersViewModel.getInstance();
        usersViewModel.add(user);


        FloatingActionButton go_to_add_contact_btn = findViewById(R.id.go_to_add_contact_btn);
        go_to_add_contact_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this,AddContact.class);
            intent.putExtra("userName", user.getUsername());
            intent.putExtra("token", user.getToken());
            startActivity(intent);
        });


        FloatingActionButton settingButton = findViewById(R.id.setting_button);
        settingButton.setOnClickListener(view -> {
            Intent intent = new Intent(this,SettingsActivity.class);
            intent.putExtra("userName", user.getUsername());
            startActivity(intent);
        });


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ContactList.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                user.setFirebaseToken(instanceIdResult.getToken());
            }
        });

        listView = findViewById(R.id.contact_list);
        adapter = new CustomListAdapter(getApplicationContext(), contacts);
        listView.setAdapter(adapter);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.contact_refresh);

        swipeRefreshLayout.setOnRefreshListener(() -> { viewModel.reload(); });

        viewModel.get().observe(this, contacts -> {
            contacts.sort(new Comparator<Contact>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public int compare(Contact o1, Contact o2) {
                    if(o1.getLastdate() != null && o2.getLastdate() != null){
                        long  mil1 = LocalDateTime.parse(o1.getLastdate()).toEpochSecond(ZoneOffset.UTC);
                        long mil2 = LocalDateTime.parse(o2.getLastdate()).toEpochSecond(ZoneOffset.UTC);
                        return Long.compare(mil2, mil1);
                    }
                    return 0;
                }
            });
            adapter.clear();
            adapter.addAll(contacts);
            swipeRefreshLayout.setRefreshing(false);
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), Chat.class);

            Contact contact = contacts.get(i);
            intent.putExtra("contactUserName", contact.getId());
            intent.putExtra("contactServer", contact.getServer());
            intent.putExtra("userName", user.getUsername());
            intent.putExtra("token", user.getToken());
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