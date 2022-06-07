package com.example.androidclient.api;


import android.media.MediaExtractor;

import androidx.lifecycle.MutableLiveData;

import com.example.androidclient.MyApplication;
import com.example.androidclient.R;
import com.example.androidclient.WebServiceAPI;
import com.example.androidclient.dao.MessageDao;
import com.example.androidclient.entities.Message;

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

    public MessageAPI(MutableLiveData<List<Message>> MessageListData, MessageDao dao) {
        this.messageListData = MessageListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void getAllMessages(String id) {
        Call<List<Message>> call = webServiceAPI.GetAllMessages(id);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                new Thread(() -> {
                    dao.clear();
                    dao.insert(messages);
                    messageListData.postValue(response.body());
                    //MessageListData.MessageValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    public void createMessage(String id, Message message) {
        Call<Void> call = webServiceAPI.CreateMessage(id, message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Void messages = response.body();
                new Thread(() -> {
                    dao.insert(message);
                    //messageListData.postValue(response.body());
                    //MessageListData.MessageValue(dao.get());
                }).start();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }




}