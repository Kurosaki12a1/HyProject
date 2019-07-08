package com.hy.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hy.project.R;
import com.hy.project.model.RoomChat;

import java.util.ArrayList;

public class RoomChatAdapter extends RecyclerView.Adapter<RoomChatAdapter.ViewHolder> {

    //  private ArrayList<Any> listData = ArrayList<Any>();

    private ArrayList<RoomChat> listRoom = new ArrayList<RoomChat>();

    private OnClickItem listener;

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final RoomChat info = listRoom.get(position);
        holder.tvRoomName.setText(info.getRoomName());
        holder.tvPeopleJoin.setText(info.getNumberPeopleJoin());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(info);
            }
        });
    }

    public void setData(ArrayList<RoomChat> listData,OnClickItem onClickItem) {
        listRoom = new ArrayList<>(listData);
        this.listener = onClickItem;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listRoom.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvRoomName, tvPeopleJoin;

        ViewHolder(View v) {
            super(v);
            tvRoomName = v.findViewById(R.id.tvRoomName);
            tvPeopleJoin = v.findViewById(R.id.tvNumber);
        }
    }

    public interface OnClickItem {
        void onClick(RoomChat dataRoom);
    }
}

