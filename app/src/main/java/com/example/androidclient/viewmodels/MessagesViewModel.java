package com.example.androidclient.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidclient.entities.Message;
import com.example.androidclient.entities.User;
import com.example.androidclient.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel extends ViewModel {
    private MessagesRepository mRepository;
    private LiveData<List<Message>> messages;

    public MessagesViewModel(User user, String contactID) {
        this.mRepository = new MessagesRepository(user, contactID);
        this.messages = mRepository.getAll();
    }

    public LiveData<List<Message>> get(String id) {return mRepository.get(id);}

    public void add(String id,String contactServer, Message message) {mRepository.add(id, contactServer, message);}

    public void delete(Message message) {mRepository.delete(message);}

    public void reload() {mRepository.reload();}
}


