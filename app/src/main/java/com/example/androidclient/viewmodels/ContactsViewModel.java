package com.example.androidclient.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidclient.entities.Contact;
import com.example.androidclient.repositories.ContactsRepository;

import java.util.List;

public class ContactsViewModel extends ViewModel {
    private ContactsRepository mRepository;
    private LiveData<List<Contact>> contacts;

    public ContactsViewModel() {
        this.mRepository = new ContactsRepository();
        this.contacts = mRepository.getAll();
    }

    public LiveData<List<Contact>> get() {return contacts;}

    public int add(Contact contact) {return mRepository.add(contact);}

    public void delete(Contact contact) {mRepository.delete(contact);}

    public void reload() {mRepository.reload();}
}


