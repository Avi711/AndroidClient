package com.example.androidclient.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidclient.R;
import com.example.androidclient.entities.Message;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvContent;
        private final TextView tvTime;
        private final LinearLayout layout;
        private final FrameLayout frameLayout;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.message_content);
            tvTime = itemView.findViewById(R.id.message_time);
            layout = itemView.findViewById(R.id.message_linear_layout);
            frameLayout = itemView.findViewById(R.id.message_container);
        }
    }

    private final LayoutInflater mInflater;
    private List<Message> messages;

    public MessageListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Message current = messages.get(viewType);
        View itemView;
        itemView = mInflater.inflate(R.layout.custom_message_layout, parent, false);
        return new MessageViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            holder.tvContent.setText(current.getContent());
            if(current.getCreated() != null && current.getCreated().length() != 5) {
                LocalDateTime s = LocalDateTime.parse(current.getCreated());
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
                String stringTime = fmt.format(s);
                holder.tvTime.setText(stringTime);
            }
            if (current.isSent()) {
                holder.layout.setBackgroundResource(R.drawable.rounded_corners_sender);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.frameLayout.getLayoutParams();
                holder.frameLayout.setPadding(0, 0, 100, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                holder.frameLayout.setLayoutParams(layoutParams);
            } else {
                holder.layout.setBackgroundResource(R.drawable.rounded_corners_reciever);
                holder.frameLayout.setPadding(100, 0, 0, 0);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.frameLayout.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                holder.frameLayout.setLayoutParams(layoutParams);
            }
        }
    }

    public void setMessages(List<Message> s) {
        messages = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messages != null)
            return messages.size();
        else return 0;
    }

    public List<Message> getMessages() {
        return messages;
    }


}
