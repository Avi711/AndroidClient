package com.example.androidclient;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Contact> {
    LayoutInflater inflater;

    public CustomListAdapter(Context context, List<Contact> userArrayList) {
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

        imageView.setImageResource(R.drawable.profile2);
        userName.setText(user.getName());
        lastMessage.setText(user.getLast());
        time.setText(user.getLastdate());

        return convertView;
    }

}
