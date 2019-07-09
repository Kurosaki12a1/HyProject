package com.hy.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hy.project.R;
import com.hy.project.model.MessageChatDatabase;

import java.util.ArrayList;

import static com.hy.project.adapter.MessageChatAdapter.*;

public class MessageChatAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<MessageChatDatabase> listChat = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageChatAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageChatDatabase infoChat = listChat.get(position);
        Glide.with(holder.itemView.getContext()).load(infoChat.getAvatarURLChat()).into(holder.ivChat);
        holder.tvNameUser.setText(infoChat.getUserNameChat());
        holder.tvChat.setText(infoChat.getTextChat());
    }

    public void setData(ArrayList<MessageChatDatabase> listData) {
        listChat = new ArrayList<>(listData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivChat;
        TextView tvNameUser, tvChat;

        ViewHolder(View v) {
            super(v);
            ivChat = v.findViewById(R.id.imgAvatarText);
            tvNameUser = v.findViewById(R.id.tvNameUser);
            tvChat = v.findViewById(R.id.txtContentFriend);
        }
    }
}
