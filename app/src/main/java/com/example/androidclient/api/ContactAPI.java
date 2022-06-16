package com.example.androidclient.api;


import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.androidclient.MyApplication;
import com.example.androidclient.R;
import com.example.androidclient.WebServiceAPI;
import com.example.androidclient.dao.ContactDao;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.entities.ContactInvitations;
import com.example.androidclient.entities.User;
import com.example.androidclient.repositories.ContactsRepository;

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
    private User user;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public ContactAPI(MutableLiveData<List<Contact>> ContactListData, ContactDao dao, User user) {
        this.contactListData = ContactListData;
        this.dao = dao;
        this.user = user;
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public WebServiceAPI getContactWebServiceAPI(String server) {
        retrofit = new Retrofit.Builder()
                .baseUrl(("http://" + server + "/api/"))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WebServiceAPI.class);
    }

    public void getAllContacts() {
        Call<List<Contact>> call = webServiceAPI.GetAllContacts(user.getToken());
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                new Thread(() -> {
                    dao.clear();
                    dao.insert(contacts);
                    contactListData.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
            }
        });
    }

    public void addContact(Contact contact, CallBackListener listener) {
        Call<Void> call = webServiceAPI.CreateContact(user.getToken(),contact);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Void contacts = response.body();
                new Thread(() -> {
                    if (response.code() == 201) {
                        dao.insert(contact);
                        contactListData.postValue(dao.index());
                        listener.onResponse(response.code());
                    }
                    else {
                        listener.onResponse(405);
                    }
                    //ContactListData.ContactValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void DeleteContact(Contact contact) {
        Call<Void> call = webServiceAPI.DeleteContact(user.getToken(), contact.getId());
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

    public void invitations(ContactInvitations contactInvitations, Contact contact, CallBackListener listener) {
        //Call<Void> call = webServiceAPI.Invitations(contactInvitations);
        Call<Void> call = getContactWebServiceAPI(contact.getServer()).Invitations(contactInvitations);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if( response.code() == 201)
                    addContact(contact, listener);
                else {
                    listener.onResponse(406);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onFailure();
            }
        });
    }
}