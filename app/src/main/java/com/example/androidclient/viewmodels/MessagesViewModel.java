package com.example.androidclient.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidclient.entities.Message;
import com.example.androidclient.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel extends ViewModel {
    private MessagesRepository mRepository;
    private LiveData<List<Message>> messages;

    public MessagesViewModel() {
        this.mRepository = new MessagesRepository();
        this.messages = mRepository.getAll();
    }

    public LiveData<List<Message>> get(String id) {return mRepository.get(id);}

    public void add(Message message) {mRepository.add(message);}

    public void delete(Message message) {mRepository.delete(message);}

    public void reload() {mRepository.reload();}
}


