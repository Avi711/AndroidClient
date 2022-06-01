package com.example.androidclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Contact> {
    LayoutInflater inflater;

    public CustomListAdapter(Context context, ArrayList<Contact> userArrayList) {
        super(context, R.layout.custom_list_item, userArrayList);

        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact user = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_image);
        TextView userName = convertView.findViewById(R.id.user_name);
        TextView lastMessage = convertView.findViewById(R.id.last_message);
        TextView time = convertView.findViewById(R.id.time);

        imageView.setImageResource(user.getImage());
        userName.setText(user.getContactName());
        lastMessage.setText(user.getLastMessage());
        time.setText(user.getLastMessageTime());

        return convertView;
    }


}