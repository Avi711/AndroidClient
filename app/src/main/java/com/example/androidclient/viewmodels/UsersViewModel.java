package com.example.androidclient.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidclient.AppDB;
import com.example.androidclient.api.MessageAPI;
import com.example.androidclient.dao.MessageDao;
import com.example.androidclient.dao.UserDao;
import com.example.androidclient.entities.Message;
import com.example.androidclient.entities.User;
import com.example.androidclient.repositories.MessagesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersViewModel extends ViewModel {
    private List<User> users;
    private static UsersViewModel instance;


    public static UsersViewModel getInstance() {
        if (instance == null) {
            instance = new UsersViewModel();
        }
        return instance;
    }


    private UsersViewModel() {
        users = new ArrayList<>();
    }

    public User get(String id) {
        for (User u : users) {
            if (Objects.equals(u.getUsername(), id)) {
                return u;
            }
        }
        return null;
    }

    public void add(User user) {
        users.add(user);
    }

    public void clear() {
        users.clear();
    }

    public void delete(String id) {
        for (User u : users) {
            if (Objects.equals(u.getUsername(), id)) {
                users.remove(u);
            }
        }
    }

}


