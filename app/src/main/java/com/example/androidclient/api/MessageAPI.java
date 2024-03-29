package com.example.androidclient.api;


import android.media.MediaExtractor;

import androidx.lifecycle.MutableLiveData;

import com.example.androidclient.MyApplication;
import com.example.androidclient.R;
import com.example.androidclient.WebServiceAPI;
import com.example.androidclient.dao.MessageDao;
import com.example.androidclient.entities.ContactTransfer;
import com.example.androidclient.entities.Message;
import com.example.androidclient.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {
    private MutableLiveData<List<Message>> messageListData;
    private MessageDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private User user;

    public MessageAPI(MutableLiveData<List<Message>> MessageListData, MessageDao dao, User user) {
        this.messageListData = MessageListData;
        this.dao = dao;
        this.user = user;

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl) + "/api/")
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public WebServiceAPI getContactWebServiceAPI(String server) {
        retrofit = new Retrofit.Builder()
                .baseUrl((server + "/api/"))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WebServiceAPI.class);
    }

    public void getAllMessages(String id) {
        //Call<List<Message>> call = getContactWebServiceAPI(user.getServer()).GetAllMessages(user.getToken(),id);
        Call<List<Message>> call = webServiceAPI.GetAllMessages(user.getToken(),id);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                List<Message> messages2 = new ArrayList<>();
                //for(Message message : messages) {
                //    messages2.add(new Message(message.getContent(),message.getTime(), message.isSent(), user.getUsername(), id));
               // }
                new Thread(() -> {
                    dao.clear();
                    dao.insert(messages);
                    messageListData.postValue(messages);
                    //MessageListData.MessageValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    public void createMessage(String id, Message message) {
       // Call<Void> call = getContactWebServiceAPI(user.getServer()).CreateMessage(user.getToken(),id, message);
        Call<Void> call = webServiceAPI.CreateMessage(user.getToken(),id, message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Void messages = response.body();
                int code = response.code();
                new Thread(() -> {
                    dao.insert(message);
                    messageListData.postValue(dao.index());
                    //MessageListData.MessageValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public void transfer(String id,String contactServer, Message message) {
        ContactTransfer contactTransfer = new ContactTransfer(user.getUsername(), id, message.getContent());
        //Call<Void> call = webServiceAPI.transfer(contactTransfer);
        Call<Void> call = getContactWebServiceAPI(contactServer).transfer(contactTransfer);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Void messages = response.body();
                int code = response.code();
                new Thread(() -> {
                    //dao.insert(message);
                    //messageListData.postValue(dao.index());
                    //MessageListData.MessageValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }




}