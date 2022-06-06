package com.example.androidclient.repositories;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.androidclient.AppDB;
import com.example.androidclient.api.ContactAPI;
import com.example.androidclient.api.MessageAPI;
import com.example.androidclient.dao.MessageDao;
import com.example.androidclient.entities.Contact;
import com.example.androidclient.entities.ContactInvitations;
import com.example.androidclient.entities.Message;

import java.util.LinkedList;
import java.util.List;

public class MessagesRepository {
    private MessageDao dao;
    private MessageListData messageListData;
    private MessageAPI api;

    public MessagesRepository() {
        AppDB db = AppDB.getInstance();
        dao = db.messageDao();
        messageListData = new MessageListData();
        api = new MessageAPI(messageListData, dao);
    }
    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            setValue(new LinkedList<Message>());

        }
        @Override
        protected void onActive() {
            super.onActive();
            List<Message> messages = dao.index();
            if(messages.size() == 0) {
                reload();
            }
            new Thread(() ->
            {
                messageListData.postValue(dao.index());
            }).start();
        }
    }
    public LiveData<List<Message>> getAll() { return messageListData; }

    public void add (final Message message) {  }

    public void delete (final Message message) { }
    public void reload () {  }
}
