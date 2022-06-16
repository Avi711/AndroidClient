package com.example.androidclient.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidclient.api.CallBackListener;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.entities.User;
import com.example.androidclient.repositories.ContactsRepository;

import java.util.List;

public class ContactsViewModel extends ViewModel {
    private ContactsRepository mRepository;
    private LiveData<List<Contact>> contacts;
    private User user;

    public ContactsViewModel(User user) {
        this.mRepository = new ContactsRepository(user);
        this.contacts = mRepository.getAll();
    }

    public LiveData<List<Contact>> get() {return contacts;}

    public void add(Contact contact, CallBackListener listener) {mRepository.add(contact, listener);}

    public void delete(Contact contact) {mRepository.delete(contact);}

    public void reload() {mRepository.reload();}
}


