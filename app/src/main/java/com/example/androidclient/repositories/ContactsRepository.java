package com.example.androidclient.repositories;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.androidclient.AppDB;
import com.example.androidclient.api.CallBackListener;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.api.ContactAPI;
import com.example.androidclient.dao.ContactDao;
import com.example.androidclient.entities.ContactInvitations;
import com.example.androidclient.entities.User;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {
    private ContactDao dao;
    private ContactListData contactListData;
    private ContactAPI api;
    private User user;

    public ContactsRepository(User user) {
        AppDB db = AppDB.getInstance();
        this.user = user;
        dao = db.contactDao();
        contactListData = new ContactListData();
        api = new ContactAPI(contactListData, dao, user);
    }
    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            setValue(new LinkedList<Contact>());
        }
        @Override
        protected void onActive() {
            super.onActive();
            List<Contact> contacts = dao.index();
            if(contacts.size() == 0) {
                reload();
            }
            new Thread(() ->
            {
                contactListData.postValue(dao.index());
                api.getAllContacts();
            }).start();
        }
    }
    public LiveData<List<Contact>> getAll() { return contactListData; }

    public void add (final Contact contact, CallBackListener listener) {
        ContactInvitations contactInvitations = new ContactInvitations("avi77", contact.getId(), contact.getServer());
        api.invitations(contactInvitations, contact, listener);
        //api.addContact(contact);
    }

    public void delete (final Contact contact) {
        api.DeleteContact(contact);
    }
    public void reload () { api.getAllContacts(); }
}
