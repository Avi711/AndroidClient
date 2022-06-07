package com.example.androidclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidclient.R;
import com.example.androidclient.entities.Message;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvContent;
        private final TextView tvTime;



        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.sender_message_content);
            tvTime = itemView.findViewById(R.id.sender_message_time);

        }
    }

    private final LayoutInflater mInflater;
    private List<Message> messages;

    public MessageListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Message current = messages.get(viewType);
        View itemView;
        if(current.isSent()) {
            itemView = mInflater.inflate(R.layout.sender_message, parent, false);
        }
        else {
            itemView = mInflater.inflate(R.layout.reciver_message, parent, false);
        }
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if(messages != null) {
            final Message current = messages.get(position);
            holder.tvContent.setText(current.getContent());
            holder.tvTime.setText(current.getTime());
        }
    }

    public void setMessages(List<Message> s) {
        messages = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(messages != null)
            return messages.size();
        else return 0;
    }

    public List<Message> getMessages() {return messages; }


}
