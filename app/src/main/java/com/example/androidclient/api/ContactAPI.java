package com.example.androidclient.api;


import androidx.lifecycle.MutableLiveData;

import com.example.androidclient.MyApplication;
import com.example.androidclient.R;
import com.example.androidclient.WebServiceAPI;
import com.example.androidclient.dao.ContactDao;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.entities.ContactInvitations;
import java.util.List;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPI {
    private MutableLiveData<List<Contact>> contactListData;
    private ContactDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public ContactAPI(MutableLiveData<List<Contact>> ContactListData, ContactDao dao) {
        this.contactListData = ContactListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void getAllContacts() {
        Call<List<Contact>> call = webServiceAPI.GetAllContacts();
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                new Thread(() -> {
                    dao.clear();
                    dao.insert(contacts);
                    contactListData.postValue(response.body());
                    //ContactListData.ContactValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
            }
        });
    }

    public void addContact(Contact contact) {
        Call<Void> call = webServiceAPI.CreateContact(contact);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Void contacts = response.body();
                new Thread(() -> {
                    dao.insert(contact);
                    //contactListData.postValue(response.body());
                    //ContactListData.ContactValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void DeleteContact(Contact contact) {
        Call<Void> call = webServiceAPI.DeleteContact(contact.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Void contacts = response.body();
                new Thread(() -> {
                    dao.delete(contact);
                    //contactListData.postValue(response.body());
                    //ContactListData.ContactValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public int invitations(ContactInvitations contactInvitations) {
        final int[] res = {0};
        Call<Void> call = webServiceAPI.Invitations(contactInvitations);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                res[0] = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                res[0] = -1;
            }
        });
        while(res[0] == 0) {

        }
        return res[0];
    }
}