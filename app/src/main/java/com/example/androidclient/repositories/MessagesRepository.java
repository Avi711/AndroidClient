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
import com.example.androidclient.entities.User;

import java.util.LinkedList;
import java.util.List;

public class MessagesRepository {
    private MessageDao dao;
    private MessageListData messageListData;
    private MessageAPI api;
    User user;
    String contactID;

    public MessagesRepository(User user, String contactID) {
        AppDB db = AppDB.getInstance();
        this.user = user;
        this.contactID = contactID;
        dao = db.messageDao();
        messageListData = new MessageListData();
        api = new MessageAPI(messageListData, dao, user);
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

    public LiveData<List<Message>> get(String id) {
        if (id == null) return null;
        api.getAllMessages(id);
        return messageListData;
    }

    public void add (String id,String contactServer, Message message) {
        api.createMessage(id, message);
        api.transfer(id, contactServer, message);
    }

    public void delete (Message message) { }
    public void reload () {  }
}
