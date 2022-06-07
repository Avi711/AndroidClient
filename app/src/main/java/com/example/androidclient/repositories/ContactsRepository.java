package com.example.androidclient.repositories;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.androidclient.AppDB;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.api.ContactAPI;
import com.example.androidclient.dao.ContactDao;
import com.example.androidclient.entities.ContactInvitations;
import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {
    private ContactDao dao;
    private ContactListData contactListData;
    private ContactAPI api;

    public ContactsRepository() {
        AppDB db = AppDB.getInstance();
        dao = db.contactDao();
        contactListData = new ContactListData();
        api = new ContactAPI(contactListData, dao);
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
            }).start();
        }
    }
    public LiveData<List<Contact>> getAll() { return contactListData; }

    public int add (final Contact contact) {
        ContactInvitations contactInvitations = new ContactInvitations("avi77", contact.getId(), contact.getServer());
        int res = api.invitations(contactInvitations);
        if(res == 201) {
            res = api.addContact(contact);
            if(res == 400)
                res = -2;
        }
        return res;
    }

    public void delete (final Contact contact) {
        api.DeleteContact(contact);
    }
    public void reload () { api.getAllContacts(); }
}
